# Sistema de Gestión de Incidencias para Taller Mecánico

## Descripción

Aplicación de escritorio desarrollada en **Java Swing** para la gestión de incidencias en un taller mecánico.

El sistema permite registrar, asignar, gestionar y resolver incidencias mediante una interfaz gráfica intuitiva conectada a una base de datos **MySQL**. Incluye control de acceso por roles, seguimiento del ciclo de vida de las incidencias e histórico de cambios realizados por los usuarios.

Este proyecto fue desarrollado como práctica de Desarrollo de Aplicaciones Multiplataforma (DAM), aplicando conceptos de arquitectura de software, persistencia de datos y diseño de interfaces gráficas.

---

## Tecnologías utilizadas

* Java
* Java Swing
* MySQL
* JDBC
* FlatLaf
* MVC (Model-View-Controller)
* DAO (Data Access Object)

---

## Características destacadas

- Sistema de autenticación con tres perfiles de usuario.
- Gestión completa de incidencias.
- Control de permisos según rol.
- Histórico de resolución y cambios.
- Sistema de reapertura de incidencias.
- Interfaz gráfica desarrollada con Java Swing.
- Persistencia de datos mediante MySQL y JDBC.
- Arquitectura MVC y patrón DAO.

## Funcionalidades principales

### Gestión de incidencias

* Alta de incidencias.
* Modificación de incidencias.
* Cambio de estado.
* Reapertura de incidencias cerradas.
* Cierre de incidencias.
* Baja lógica de registros.
* Gestión de prioridades.
* Consulta detallada de información.

### Gestión de usuarios

* Inicio de sesión mediante autenticación.
* Control de acceso según perfil.
* Administración de usuarios.
* Gestión de técnicos asignados.

### Seguimiento e historial

* Registro histórico de cambios.
* Trazabilidad de estados y prioridades.
* Asociación entre incidencias reabiertas y originales.

### Búsqueda y filtrado

* Filtrado por estado.
* Filtrado por prioridad.
* Filtrado por técnico asignado.
* Búsqueda textual sobre incidencias.

---

## Roles del sistema

### Usuario

* Crear incidencias.
* Consultar sus incidencias.
* Modificar incidencias pendientes.

### Técnico

* Gestionar incidencias.
* Cambiar estados.
* Resolver incidencias.
* Reabrir incidencias cerradas.
* Modificar prioridades.

### Administrador

* Acceso completo al sistema.
* Gestión de usuarios.
* Gestión de categorías.
* Eliminación lógica de registros.

---

## Arquitectura del proyecto

El proyecto sigue una arquitectura basada en el patrón **MVC (Modelo - Vista - Controlador)**.

```text
Modelo/
Vista/
Controlador/
```

Además, se utiliza el patrón **DAO** para aislar el acceso a la base de datos y facilitar el mantenimiento de la aplicación.

---

## Base de datos

La aplicación utiliza MySQL como sistema gestor de bases de datos.

Tablas principales:

```text
usuarios
incidencias
historico_resolucion
```

Características implementadas:

* Claves foráneas.
* Integridad referencial.
* Histórico de cambios.
* Baja lógica de registros.

## Instalación

1. Crear una base de datos MySQL.
2. Ejecutar el script:

sql/taller_mecanico.sql

3. Configurar la conexión JDBC.
4. Ejecutar la aplicación.

---

## Flujo de trabajo de una incidencia

```text
PENDIENTE
    ↓
EN PROCESO
    ↓
ESPERA
    ↓
CERRADA

REAPERTURA
    ↓
NUEVA INCIDENCIA VINCULADA
```

---

## Capturas de pantalla

### Inicio de sesión

La aplicación permite autenticarse con distintos perfiles de usuario.

![Login](docs/capturas/login.png)

---

### Vista Administrador

El administrador dispone de acceso completo a la gestión de incidencias, usuarios, técnicos y categorías.

![Administrador](docs/capturas/cuenta_admin.png)

---

### Vista Técnico

Los técnicos pueden gestionar incidencias, modificar estados, reasignar prioridades y consultar históricos de resolución.

![Técnico](docs/capturas/cuenta_tecnico.png)

---

### Vista Usuario

Los usuarios pueden crear incidencias y consultar el estado de las incidencias asociadas a su cuenta.

![Usuario](docs/capturas/cuenta_usuario.png)

---

## Instalación

1. Clonar el repositorio.

```bash
git clone https://github.com/tuusuario/nombre-repositorio.git
```

2. Crear la base de datos MySQL.

3. Ejecutar los scripts SQL incluidos en el proyecto.

4. Configurar los parámetros de conexión en la aplicación.

5. Ejecutar el proyecto desde tu IDE (NetBeans, IntelliJ IDEA o Eclipse).

---

## Posibles mejoras futuras

* Exportación de informes a PDF y Excel.
* Dashboard de métricas.
* Notificaciones por correo electrónico.
* API REST para integración con otros sistemas.
* Soporte multidioma.

---

## Autor

Proyecto desarrollado como parte de su formación en Desarrollo de Aplicaciones Multiplataforma (DAM).
