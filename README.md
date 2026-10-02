# Sistema de Registro de Empleados

Proyecto desarrollado en JavaFX con conexión a PostgreSQL para registrar y consultar información de empleados.

La aplicación permite ingresar datos de empleados mediante un formulario, almacenarlos en la base de datos y posteriormente mostrarlos en un TableView.

## Integrantes

- Fanor Velásquez
- Hendrix Aguirre
- Jarold Montealto

## Objetivo

Desarrollar una aplicación de escritorio utilizando JavaFX y PostgreSQL que permita registrar información de empleados y visualizar los registros almacenados en la base de datos.

## Tecnologías utilizadas

- Java
- JavaFX
- FXML
- PostgreSQL
- JDBC
- IntelliJ IDEA
- Scene Builder

## Funcionalidades

La aplicación permite:

- Registrar empleados.
- Mostrar empleados registrados.
- Consultar información desde PostgreSQL.
- Mostrar los resultados en un TableView.
- Limpiar los campos del formulario.
- Actualizar la tabla de empleados.
- Realizar consultas desde Java.
- Cerrar la aplicación mediante el botón Salir.

## Datos del empleado

Cada empleado contiene la siguiente información:

- ID
- Nombres
- Apellidos
- Cédula
- Correo
- Teléfono
- Cargo
- Departamento
- Salario
- Fecha de contratación
- Estado

## Estructura del proyecto

```text
src
└── main
    ├── java
    │   └── ni.edu.uam.empleadossistema
    │       ├── controller
    │       │   └── EmpleadoController.java
    │       ├── database
    │       │   └── DatabaseConnection.java
    |       |   └── TestConexion.java
    │       └── model
    │           └── Empleado.java
    │
    └── resources
        └── ni.edu.uam.empleadossistema
            └── empleado-view.fxml
