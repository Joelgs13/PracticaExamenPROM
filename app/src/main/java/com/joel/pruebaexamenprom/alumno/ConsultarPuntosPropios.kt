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

/**
 * Actividad que permite consultar los puntos de los alumnos pertenecientes a un grupo específico.
 *
 * Esta clase obtiene el ID del grupo del alumno desde las preferencias compartidas (SharedPreferences),
 * y luego carga la lista de alumnos asociados a ese grupo desde la base de datos.
 * La información se muestra en un `RecyclerView`, donde cada item corresponde a un alumno y su puntuación.
 *
 * @see ConexionDB para la conexión y consulta a la base de datos.
 * @see AlumnoAdapter para la adaptación de los datos de los alumnos a las vistas del `RecyclerView`.
 */
class ConsultarPuntosPropios : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter

    /**
     * Metodo de la actividad que se llama al crear la actividad.
     * Configura el `RecyclerView` y obtiene el ID del grupo del alumno desde SharedPreferences.
     * Si se obtiene correctamente el ID del grupo, se procede a cargar los alumnos asociados.
     * Si ocurre un error al obtener el grupo, se muestra un `Toast` de error.
     *
     * @param savedInstanceState Bundle que contiene el estado previamente guardado de la actividad.
     */
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

    /**
     * Carga los alumnos pertenecientes a un grupo específico.
     * Este metodo se ejecuta en un hilo secundario para evitar bloquear el hilo principal de la interfaz de usuario.
     * Una vez que se obtienen los datos de los alumnos, se configura el adaptador del `RecyclerView`
     * y se muestra la lista de alumnos en la interfaz.
     *
     * @param idGrupo El ID del grupo cuyos alumnos se desean cargar.
     */
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
