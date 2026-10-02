CREATE DATABASE biblioteca_fx
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Latin America.1252'
    LC_CTYPE = 'Spanish_Latin America.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


CREATE TABLE empleado (
    id SERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(120) UNIQUE,
    telefono VARCHAR(20),
    cargo VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    salario NUMERIC(10,2) NOT NULL CHECK (salario >= 0),
    fecha_contratacion DATE NOT NULL,
    estado VARCHAR(20) NOT NULL
);


INSERT INTO empleado
(nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado)
VALUES
('Carlos', 'López', '001-010190-0001A', 'carlos@gmail.com', '88881111',
 'Programador', 'Tecnología', 18000.00, '2025-01-15', 'Activo'),
('María', 'González', '001-020290-0002B', 'maria@gmail.com', '88882222',
 'Contadora', 'Contabilidad', 22000.00, '2024-08-10', 'Activo'),
('José', 'Martínez', '001-030390-0003C', 'jose@gmail.com', '88883333',
 'Vendedor', 'Ventas', 15000.00, '2025-03-01', 'Activo'),
('Ana', 'Ramírez', '001-040490-0004D', 'ana@gmail.com', '88884444',
 'Diseñadora', 'Marketing', 17500.00, '2024-11-20', 'Inactivo'),
('Luis', 'Hernández', '001-050590-0005E', 'luis@gmail.com', '88885555',
 'Soporte Técnico', 'Tecnología', 16000.00, '2025-05-05', 'Activo');
 
/* Comprobación de inserciones*/
SELECT * FROM empleado;


/*Nombres, apellidos y cargo*/
SELECT nombres, apellidos, cargo
FROM empleado;

/*Filtrar por departamento*/
SELECT *
FROM empleado
WHERE departamento = 'Tecnología';

/*Salarios mayores a cierto valor*/
SELECT *
FROM empleado
WHERE salario > 17000;

/*Ordenar de mayor a menor salario*/
SELECT *
FROM empleado
ORDER BY salario DESC;

/*Cantidad total de empleados*/
SELECT COUNT(*) AS total_empleados
FROM empleado;

/*Salario promedio*/
SELECT AVG(salario) AS salario_promedio
FROM empleado;

/*Suma de salarios*/
SELECT SUM(salario) AS total_salarios
FROM empleado;

/*Solo empleados activos*/
SELECT *
FROM empleado
WHERE estado = 'Activo';

/*Cantidad de empleados por departamento*/
SELECT departamento, COUNT(*) AS cantidad
FROM empleado
GROUP BY departamento;