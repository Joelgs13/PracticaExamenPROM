package com.joel.pruebaexamenprom.alumno

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R

class MenuAlumno : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alumno_menu)

        // Asociar los botones con sus respectivas vistas
        val buttonDragAndDrop = findViewById<Button>(R.id.button_drag_and_drop)
        val buttonFillTheGaps = findViewById<Button>(R.id.button_fill_the_gaps)
        val buttonConsultarPuntos = findViewById<Button>(R.id.button_consultar_puntos)

        // Acción para el botón Jugar Drag & Drop
        buttonDragAndDrop.setOnClickListener {
            val intent = Intent(this, DragAndDrop::class.java)
            startActivity(intent)
        }

        // Acción para el botón Jugar Fill The Gaps
        buttonFillTheGaps.setOnClickListener {
            val intent = Intent(this, FillTheGaps::class.java)
            startActivity(intent)
        }

        // Acción para el botón Consultar Puntos
        buttonConsultarPuntos.setOnClickListener {
            val intent = Intent(this, ConsultarPuntosPropios::class.java)
            startActivity(intent)
        }
    }
}
