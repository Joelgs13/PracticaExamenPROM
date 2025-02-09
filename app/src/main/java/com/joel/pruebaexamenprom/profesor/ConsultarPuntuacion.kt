package com.joel.pruebaexamenprom.profesor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

class ConsultarPuntuacion : AppCompatActivity() {

    private lateinit var spinnerGrupo: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consultar_puntuacion) // Carga el layout "consultar_puntuacion.xml"

        spinnerGrupo = findViewById(R.id.spinner_grupo)
        recyclerView = findViewById(R.id.recycler_alumnos)

        // Configuración del RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar los grupos en el Spinner
        Thread {
            val conexionDB = ConexionDB(applicationContext)
            val listaGrupos = conexionDB.obtenerGrupos()

            runOnUiThread {
                // Cargar los grupos en el Spinner
                val adapterGrupos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaGrupos)
                adapterGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGrupo.adapter = adapterGrupos
            }
        }.start()

        spinnerGrupo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val grupoSeleccionado = spinnerGrupo.selectedItem.toString()
                cargarAlumnosPorGrupo(grupoSeleccionado)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Puedes dejarlo vacío si no necesitas manejar el caso cuando no hay selección
            }
        })
    }

    private fun cargarAlumnosPorGrupo(grupoSeleccionado: String) {
        Thread {
            val conexionDB = ConexionDB(applicationContext)

            // Obtener los ID del grupo seleccionado
            val idGrupo = conexionDB.obtenerIdPorNombreGrupo(grupoSeleccionado)

            if (idGrupo != null) {
                // Obtener la lista de alumnos con el mismo id_grupo
                val alumnos = conexionDB.obtenerAlumnosPorGrupo(idGrupo)

                runOnUiThread {
                    // Actualizar el RecyclerView con los datos obtenidos
                    adapter = AlumnoAdapter(alumnos)
                    recyclerView.adapter = adapter
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this, "Grupo no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
