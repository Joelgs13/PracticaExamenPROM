package com.joel.pruebaexamenprom.alumno

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB
import com.joel.pruebaexamenprom.profesor.AlumnoAdapter
import com.joel.pruebaexamenprom.models.Alumno

class ConsultarPuntosPropios : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consultar_puntos_propios)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener ID del grupo desde SharedPreferences
        val sharedPreferences = getSharedPreferences("AlumnoPrefs", Context.MODE_PRIVATE)
        val idGrupo = sharedPreferences.getInt("id_grupo", -1)

        if (idGrupo != -1) {
            cargarAlumnosPorGrupo(idGrupo)
        } else {
            Toast.makeText(this, "Error al obtener grupo del alumno", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarAlumnosPorGrupo(idGrupo: Int) {
        Thread {
            val conexionDB = ConexionDB(applicationContext)
            val alumnos = conexionDB.obtenerAlumnosPorGrupo(idGrupo)

            runOnUiThread {
                adapter = AlumnoAdapter(alumnos)
                recyclerView.adapter = adapter
            }
        }.start()
    }
}
