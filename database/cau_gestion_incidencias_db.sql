-- Crear la base de datos
CREATE DATABASE gestion_incidencias;
USE gestion_incidencias;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    perfil ENUM('USUARIO', 'TECNICO', 'ADMINISTRADOR') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de incidencias
CREATE TABLE incidencias (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_alta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio TIMESTAMP NULL,
    fecha_fin TIMESTAMP NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'ESPERA', 'CERRADA') DEFAULT 'PENDIENTE',
    prioridad ENUM('BAJA', 'MEDIA', 'ALTA') DEFAULT 'MEDIA',
    id_autor INT NOT NULL,
    id_tecnico_asignado INT NULL,
    id_incidencia_anterior INT NULL,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_autor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_tecnico_asignado) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_incidencia_anterior) REFERENCES incidencias(id_incidencia)
);

-- Tabla de histórico de resoluciones
CREATE TABLE historico_resoluciones (
    id_historico INT AUTO_INCREMENT PRIMARY KEY,
    id_incidencia INT NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_anterior ENUM('PENDIENTE', 'EN_PROCESO', 'ESPERA', 'CERRADA'),
    estado_nuevo ENUM('PENDIENTE', 'EN_PROCESO', 'ESPERA', 'CERRADA'),
    prioridad_anterior ENUM('BAJA', 'MEDIA', 'ALTA'),
    prioridad_nueva ENUM('BAJA', 'MEDIA', 'ALTA'),
    comentario TEXT,
    id_tecnico INT NOT NULL,
    FOREIGN KEY (id_incidencia) REFERENCES incidencias(id_incidencia),
    FOREIGN KEY (id_tecnico) REFERENCES usuarios(id_usuario)
);

-- Insertar datos de ejemplo
INSERT INTO usuarios (email, password, nombre, apellidos, perfil) VALUES
('admin@centro.edu', 'admin123', 'Admin', 'Principal', 'ADMINISTRADOR'),
('tecnico1@centro.edu', 'tecnico123', 'Paquita', 'Salas', 'TECNICO'),
('tecnico2@centro.edu', 'tecnico123', 'Noemi', 'Argüelles', 'TECNICO'),
('usuario1@centro.edu', 'usuario123', 'Juan', 'Cuesta', 'USUARIO'),
('usuario2@centro.edu', 'usuario123', 'Antonio', 'Recio', 'USUARIO');