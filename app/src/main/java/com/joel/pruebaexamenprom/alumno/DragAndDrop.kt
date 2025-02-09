package com.joel.pruebaexamenprom.alumno

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

class DragAndDrop : AppCompatActivity() {

    private lateinit var conexionDB: ConexionDB
    private var usuarioActual: String? = null  // Usuario que inició sesión

    // Contadores de cuántas imágenes han sido correctamente arrastradas
    private var correctas = 0
    private val totalImágenes = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drag_and_drop)

        // Inicializar conexión a la BD
        conexionDB = ConexionDB(this)

        // Obtener usuario desde SharedPreferences
        val sharedPreferences = getSharedPreferences("AlumnoPrefs", Context.MODE_PRIVATE)
        usuarioActual = sharedPreferences.getString("nombre_alumno", null)

        if (usuarioActual == null) {
            Toast.makeText(this, "Error: No se encontró el usuario", Toast.LENGTH_SHORT).show()
            finish() // Cerrar la actividad si no hay usuario
            return
        }

        // Referencias a los TextViews
        val textA: TextView = findViewById(R.id.text_A)
        val textB: TextView = findViewById(R.id.text_B)
        val textC: TextView = findViewById(R.id.text_C)

        // Referencias a las Imágenes
        val imageA: ImageView = findViewById(R.id.image_A)
        val imageB: ImageView = findViewById(R.id.image_B)
        val imageC: ImageView = findViewById(R.id.image_C)

        // Configurar Drag and Drop
        setupDragAndDrop(imageA, textA, "A")
        setupDragAndDrop(imageB, textB, "B")
        setupDragAndDrop(imageC, textC, "C")
    }

    private fun setupDragAndDrop(image: ImageView, target: TextView, letter: String) {
        // Configurar el Long Click en la imagen para iniciar el arrastre
        image.setOnLongClickListener {
            val clipData = ClipData.newPlainText("letter", letter)
            val shadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(clipData, shadowBuilder, it, 0)
            it.visibility = View.INVISIBLE  // Ocultar mientras se arrastra
            true
        }

        // Configurar el evento de arrastre en el TextView
        target.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DRAG_EXITED -> true
                DragEvent.ACTION_DROP -> {
                    val draggedView = event.localState as View
                    val clipData = event.clipData.getItemAt(0).text.toString()

                    if (clipData == letter) {
                        // Correcto: ocultar el texto y la imagen
                        Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
                        draggedView.visibility = View.GONE
                        target.visibility = View.GONE

                        // Sumar 25 puntos al usuario
                        usuarioActual?.let { usuario ->
                            conexionDB.actualizarPuntuacion(usuario, 25)
                        }

                        correctas++

                        // Verificar si todas las imágenes fueron arrastradas correctamente
                        if (correctas == totalImágenes) {
                            // Ocultar todas las imágenes y finalizar la actividad
                            hideImagesAndFinish()
                        }

                    } else {
                        // Incorrecto: devolver la imagen a su estado original
                        Toast.makeText(this, "Incorrecto, intenta de nuevo", Toast.LENGTH_SHORT).show()
                        draggedView.visibility = View.VISIBLE

                        // Restar 10 puntos, pero sin bajar de 0
                        usuarioActual?.let { usuario ->
                            conexionDB.actualizarPuntuacion(usuario, -10)
                        }
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    // Si no se soltó correctamente, hacer visible la imagen de nuevo
                    if (!event.result) {
                        val draggedView = event.localState as View
                        draggedView.visibility = View.VISIBLE
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun hideImagesAndFinish() {
        // Ocultar todas las imágenes
        val imageA: ImageView = findViewById(R.id.image_A)
        val imageB: ImageView = findViewById(R.id.image_B)
        val imageC: ImageView = findViewById(R.id.image_C)

        imageA.visibility = View.GONE
        imageB.visibility = View.GONE
        imageC.visibility = View.GONE

        // Volver al menú anterior (MenuAlumno)
        Toast.makeText(this, "¡Felicidades, has completado la actividad!", Toast.LENGTH_SHORT).show()

        // Esperar un segundo para mostrar el mensaje antes de regresar
        android.os.Handler().postDelayed({
            finish()  // Finaliza la actividad y vuelve a la anterior
        }, 1000)
    }
}
