package com.joel.pruebaexamenprom.profesor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

class CrearAlumno : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_alumno)

        val editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val editTextContrasena = findViewById<EditText>(R.id.editTextContrasena)
        val buttonCrear = findViewById<Button>(R.id.button_crear)

        buttonCrear.setOnClickListener {
            val usuario = editTextUsuario.text.toString()
            val contrasenia = editTextContrasena.text.toString()

            // Validamos los campos
            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Usamos un Thread para ejecutar la verificación y la inserción en segundo plano
                Thread {
                    val conexionDB = ConexionDB(applicationContext)

                    // Verificamos si el alumno ya existe
                    val alumnoExiste = conexionDB.alumnoExiste(usuario, contrasenia)

                    if (alumnoExiste) {
                        // Si el alumno ya existe, mostramos un mensaje en el hilo principal
                        runOnUiThread {
                            Toast.makeText(this, "El alumno ya existe", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Si el alumno no existe, insertamos el nuevo alumno
                        val resultado = conexionDB.insertAlumno(usuario, contrasenia)

                        // Actualizamos la UI en el hilo principal después de completar la tarea
                        runOnUiThread {
                            if (resultado) {
                                Toast.makeText(this, "Alumno creado exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Error al crear alumno", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }.start()
            }
        }
    }
}
