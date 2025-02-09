package com.joel.pruebaexamenprom.alumno

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R

class DragAndDrop : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drag_and_drop)

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
                    } else {
                        // Incorrecto: devolver la imagen a su estado original
                        Toast.makeText(this, "Incorrecto, intenta de nuevo", Toast.LENGTH_SHORT).show()
                        draggedView.visibility = View.VISIBLE
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
}
