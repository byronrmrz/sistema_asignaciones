# Sistema de Asignaciones - Proyecto Universitario

Este proyecto es un sistema de asignaciones desarrollado para tareas de la universidad. El sistema permite gestionar asignaciones de personas a cursos, verificación de cursos y manejo de datos relacionados con pagos y facturación. La aplicación consta de tres pantallas principales y utiliza una base de datos SQLite para almacenar y gestionar la información.

## Características

- **Pantalla 1: Asignaciones**
  - Permite registrar los datos de una persona a asignar.
  - Muestra los cursos disponibles y permite seleccionarlos.
  - Registra los datos de pago y facturación.
  - Los cursos seleccionados se agregan a un listado de cursos a asignar.

- **Pantalla 2: Verificación de Cursos**
  - Muestra los cursos que han sido asignados a las personas.
  
- **Pantalla 3: Base de Datos**
  - Utiliza SQLite para almacenar y gestionar los datos de las asignaciones, personas, cursos y pagos.
  
## Requisitos

- **Java**: Se recomienda usar Java 11 o superior.
- **SQLite**: Base de datos utilizada para gestionar la información.
- **IDE**: Este proyecto fue desarrollado utilizando IntelliJ IDEA.

## Uso

1. **Pantalla de Asignaciones**: 
   - Ingresar los datos de la persona a asignar.
   - Seleccionar los cursos disponibles.
   - Registrar la información de pago y facturación.
   - Los cursos seleccionados se agregarán automáticamente al listado de cursos asignados.

2. **Pantalla de Verificación de Cursos**: 
   - Visualizar los cursos asignados a las personas.

3. **Base de Datos**: 
   - La base de datos SQLite se utiliza para guardar la información de asignaciones, personas, cursos y pagos. Los datos se actualizan y consultan a través de la interfaz.

## Contribuciones

Si deseas contribuir a este proyecto, siéntete libre de hacer un **fork** y enviar tus **pull requests**. ¡Cualquier mejora es bienvenida!

## Licencia

Este proyecto está bajo la licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.
