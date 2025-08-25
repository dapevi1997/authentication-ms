-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS authentication;

-- Usar la base de datos
USE authentication;

-- Crear tabla Rol primero
CREATE TABLE IF NOT EXISTS role (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- Crear tabla Usuario
CREATE TABLE IF NOT EXISTS user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    birthdate DATE,
    address VARCHAR(255),
    email VARCHAR(150) UNIQUE NOT NULL,
    document_id BIGINT NOT NULL,
    phone BIGINT,
    base_salary DECIMAL(15,5),
    id_role INT NOT NULL,
    FOREIGN KEY (id_role) REFERENCES role(id_role)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

INSERT IGNORE INTO role (name, description)
VALUES
    ('ADMIN', 'Administrador del sistema'),
    ('CLIENT', 'Cliente de la aplicación');