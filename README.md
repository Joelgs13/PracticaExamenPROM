# Prueba Examen Promoción

Este es un proyecto de prueba para un sistema de gestión de alumnos y grupos, en el que los profesores pueden realizar diversas acciones como crear alumnos, grupos, asignar alumnos a grupos y consultar puntuaciones. Los alumnos pueden iniciar sesión en el sistema para ver su información personal y sus puntuaciones.

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal para el desarrollo de la aplicación Android.
- **Android SDK**: Herramientas y bibliotecas utilizadas para desarrollar la aplicación Android.
- **SQLite**: Base de datos utilizada para almacenar los datos de los alumnos, grupos y puntuaciones.
- **RecyclerView**: Componente de UI utilizado para mostrar listas de alumnos.

## Funcionalidades

### Funcionalidades para el Profesor

- **Crear Alumno**: El profesor puede agregar nuevos alumnos al sistema, proporcionando un nombre de usuario y una contraseña.
- **Crear Grupo**: El profesor puede crear nuevos grupos dentro del sistema.
- **Asignar Alumno a Grupo**: El profesor puede asignar a un alumno a un grupo determinado.
- **Consultar Puntuación**: El profesor puede consultar las puntuaciones de los alumnos por grupo.

### Funcionalidades para el Alumno

- **Iniciar Sesión**: El alumno puede iniciar sesión con su nombre de usuario y contraseña.
- **Consultar Información**: El alumno puede ver su puntuación y grupo al iniciar sesión.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

/app /src /main /java /com/joel/pruebaexamenprom /alumno MenuAlumno.kt # Menú principal del alumno /profesor MenuProfesor.kt # Menú principal del profesor CrearAlumno.kt # Pantalla para crear un nuevo alumno CrearGrupo.kt # Pantalla para crear un nuevo grupo AsignarAlumnoAGrupo.kt # Pantalla para asignar un alumno a un grupo ConsultarPuntuacion.kt # Pantalla para consultar puntuaciones ConexionDB.kt # Clase para manejar la conexión y operaciones de la base de datos MainActivity.kt # Pantalla de inicio donde el alumno o profesor inicia sesión models Alumno.kt # Modelo de datos del alumno Grupo.kt # Modelo de datos del grupo

markdown
Copiar
Editar

### **Estructura de las Carpetas**

1. **com.joel.pruebaexamenprom.alumno**: Contiene las actividades relacionadas con el flujo del alumno.
2. **com.joel.pruebaexamenprom.profesor**: Contiene las actividades y pantallas del profesor.
3. **com.joel.pruebaexamenprom.models**: Contiene las clases de modelos de datos (Alumno, Grupo, etc.).
4. **com.joel.pruebaexamenprom.bbdd**: Contiene las clases necesarias para la interacción con la base de datos (ConexionDB).

## Instrucciones de Instalación

Para configurar este proyecto en tu máquina local, sigue estos pasos:

1. **Clona el repositorio**:
    ```bash
    git clone https://github.com/tu-usuario/prueba-examen-prom.git
    ```
   
2. **Abre el proyecto en Android Studio**:
    - Una vez clonado, abre Android Studio y selecciona "Open an existing project".
    - Navega hasta la carpeta del proyecto y selecciónala.

3. **Configura el SDK de Android**:
    - Asegúrate de que tienes el SDK de Android correctamente configurado y actualizado en tu máquina.
    - También, verifica que las dependencias de gradle estén actualizadas.

4. **Compila y ejecuta la aplicación**:
    - Conecta un dispositivo Android o usa un emulador para ejecutar la aplicación.
    - En Android Studio, selecciona el dispositivo y presiona el botón de "Run".

## Base de Datos

La aplicación utiliza SQLite para manejar la persistencia de datos de alumnos, grupos y puntuaciones. Algunas de las funciones que se realizan con la base de datos son:

- **Obtener todos los alumnos**.
- **Obtener todos los grupos**.
- **Verificar si un alumno ya existe**.
- **Crear un nuevo alumno o grupo**.
- **Asignar un alumno a un grupo**.
- **Consultar puntuaciones de los alumnos**.

## Screenshots

Puedes agregar aquí algunas capturas de pantalla del funcionamiento de la aplicación para mostrar cómo luce la interfaz.

## Contribuciones

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar este proyecto, por favor haz un fork del repositorio y envía tus cambios a través de un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para mas detalles.
