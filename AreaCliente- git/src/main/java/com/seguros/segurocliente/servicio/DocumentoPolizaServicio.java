package com.seguros.segurocliente.servicio;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.seguros.segurocliente.modelo.DocumentoPoliza;
import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.DocumentoPolizaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para generar documentos PDF de pólizas.
 */
@Service
@RequiredArgsConstructor
public class DocumentoPolizaServicio {

    private final DocumentoPolizaRepositorio documentoRepositorio;

    /**
     * Genera un nuevo PDF para una póliza.
     * @param poliza póliza para la que se genera el documento
     * @param motivo motivo de la generación
     * @return documento guardado en la base de datos
     */
    public DocumentoPoliza generarNuevoDocumento(Poliza poliza, String motivo) {

        if ("anulada".equalsIgnoreCase(poliza.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La póliza está anulada. Contacte con nosotros en el teléfono 900 123 123."
            );
        }

        try {
            List<DocumentoPoliza> documentos =
                    documentoRepositorio.findByPoliza_IdPolizaOrderByVersionDesc(poliza.getIdPoliza());

            int nuevaVersion = 1;

            if (!documentos.isEmpty()) {
                nuevaVersion = documentos.get(0).getVersion() + 1;
            }

            File carpeta = new File("storage/polizas");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String nombreArchivo = poliza.getNumeroPoliza() + "_v" + nuevaVersion + ".pdf";
            String rutaArchivo = "storage/polizas/" + nombreArchivo;

            crearPdf(poliza, rutaArchivo, nuevaVersion, motivo);

            DocumentoPoliza documento = new DocumentoPoliza();
            documento.setPoliza(poliza);
            documento.setVersion(nuevaVersion);
            documento.setRutaArchivo(rutaArchivo);
            documento.setFechaGeneracion(LocalDateTime.now());
            documento.setMotivo(motivo);

            return documentoRepositorio.save(documento);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF");
        }
    }

    /**
     * Crea el archivo PDF en la carpeta del proyecto.
     * @param poliza póliza usada para crear el PDF
     * @param rutaArchivo ruta donde se guarda el archivo
     * @param version versión del documento
     * @param motivo motivo de la generación
     */
    private void crearPdf(Poliza poliza, String rutaArchivo, int version, String motivo) {

        try {
            Usuario usuario = poliza.getUsuario();

            Document documentoPdf = new Document();
            PdfWriter.getInstance(documentoPdf, new FileOutputStream(rutaArchivo));

            documentoPdf.open();

            documentoPdf.add(new Paragraph("DOCUMENTO DE POLIZA"));
            documentoPdf.add(new Paragraph(" "));
            documentoPdf.add(new Paragraph("Numero de poliza: " + poliza.getNumeroPoliza()));
            documentoPdf.add(new Paragraph("Version: " + version));
            documentoPdf.add(new Paragraph("Estado: " + poliza.getEstado()));
            documentoPdf.add(new Paragraph("Fecha inicio: " + poliza.getFechaInicio()));
            documentoPdf.add(new Paragraph("Fecha fin: " + poliza.getFechaFin()));
            documentoPdf.add(new Paragraph(" "));
            documentoPdf.add(new Paragraph("DATOS DEL CLIENTE"));
            documentoPdf.add(new Paragraph("Nombre: " + usuario.getNombre() + " " + usuario.getApellidos()));
            documentoPdf.add(new Paragraph("DNI: " + usuario.getDni()));
            documentoPdf.add(new Paragraph("Email: " + usuario.getEmail()));
            documentoPdf.add(new Paragraph("Telefono: " + usuario.getTelefono()));
            documentoPdf.add(new Paragraph("IBAN: " + usuario.getIban()));
            documentoPdf.add(new Paragraph(" "));
            documentoPdf.add(new Paragraph("DATOS DEL PRODUCTO"));
            documentoPdf.add(new Paragraph("Producto: " + poliza.getProducto().getNombre()));
            documentoPdf.add(new Paragraph("Descripcion: " + poliza.getProducto().getDescripcion()));
            documentoPdf.add(new Paragraph(" "));
            documentoPdf.add(new Paragraph("Motivo de generacion: " + motivo));
            documentoPdf.add(new Paragraph("Fecha de generacion: " + LocalDateTime.now()));

            documentoPdf.close();

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el archivo PDF");
        }
    }
}