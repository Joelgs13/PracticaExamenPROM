package com.joel.pruebaexamenprom.profesor

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

class AsignarAlumnoAGrupo : AppCompatActivity() {

    private lateinit var spinnerAlumno: Spinner
    private lateinit var spinnerGrupo: Spinner
    private lateinit var buttonAsociar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asignar_a_grupo_alumno) // Carga el layout "asignar_a_grupo.xml"

        spinnerAlumno = findViewById(R.id.spinner_alumno)
        spinnerGrupo = findViewById(R.id.spinner_grupo)
        buttonAsociar = findViewById(R.id.button_asociar)

        // Cargar los datos de los alumnos y grupos en segundo plano
        Thread {
            val conexionDB = ConexionDB(applicationContext)

            val listaAlumnos = conexionDB.obtenerAlumnos()  // Obtener los alumnos
            val listaGrupos = conexionDB.obtenerGrupos()  // Obtener los grupos

            // Cargar los datos en el hilo principal
            runOnUiThread {
                // Cargar los alumnos en el spinner
                val adapterAlumnos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaAlumnos)
                adapterAlumnos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAlumno.adapter = adapterAlumnos

                // Cargar los grupos en el spinner
                val adapterGrupos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaGrupos)
                adapterGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGrupo.adapter = adapterGrupos
            }
        }.start()

        // Acción del botón Asociar
        buttonAsociar.setOnClickListener {
            val alumnoSeleccionado = spinnerAlumno.selectedItem.toString()
            val grupoSeleccionado = spinnerGrupo.selectedItem.toString()

            if (alumnoSeleccionado.isEmpty() || grupoSeleccionado.isEmpty()) {
                Toast.makeText(this, "Por favor seleccione un alumno y un grupo", Toast.LENGTH_SHORT).show()
            } else {
                // Obtener los IDs correspondientes del alumno y del grupo seleccionados
                Thread {
                    val conexionDB = ConexionDB(applicationContext)

                    val idAlumno = conexionDB.obtenerIdPorNombreAlumno(alumnoSeleccionado)
                    val idGrupo = conexionDB.obtenerIdPorNombreGrupo(grupoSeleccionado)

                    if (idAlumno != null && idGrupo != null) {
                        // Asignar el alumno al grupo
                        val resultado = conexionDB.asignarAlumnoAGrupo(idAlumno, idGrupo)

                        // Notificar el resultado en el hilo principal
                        runOnUiThread {
                            if (resultado) {
                                Toast.makeText(this, "Alumno asignado al grupo exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Error al asignar el alumno", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "No se encontró el alumno o el grupo", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            }
        }
    }
}
