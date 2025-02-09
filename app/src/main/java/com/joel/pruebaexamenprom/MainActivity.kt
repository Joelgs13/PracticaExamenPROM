package com.joel.pruebaexamenprom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.alumno.MenuAlumno
import com.joel.pruebaexamenprom.bbdd.ConexionDB
import com.joel.pruebaexamenprom.profesor.MenuProfesor
import com.joel.pruebaexamenprom.models.Alumno

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonProfesor = findViewById<Button>(R.id.button_profesor)
        val buttonLogin = findViewById<Button>(R.id.button_login)
        val editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val editTextContrasena = findViewById<EditText>(R.id.editTextContrasena)

        buttonProfesor.setOnClickListener {
            val intent = Intent(this, MenuProfesor::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val usuario = editTextUsuario.text.toString()
            val contrasena = editTextContrasena.text.toString()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                verificarAlumno(usuario, contrasena) { isValid ->
                    if (isValid) {
                        // El alumno existe, redirigir al menú
                        val intent = Intent(this, MenuAlumno::class.java)
                        startActivity(intent)
                    } else {
                        // Mostrar mensaje de error
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun verificarAlumno(usuario: String, contrasena: String, callback: (Boolean) -> Unit) {
        Thread {
            val conexionDB = ConexionDB(this)
            val alumno = conexionDB.selectAlumnoPorCredenciales(usuario, contrasena)

            runOnUiThread {
                if (alumno != null) {
                    // Guardar datos del alumno logueado en SharedPreferences
                    val sharedPreferences = getSharedPreferences("AlumnoPrefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("id_alumno", alumno.idAlumno) // Usar idAlumno en lugar de id
                    editor.putInt("id_grupo", alumno.idGrupo ?: -1) // Manejar idGrupo nulo con -1
                    editor.putString("nombre_alumno", alumno.nombre)
                    editor.apply()

                    callback(true)
                } else {
                    callback(false)
                }
            }
        }.start()
    }



}
