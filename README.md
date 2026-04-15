# NOVABANK DIGITAL SERVICES – Sistema de Gestión Bancaria

> Proyecto desarrollado en Java 17 que simula un sistema bancario por consola.  
> Permite gestionar clientes, cuentas y operaciones financieras utilizando PostgreSQL mediante JDBC, aplicando arquitectura por capas y buenas prácticas de diseño.

---

## Funcionalidades

### Gestión de clientes
* Crear cliente
* Buscar cliente
* Listar clientes

### Gestión de cuentas
* Crear cuenta asociada a un cliente
* Listar cuentas de un cliente
* Ver información de una cuenta

### Operaciones
* Depósitos
* Retiros
* Transferencias

### Consultas
* Consultar saldo
* Ver movimientos de una cuenta
* Filtrar movimientos de una cuenta por rango de fechas

---

## Estructura del proyecto
El proyecto está organizado por dominios y capas:

![Estructura del Proyecto](docs/estructuraProyecto.png)

---

## Acceso a datos
* Implementación con JDBC
* Uso de PreparedStatement
* Uso de try-with-resources
* Conversión de ResultSet a objetos Java

---

## Patrones
* Repository
* Builder
* Singleton
* Factory

---

## Tecnologías usadas
* Java 17
* Maven
* JUnit 5
* Mockito
* JDBC
* PostgreSQL
* Git

---

## Requisitos del sistema
* Java 17 o superior
* Maven 3.9 o superior
* PostgreSQL instalado y en ejecución

---

## Configuración de la base de datos
* La conexión se gestiona desde `DatabaseConnectionManager`
* Desde ahí podrás gestionar las credenciales

---

## Cómo compilar
`mvn clean compile`

## Cómo ejecutar
`mvn exec:java`

## Cómo ejecutar los tests
`mvn test`

---

## Repositorio
https://github.com/JaviergpNTTDATA/CasoPractico1
