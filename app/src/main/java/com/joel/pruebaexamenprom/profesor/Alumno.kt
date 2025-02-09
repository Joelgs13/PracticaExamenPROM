package com.joel.pruebaexamenprom.profesor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joel.pruebaexamenprom.R

data class Alumno(val nombre: String, val puntuacion: Int)

class AlumnoAdapter(private val alumnos: List<Alumno>) : RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    class AlumnoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreAlumno: TextView = view.findViewById(R.id.nombre_alumno)
        val puntuacion: TextView = view.findViewById(R.id.puntuacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = alumnos[position]
        holder.nombreAlumno.text = alumno.nombre
        holder.puntuacion.text = alumno.puntuacion.toString()
    }

    override fun getItemCount(): Int {
        return alumnos.size
    }
}
