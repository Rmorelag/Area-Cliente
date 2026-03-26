-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-03-2026 a las 14:58:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `seguro_cliente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documentos_poliza`
--

CREATE TABLE `documentos_poliza` (
  `id_documento` int(11) NOT NULL,
  `id_poliza` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ruta_archivo` varchar(255) NOT NULL,
  `fecha_generacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `motivo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `documentos_poliza`
--

INSERT INTO `documentos_poliza` (`id_documento`, `id_poliza`, `version`, `ruta_archivo`, `fecha_generacion`, `motivo`) VALUES
(1, 8, 1, 'storage/polizas/POL-2020-0008_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(2, 6, 1, 'storage/polizas/POL-2021-0006_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(3, 1, 1, 'storage/polizas/POL-2022-0001_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(4, 9, 1, 'storage/polizas/POL-2022-0009_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(5, 2, 1, 'storage/polizas/POL-2023-0002_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(6, 4, 1, 'storage/polizas/POL-2023-0004_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(7, 11, 1, 'storage/polizas/POL-2023-0011_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(8, 3, 1, 'storage/polizas/POL-2024-0003_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(9, 7, 1, 'storage/polizas/POL-2024-0007_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(10, 12, 1, 'storage/polizas/POL-2024-0012_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(11, 5, 1, 'storage/polizas/POL-2025-0005_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(12, 10, 1, 'storage/polizas/POL-2025-0010_v1.pdf', '2026-02-18 09:25:59', 'Alta póliza'),
(16, 1, 2, 'storage/polizas/POL-2022-0001_v2.pdf', '2026-02-18 09:25:59', 'Actualización de datos del cliente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `polizas`
--

CREATE TABLE `polizas` (
  `id_poliza` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `numero_poliza` varchar(50) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date DEFAULT NULL,
  `estado` enum('activa','anulada') NOT NULL DEFAULT 'activa'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `polizas`
--

INSERT INTO `polizas` (`id_poliza`, `id_usuario`, `id_producto`, `numero_poliza`, `fecha_inicio`, `fecha_fin`, `estado`) VALUES
(1, 1, 1, 'POL-2022-0001', '2022-03-15', NULL, 'activa'),
(2, 1, 3, 'POL-2023-0002', '2023-09-01', NULL, 'activa'),
(3, 2, 2, 'POL-2024-0003', '2024-02-10', NULL, 'activa'),
(4, 3, 1, 'POL-2023-0004', '2023-01-20', NULL, 'activa'),
(5, 4, 3, 'POL-2025-0005', '2025-11-18', NULL, 'activa'),
(6, 5, 1, 'POL-2021-0006', '2021-07-01', NULL, 'activa'),
(7, 5, 2, 'POL-2024-0007', '2024-09-30', NULL, 'activa'),
(8, 6, 2, 'POL-2020-0008', '2020-05-12', NULL, 'activa'),
(9, 7, 1, 'POL-2022-0009', '2022-12-01', NULL, 'activa'),
(10, 8, 3, 'POL-2025-0010', '2025-08-20', NULL, 'activa'),
(11, 9, 2, 'POL-2023-0011', '2023-04-01', '2024-04-15', 'anulada'),
(12, 10, 1, 'POL-2024-0012', '2024-01-10', '2025-01-10', 'anulada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre`, `descripcion`) VALUES
(1, 'Terceros', 'Incluye: Daños a terceros, Lunas y Asistencia en carretera 24/7.'),
(2, 'Terceros ampliado', 'Incluye: Daños a terceros, Incendio, Robo total y Asistencia en carretera 24/7.'),
(3, 'Todo riesgo', 'Incluye: Daños a terceros, Daños propios y Asistencia en carretera 24/7.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recibos`
--

CREATE TABLE `recibos` (
  `id_recibo` int(11) NOT NULL,
  `id_poliza` int(11) NOT NULL,
  `fecha_recibo` date NOT NULL,
  `importe` decimal(10,2) NOT NULL,
  `estado` enum('pagado','pendiente') NOT NULL DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `recibos`
--

INSERT INTO `recibos` (`id_recibo`, `id_poliza`, `fecha_recibo`, `importe`, `estado`) VALUES
(1, 1, '2025-12-15', 39.90, 'pagado'),
(2, 1, '2026-01-15', 39.90, 'pagado'),
(3, 1, '2026-02-15', 39.90, 'pendiente'),
(4, 2, '2026-01-01', 55.00, 'pagado'),
(5, 2, '2026-02-01', 55.00, 'pendiente'),
(6, 8, '2026-01-12', 29.80, 'pagado'),
(7, 8, '2026-02-12', 29.80, 'pagado'),
(8, 11, '2024-03-01', 41.20, 'pagado'),
(9, 11, '2024-04-01', 41.20, 'pagado'),
(10, 12, '2024-12-10', 33.60, 'pagado'),
(11, 12, '2025-01-10', 33.60, 'pagado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `email` varchar(150) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `iban` varchar(34) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellidos`, `dni`, `email`, `telefono`, `iban`, `password_hash`, `fecha_creacion`) VALUES
(1, 'Lucía', 'Martín Gómez', '11111111A', 'lucia.martin@correo.com', '600111222', 'ES1200491500051234567891', '$2y$10$demo_hash_lucia', '2026-02-18 09:25:58'),
(2, 'David', 'Sánchez Ruiz', '22222222B', 'david.sanchez@correo.com', '600111223', 'ES2300491500051234567892', '$2y$10$demo_hash_david', '2026-02-18 09:25:58'),
(3, 'Marta', 'Pérez Navarro', '33333333C', 'marta.perez@correo.com', '600111224', 'ES3400491500051234567893', '$2y$10$demo_hash_marta', '2026-02-18 09:25:58'),
(4, 'Javier', 'López Díaz', '44444444D', 'javier.lopez@correo.com', '600111225', 'ES4500491500051234567894', '$2y$10$demo_hash_javier', '2026-02-18 09:25:58'),
(5, 'Sara', 'García Molina', '55555555E', 'sara.garcia@correo.com', '600111226', 'ES5600491500051234567895', '$2y$10$demo_hash_sara', '2026-02-18 09:25:58'),
(6, 'Álvaro', 'Romero Torres', '66666666F', 'alvaro.romero@correo.com', '600111227', 'ES6700491500051234567896', '$2y$10$demo_hash_alvaro', '2026-02-18 09:25:58'),
(7, 'Elena', 'Hernández Vega', '77777777G', 'elena.hernandez@correo.com', '600111228', 'ES7800491500051234567897', '$2y$10$demo_hash_elena', '2026-02-18 09:25:58'),
(8, 'Pablo', 'Castro León', '88888888H', 'pablo.castro@correo.com', '600111229', 'ES8900491500051234567898', '$2y$10$demo_hash_pablo', '2026-02-18 09:25:58'),
(9, 'Nuria', 'Ortega Ramos', '99999999I', 'nuria.ortega@correo.com', '600111230', 'ES9100491500051234567899', '$2y$10$demo_hash_nuria', '2026-02-18 09:25:58'),
(10, 'Carlos', 'Iglesias Soto', '10101010J', 'carlos.iglesias@correo.com', '600111231', 'ES0200491500051234567800', '$2y$10$demo_hash_carlos', '2026-02-18 09:25:58');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `documentos_poliza`
--
ALTER TABLE `documentos_poliza`
  ADD PRIMARY KEY (`id_documento`),
  ADD UNIQUE KEY `uq_doc_poliza_version` (`id_poliza`,`version`),
  ADD KEY `idx_docs_poliza` (`id_poliza`);

--
-- Indices de la tabla `polizas`
--
ALTER TABLE `polizas`
  ADD PRIMARY KEY (`id_poliza`),
  ADD UNIQUE KEY `numero_poliza` (`numero_poliza`),
  ADD KEY `idx_polizas_usuario` (`id_usuario`),
  ADD KEY `idx_polizas_estado` (`estado`),
  ADD KEY `idx_polizas_producto` (`id_producto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `recibos`
--
ALTER TABLE `recibos`
  ADD PRIMARY KEY (`id_recibo`),
  ADD KEY `idx_recibos_poliza` (`id_poliza`),
  ADD KEY `idx_recibos_fecha` (`fecha_recibo`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `documentos_poliza`
--
ALTER TABLE `documentos_poliza`
  MODIFY `id_documento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `polizas`
--
ALTER TABLE `polizas`
  MODIFY `id_poliza` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `recibos`
--
ALTER TABLE `recibos`
  MODIFY `id_recibo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `documentos_poliza`
--
ALTER TABLE `documentos_poliza`
  ADD CONSTRAINT `fk_docs_poliza` FOREIGN KEY (`id_poliza`) REFERENCES `polizas` (`id_poliza`) ON DELETE CASCADE;

--
-- Filtros para la tabla `polizas`
--
ALTER TABLE `polizas`
  ADD CONSTRAINT `fk_polizas_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`),
  ADD CONSTRAINT `fk_polizas_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE;

--
-- Filtros para la tabla `recibos`
--
ALTER TABLE `recibos`
  ADD CONSTRAINT `fk_recibos_poliza` FOREIGN KEY (`id_poliza`) REFERENCES `polizas` (`id_poliza`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
