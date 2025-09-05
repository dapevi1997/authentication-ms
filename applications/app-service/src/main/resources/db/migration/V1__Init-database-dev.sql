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
    created_at DATE,
    address VARCHAR(255),
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
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
    ('ROLE_ADMIN', 'Administrador del sistema'),
    ('ROLE_ASESOR', 'Asesor de CrediYa'),
    ('ROLE_CLIENT', 'Cliente de la aplicación');

-- Usuario por defecto
INSERT IGNORE INTO user (
    name, lastname, birthdate, created_at, address, email, password, document_id, phone, base_salary, id_role
) VALUES (
    'Admin',
    'System',
    '1990-01-01',
    '1990-01-01',
    'Default Address',
    'admin@mail.com',
    '$2a$12$RL5AZHyYbDbeqaCGx.fr0.Da7dd0fx110/h1YRBx1qsNCnv0LJz9O', -- Ejemplo hash bcrypt: admin123
    1000000000,
    3000000000,
    5000000.00000,
    1 -- Este ID debe existir en la tabla role
),
(
    'Asesor',
    'System',
    '1990-01-01',
    '1990-01-01',
    'Default Address',
    'asesor@mail.com',
    '$2a$12$W4lzbPTUT.EBRVv/6pEkz.INBiqH7hXPk.hZcn/K3uuOuDqM00Blu', -- Ejemplo hash bcrypt: asesor
    1000000000,
    3000000000,
    5000000.00000,
    2 -- Este ID debe existir en la tabla role
),
(
    'Cliente',
    'System',
    '1990-01-01',
    '1990-01-01',
    'Default Address',
    'cliente@mail.com',
    '$2a$12$HjjKfz6uoNY2iZMD30MjkuWzk09h5sLcjQ9i8vGlFs7TRbHtrWr1C', -- Ejemplo hash bcrypt: cliente
    1000000000,
    3000000000,
    5000000.00000,
    3 -- Este ID debe existir en la tabla role
),

(
    'Andrés',
    'System',
    '1990-01-01',
    '1990-01-01',
    'Default Address',
    'andres.ramirez@example.com',
    '$2a$12$HjjKfz6uoNY2iZMD30MjkuWzk09h5sLcjQ9i8vGlFs7TRbHtrWr1C', -- Ejemplo hash bcrypt: cliente
    1000000000,
    3000000000,
    5000000.00000,
    3 -- Este ID debe existir en la tabla role
),

(
    'Sofía',
    'System',
    '1990-01-01',
    '1990-01-01',
    'Default Address',
    'sofia.mendez@example.com',
    '$2a$12$HjjKfz6uoNY2iZMD30MjkuWzk09h5sLcjQ9i8vGlFs7TRbHtrWr1C', -- Ejemplo hash bcrypt: cliente
    1000000000,
    3000000000,
    5000000.00000,
    3 -- Este ID debe existir en la tabla role
);