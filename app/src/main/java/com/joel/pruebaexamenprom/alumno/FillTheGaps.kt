package com.joel.pruebaexamenprom.alumno

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

class FillTheGaps : AppCompatActivity() {

    private lateinit var conexionDB: ConexionDB
    private var usuarioActual: String? = null  // Usuario que inició sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fill_the_gaps)

        // Inicializar conexión a la BD
        conexionDB = ConexionDB(this)

        // Obtener usuario desde SharedPreferences
        val sharedPreferences = getSharedPreferences("AlumnoPrefs", MODE_PRIVATE)
        usuarioActual = sharedPreferences.getString("nombre_alumno", null)

        if (usuarioActual == null) {
            Toast.makeText(this, "Error: No se encontró el usuario", Toast.LENGTH_SHORT).show()
            finish() // Cerrar la actividad si no hay usuario
            return
        }

        // Referencias a los EditTexts, el texto grande y el botón
        val textoGrande: TextView = findViewById(R.id.texto_instrucciones)
        val editText1: EditText = findViewById(R.id.editText1)
        val editText2: EditText = findViewById(R.id.editText2)
        val editText3: EditText = findViewById(R.id.editText3)
        val editText4: EditText = findViewById(R.id.editText4)
        val btnConfirmar: Button = findViewById(R.id.btn_confirmar)
        var puntos = 0

        // Acción cuando el botón Confirmar es presionado
        btnConfirmar.setOnClickListener {

            // Obtener las respuestas del usuario
            val respuesta1 = editText1.text.toString().lowercase()
            val respuesta2 = editText2.text.toString().lowercase()
            val respuesta3 = editText3.text.toString().lowercase()
            val respuesta4 = editText4.text.toString().lowercase()


            var todasCorrectas = true

            // Validar respuestas
            if (respuesta1 == "cinco") {
                textoGrande.text = textoGrande.text.toString().replace("(1)", "cinco")
                editText1.isEnabled = false  // Deshabilitar el campo
                editText1.isVisible = false
            } else {
                puntos -= 5
                todasCorrectas = false
                Toast.makeText(this, "Vuelve a intentarlo (1)", Toast.LENGTH_SHORT).show()
            }

            if (respuesta2 == "nueve") {
                textoGrande.text = textoGrande.text.toString().replace("(2)", "nueve")
                editText2.isEnabled = false  // Deshabilitar el campo
                editText2.isVisible = false
            } else {
                puntos -= 5
                todasCorrectas = false
                Toast.makeText(this, "Vuelve a intentarlo (2)", Toast.LENGTH_SHORT).show()
            }

            if (respuesta3 == "c") {
                textoGrande.text = textoGrande.text.toString().replace("(3)", "c")
                editText3.isEnabled = false  // Deshabilitar el campo
                editText3.isVisible = false
            } else {
                puntos -= 5
                todasCorrectas = false
                Toast.makeText(this, "Vuelve a intentarlo (3)", Toast.LENGTH_SHORT).show()
            }

            if (respuesta4 == "a") {
                textoGrande.text = textoGrande.text.toString().replace("(4)", "a")
                editText4.isEnabled = false  // Deshabilitar el campo
                editText4.isVisible = false
            } else {
                puntos -= 5
                todasCorrectas = false
                Toast.makeText(this, "Vuelve a intentarlo (4)", Toast.LENGTH_SHORT).show()
            }

            // Si todas las respuestas son correctas
            if (todasCorrectas) {
                Toast.makeText(this, "¡Lo hicimos bien!", Toast.LENGTH_SHORT).show()
                // Sumar 100 puntos
                puntos+=100
                usuarioActual?.let { usuario ->
                    conexionDB.actualizarPuntuacion(usuario, puntos)
                }
                // Volver a la actividad anterior
                finish()
            } else {
                // Restar puntos por fallos
                usuarioActual?.let { usuario ->
                    conexionDB.actualizarPuntuacion(usuario, puntos)
                }
                puntos=0
            }
        }
    }
}
