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

/**
 * Actividad principal de la aplicación, que maneja el inicio de sesión tanto para profesores como para alumnos.
 * Proporciona botones para ingresar como profesor o alumno y verificar las credenciales del alumno.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Metodo que se llama cuando se crea la actividad. Aquí se configuran los botones y se asocia
     * el comportamiento de inicio de sesión con la verificación de las credenciales del alumno.
     *
     * @param savedInstanceState El estado guardado de la actividad si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonProfesor = findViewById<Button>(R.id.button_profesor)
        val buttonLogin = findViewById<Button>(R.id.button_login)
        val editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val editTextContrasena = findViewById<EditText>(R.id.editTextContrasena)

        /**
         * Acción a realizar cuando se hace clic en el botón "Profesor". Inicia la actividad MenuProfesor.
         */
        buttonProfesor.setOnClickListener {
            val intent = Intent(this, MenuProfesor::class.java)
            startActivity(intent)
        }

        /**
         * Acción a realizar cuando se hace clic en el botón "Login". Verifica las credenciales del alumno.
         * Si son válidas, se redirige al menú del alumno, si no, se muestra un mensaje de error.
         */
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

    /**
     * Verifica las credenciales del alumno en la base de datos.
     * Si el alumno es válido, se guarda su información en SharedPreferences.
     *
     * @param usuario El nombre de usuario del alumno.
     * @param contrasena La contraseña del alumno.
     * @param callback Función callback que recibe un valor booleano que indica si el alumno es válido.
     */
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
