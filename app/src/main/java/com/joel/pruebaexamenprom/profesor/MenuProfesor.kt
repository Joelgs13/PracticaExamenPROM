package com.joel.pruebaexamenprom.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.Button // Asegúrate de importar Button
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R

/**
 * Actividad principal del profesor que muestra el menú con las opciones disponibles.
 * El menú incluye botones para navegar a distintas actividades, como crear alumno,
 * crear grupo, asignar alumno a grupo y consultar puntuaciones.
 */
class MenuProfesor : AppCompatActivity() {

    /**
     * Metodo que se llama cuando se crea la actividad. Aquí se configuran los botones
     * y se asocian las acciones de navegación a otras actividades.
     *
     * @param savedInstanceState El estado guardado de la actividad si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profesor_menu)

        // Asociar los botones a las variables usando findViewById
        val buttonCrearAlumno = findViewById<Button>(R.id.button_crear_alumno)
        val buttonCrearGrupo = findViewById<Button>(R.id.button_crear_grupo)
        val buttonAsignarAlumno = findViewById<Button>(R.id.button_asignar_alumno)
        val buttonConsultarPuntuacion = findViewById<Button>(R.id.button_consultar_puntuacion)

        /**
         * Acción a realizar cuando se hace clic en el botón "Crear Alumno".
         * Inicia la actividad CrearAlumno.
         */
        buttonCrearAlumno.setOnClickListener {
            val intent = Intent(this, CrearAlumno::class.java)
            startActivity(intent)
        }

        /**
         * Acción a realizar cuando se hace clic en el botón "Crear Grupo".
         * Inicia la actividad CrearGrupo.
         */
        buttonCrearGrupo.setOnClickListener {
            val intent = Intent(this, CrearGrupo::class.java)
            startActivity(intent)
        }

        /**
         * Acción a realizar cuando se hace clic en el botón "Asignar Alumno a Grupo".
         * Inicia la actividad AsignarAlumnoAGrupo.
         */
        buttonAsignarAlumno.setOnClickListener {
            val intent = Intent(this, AsignarAlumnoAGrupo::class.java)
            startActivity(intent)
        }

        /**
         * Acción a realizar cuando se hace clic en el botón "Consultar Puntuación".
         * Inicia la actividad ConsultarPuntuacion.
         */
        buttonConsultarPuntuacion.setOnClickListener {
            val intent = Intent(this, ConsultarPuntuacion::class.java)
            startActivity(intent)
        }
    }
}
