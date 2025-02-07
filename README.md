# Sistema de Asignaciones - Proyecto Universitario

Este es un sistema de asignaciones desarrollado para la universidad, que permite gestionar la asignación de cursos a personas. El sistema tiene tres pantallas principales y se conecta a una base de datos SQLite para almacenar y gestionar la información de los usuarios, cursos y pagos.

## Funcionalidades

El sistema incluye las siguientes pantallas:

### Pantalla 1: Menú Principal
- **Botón 1: Asignaciones**: Dirige al usuario a la pantalla de asignación de cursos.
- **Botón 2: Verificación de Cursos**: Permite ver los cursos que han sido asignados.

### Pantalla 2: Asignaciones
- En esta pantalla se registran los datos de la persona a asignar, incluyendo:
  - Nombre, dirección, y demás datos relevantes de la persona.
- Se presentan los **cursos disponibles** para seleccionar.
- Una vez que se seleccionan los cursos, se agregan a un listado de **cursos a asignar**.
- Se incluye una sección de **datos de pago y facturación**, para ingresar la información necesaria para completar la asignación.

### Pantalla 3: Verificación de Cursos
- Muestra una lista de los **cursos asignados** a las personas registradas en el sistema.

## Base de Datos
El sistema utiliza **SQLite** para almacenar la siguiente información:
- Datos personales de las personas asignadas.
- Cursos disponibles.
- Información de pagos y facturación.

## Tecnologías Utilizadas
- **Lenguaje de programación**: Java
- **IDE**: IntelliJ IDEA
- **Base de datos**: SQLite
- **Interfaz de usuario**: [Especificar librerías si las usas, como JavaFX, Swing, etc.]

## Requisitos del Sistema
- Tener instalado **Java** (preferentemente versión 8 o superior).
- Tener instalado **IntelliJ IDEA**.
- Tener configurada una base de datos **SQLite**.

## Instrucciones de Uso

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en **IntelliJ IDEA**.
3. Asegúrate de tener la JDK instalada en tu entorno de desarrollo.
4. Si es necesario, configura la base de datos SQLite.
5. Ejecuta el archivo `Main.java` o el archivo principal de la aplicación desde IntelliJ para iniciar el sistema.

```bash
git clone <repositorio_url>
cd <directorio_del_proyecto>
<instrucciones_para_ejecutar>
