package com.joel.pruebaexamenprom.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.Button // Asegúrate de importar Button
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R

class MenuProfesor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profesor_menu)

        // Asociar los botones a las variables usando findViewById
        val buttonCrearAlumno = findViewById<Button>(R.id.button_crear_alumno)
        val buttonCrearGrupo = findViewById<Button>(R.id.button_crear_grupo)
        val buttonAsignarAlumno = findViewById<Button>(R.id.button_asignar_alumno)
        val buttonConsultarPuntuacion = findViewById<Button>(R.id.button_consultar_puntuacion)

        // Llamar a la actividad CrearAlumno cuando se haga clic en el botón Crear Alumno
        buttonCrearAlumno.setOnClickListener {
            val intent = Intent(this, CrearAlumno::class.java)
            startActivity(intent)
        }

        // Llamar a la actividad CrearGrupo cuando se haga clic en el botón Crear Grupo
        buttonCrearGrupo.setOnClickListener {
            val intent = Intent(this, CrearGrupo::class.java)
            startActivity(intent)
        }

        // Llamar a la actividad AsignarAlumnoAGrupo cuando se haga clic en el botón Asignar Alumno a Grupo
        buttonAsignarAlumno.setOnClickListener {
            val intent = Intent(this, AsignarAlumnoAGrupo::class.java)
            startActivity(intent)
        }

        // Llamar a la actividad ConsultarPuntuacion cuando se haga clic en el botón Consultar Puntuación
        buttonConsultarPuntuacion.setOnClickListener {
            val intent = Intent(this, ConsultarPuntuacion::class.java)
            startActivity(intent)
        }
    }
}
