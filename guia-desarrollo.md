# Guía de Desarrollo – Sistema de Gestión de Taller Tomasin SAC

## 1. Resumen del proyecto

Desarrollamos un sistema de gestión de órdenes de servicio para un taller mecánico. El sistema permite administrar clientes, vehículos, marcas, modelos, servicios, órdenes de trabajo, checklist de accesorios, registro de daños (manuales y con IA), fotografías de inspección y control de usuarios con roles (Administrador y Mecánico). Se descartaron auditoría y logs por simplicidad.

## 2. Base de datos – Script final (versión 7.1)

La base de datos MySQL se definió con el siguiente script. Incluye 12 tablas, relaciones con claves foráneas, índices para rendimiento y datos iniciales (roles, usuario admin, marcas, modelos y servicios de ejemplo).

```sql
-- =============================================
-- SISTEMA DE GESTIÓN DE TALLER TOMASIN SAC
-- Script de base de datos - Versión 7.1
-- =============================================

-- 1. ROLES
CREATE TABLE rol (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- 2. USUARIOS
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    bloqueado BOOLEAN DEFAULT FALSE,
    intentos_fallidos INT DEFAULT 0,
    fecha_bloqueo DATETIME NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id)
);

-- 3. CLIENTES
CREATE TABLE clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_documento ENUM('DNI','RUC') NOT NULL,
    numero_documento VARCHAR(11) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(255) NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE KEY unique_documento (tipo_documento, numero_documento)
);

-- 4. MARCAS
CREATE TABLE marca (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- 5. MODELOS
CREATE TABLE modelo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    id_marca INT NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE KEY unique_modelo_por_marca (id_marca, nombre),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
);

-- 6. VEHÍCULOS
CREATE TABLE vehiculos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    placa VARCHAR(7) NOT NULL UNIQUE,
    id_modelo INT NOT NULL,
    chasis VARCHAR(17) NOT NULL UNIQUE,
    motor VARCHAR(50) NOT NULL,
    color VARCHAR(30) NULL,
    anio YEAR NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    id_cliente INT NOT NULL,
    ultimo_km INT NOT NULL DEFAULT 0,
    CHECK (ultimo_km >= 0),
    FOREIGN KEY (id_modelo) REFERENCES modelo(id),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

-- 7. ÓRDENES DE SERVICIO
CREATE TABLE ordenes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero_correlativo VARCHAR(10) NOT NULL UNIQUE,
    fecha_ingreso DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_estimada_entrega DATE NOT NULL,
    fecha_entrega_real DATETIME NULL,
    estado ENUM('REGISTRADA','EN_REPARACION','FINALIZADA','ENTREGADA','ANULADA') NOT NULL DEFAULT 'REGISTRADA',
    id_cliente INT NOT NULL,
    id_vehiculo INT NOT NULL,
    kilometraje INT NOT NULL,
    nivel_combustible ENUM('E','1/4','1/2','3/4','F') NOT NULL,
    costo_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    observaciones TEXT NULL,
    motivo_anulacion TEXT NULL,
    fecha_anulacion DATETIME NULL,
    version INT NOT NULL DEFAULT 0,
    CHECK (kilometraje >= 0),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id)
);

-- 8. CHECKLIST DE ÓRDENES
CREATE TABLE orden_checklist (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_orden INT NOT NULL,
    item_index INT NOT NULL,
    respuesta ENUM('SI','NO','CANT') NOT NULL,
    cantidad INT NULL,
    observacion VARCHAR(255) NULL,
    UNIQUE KEY unique_orden_item (id_orden, item_index),
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE
);

-- 9. FOTOS DE ÓRDENES
CREATE TABLE fotos_orden (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_orden INT NOT NULL,
    nombre_archivo VARCHAR(255) NOT NULL,
    ruta VARCHAR(500) NOT NULL,
    fecha_subida DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    hash_archivo VARCHAR(64) NULL,
    procesado_ia BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE
);

-- 10. DAÑOS DEL VEHÍCULO
CREATE TABLE orden_danios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_orden INT NOT NULL,
    tipo_danio ENUM('QUINADO','ABOLLADO','RAYADO') NOT NULL,
    pos_x DECIMAL(5,2) NOT NULL,
    pos_y DECIMAL(5,2) NOT NULL,
    observacion VARCHAR(255) NULL,
    origen ENUM('MANUAL','IA') NOT NULL DEFAULT 'MANUAL',
    id_foto INT NULL,
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_foto) REFERENCES fotos_orden(id) ON DELETE SET NULL
);

-- 11. CATÁLOGO DE SERVICIOS
CREATE TABLE servicios_catalogo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NULL,
    precio_base DECIMAL(10,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- 12. SERVICIOS ASIGNADOS
CREATE TABLE servicios_asignados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_orden INT NOT NULL,
    id_servicio_catalogo INT NOT NULL,
    id_mecanico INT NOT NULL,
    precio_acordado DECIMAL(10,2) NOT NULL,
    estado_servicio ENUM('PENDIENTE','EN_PROGRESO','COMPLETADO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    fecha_inicio DATETIME NULL,
    fecha_fin DATETIME NULL,
    motivo_cancelacion TEXT NULL,
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_servicio_catalogo) REFERENCES servicios_catalogo(id),
    FOREIGN KEY (id_mecanico) REFERENCES usuarios(id)
);

-- ÍNDICES
CREATE INDEX idx_ordenes_cliente ON ordenes(id_cliente);
CREATE INDEX idx_ordenes_vehiculo ON ordenes(id_vehiculo);
CREATE INDEX idx_ordenes_estado ON ordenes(estado);
CREATE INDEX idx_ordenes_fecha_ingreso ON ordenes(fecha_ingreso);
CREATE INDEX idx_vehiculos_placa ON vehiculos(placa);
CREATE INDEX idx_vehiculos_cliente ON vehiculos(id_cliente);
CREATE INDEX idx_servicios_asignados_mecanico ON servicios_asignados(id_mecanico);
CREATE INDEX idx_fotos_procesado_ia ON fotos_orden(procesado_ia);

-- DATOS INICIALES (SEMILLA)
INSERT INTO rol (nombre, estado) VALUES 
('ADMINISTRADOR', 1),
('MECANICO', 1);

INSERT INTO usuarios (nombre, email, password_hash, id_rol, bloqueado) VALUES 
('Administrador Principal', 'admin@tomasin.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrkJ6L9Z3f5xNqQZ5xJ5xJ5xJ5xJ5xJ', 1, FALSE);

INSERT INTO marca (nombre) VALUES 
('Toyota'), ('Ford'), ('Chevrolet'), ('Hyundai'), ('Nissan');

INSERT INTO modelo (nombre, id_marca) VALUES 
('Corolla', 1), ('Hilux', 1), ('Rav4', 1),
('F-150', 2), ('Mustang', 2),
('Onix', 3), ('Cruze', 3),
('Tucson', 4), ('Elantra', 4),
('Sentra', 5), ('Versa', 5);

INSERT INTO servicios_catalogo (nombre, descripcion, precio_base) VALUES 
('Cambio de aceite', 'Incluye filtro y aceite 5W30', 80.00),
('Alineación y balanceo', 'Alineación de las cuatro ruedas', 120.00),
('Frenos delanteros', 'Cambio de pastillas y rectificado', 150.00),
('Diagnóstico computarizado', 'Escaneo de centralita', 60.00);