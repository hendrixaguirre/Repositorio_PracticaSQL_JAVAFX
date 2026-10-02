-- Script de la tabla empleado para la base de datos existente biblioteca_fx.
-- No crea otra base de datos.
--
-- Ejecutar ya conectado a biblioteca_fx. En este equipo el servidor local
-- escucha en el puerto 5433:
--
--   psql -h localhost -p 5433 -U postgres -d biblioteca_fx -f database/empleados.sql
--
-- El script se puede volver a ejecutar: no duplica la tabla ni las cédulas.

SET client_encoding = 'UTF8';

-- ---------------------------------------------------------------------------
-- Tabla
-- ---------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS empleado (
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

-- ---------------------------------------------------------------------------
-- Datos de prueba
-- Departamentos distintos, salarios variados y estados Activo / Inactivo
-- para poder probar filtros, ordenamientos y funciones agregadas.
-- ---------------------------------------------------------------------------

INSERT INTO empleado (
    nombres,
    apellidos,
    cedula,
    correo,
    telefono,
    cargo,
    departamento,
    salario,
    fecha_contratacion,
    estado
) VALUES
    ('Carlos', 'López',     '001-010190-0001A', 'carlos@gmail.com', '88881111', 'Programador',     'Tecnología',   18000.00, '2025-01-15', 'Activo'),
    ('María',  'González',  '001-020290-0002B', 'maria@gmail.com',  '88882222', 'Contadora',       'Contabilidad', 22000.00, '2024-08-10', 'Activo'),
    ('José',   'Martínez',  '001-030390-0003C', 'jose@gmail.com',   '88883333', 'Vendedor',        'Ventas',       15000.00, '2025-03-01', 'Activo'),
    ('Ana',    'Ramírez',   '001-040490-0004D', 'ana@gmail.com',    '88884444', 'Diseñadora',      'Marketing',    17500.00, '2024-11-20', 'Inactivo'),
    ('Luis',   'Hernández', '001-050590-0005E', 'luis@gmail.com',   '88885555', 'Soporte Técnico', 'Tecnología',   16000.00, '2025-05-05', 'Activo')
ON CONFLICT (cedula) DO NOTHING;

-- ---------------------------------------------------------------------------
-- Consultas requeridas
-- ---------------------------------------------------------------------------

-- 1. Mostrar todos los empleados.
SELECT *
FROM empleado;

-- 2. Mostrar solamente nombres, apellidos y cargo.
SELECT nombres, apellidos, cargo
FROM empleado;

-- 3. Filtrar empleados por departamento.
--    Cambie el valor de departamento para consultar otro área.
SELECT *
FROM empleado
WHERE departamento = 'Tecnología';

-- 4. Mostrar empleados cuyo salario sea mayor a un valor determinado.
--    Cambie 17000 por el límite que quiera probar.
SELECT *
FROM empleado
WHERE salario > 17000;

-- 5. Ordenar empleados por salario de mayor a menor.
SELECT *
FROM empleado
ORDER BY salario DESC;

-- 6. Contar el total de empleados.
SELECT COUNT(*) AS total_empleados
FROM empleado;

-- 7. Calcular el salario promedio.
SELECT ROUND(AVG(salario), 2) AS salario_promedio
FROM empleado;

-- 8. Calcular la suma total de los salarios.
SELECT SUM(salario) AS suma_salarios
FROM empleado;

-- 9. Mostrar solamente empleados activos.
--    Los datos de prueba usan el valor 'Activo'.
SELECT *
FROM empleado
WHERE estado = 'Activo';

-- 10. Agrupar empleados por departamento.
SELECT
    departamento,
    COUNT(*) AS cantidad,
    ROUND(AVG(salario), 2) AS salario_promedio,
    SUM(salario) AS suma_salarios
FROM empleado
GROUP BY departamento
ORDER BY departamento;

-- 11. Mostrar la cantidad de empleados por departamento.
SELECT
    departamento,
    COUNT(*) AS cantidad_empleados
FROM empleado
GROUP BY departamento
ORDER BY cantidad_empleados DESC, departamento;
