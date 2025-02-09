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
            val resultado = conexionDB.selectAlumnoPorCredenciales(usuario, contrasena)

            // Usamos el callback para devolver el resultado al hilo principal
            runOnUiThread {
                callback(resultado)
            }
        }.start()
    }

}
