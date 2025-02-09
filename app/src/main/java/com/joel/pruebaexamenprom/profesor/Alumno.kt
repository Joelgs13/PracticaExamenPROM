package com.joel.pruebaexamenprom.profesor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.models.Alumno  // Usar la clase correcta de models

/**
 * Adapter para mostrar una lista de alumnos en un RecyclerView.
 *
 * @param alumnos Lista de objetos Alumno que se van a mostrar en el RecyclerView.
 */
class AlumnoAdapter(private val alumnos: List<Alumno>) : RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    /**
     * ViewHolder que mantiene las vistas correspondientes a cada elemento de la lista de alumnos.
     *
     * @param view Vista del item de alumno que se va a mostrar en el RecyclerView.
     */
    class AlumnoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreAlumno: TextView = view.findViewById(R.id.nombre_alumno)
        val puntuacion: TextView = view.findViewById(R.id.puntuacion)
    }

    /**
     * Crea una nueva vista de tipo AlumnoViewHolder.
     *
     * @param parent El contenedor donde se agregará la nueva vista.
     * @param viewType El tipo de vista.
     * @return Una instancia de AlumnoViewHolder que representa un item de la lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder(view)
    }

    /**
     * Asocia los datos del alumno a las vistas correspondientes en el ViewHolder.
     *
     * @param holder El ViewHolder que contiene las vistas.
     * @param position La posición del item en la lista de alumnos.
     */
    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = alumnos[position]
        holder.nombreAlumno.text = alumno.nombre
        holder.puntuacion.text = alumno.puntuacion.toString()
    }

    /**
     * Devuelve la cantidad total de elementos en la lista de alumnos.
     *
     * @return El número total de alumnos en la lista.
     */
    override fun getItemCount(): Int {
        return alumnos.size
    }
}
