package com.joel.pruebaexamenprom.models

data class Alumno(
    var idAlumno: Int = 0,
    var nombre: String,
    var contraseña: String,
    var puntuacion: Int,
    var idGrupo: Int? = null
)
