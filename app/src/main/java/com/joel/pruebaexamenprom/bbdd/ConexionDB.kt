package com.joel.pruebaexamenprom.bbdd

import android.content.Context
import com.joel.pruebaexamenprom.models.Alumno
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Properties

class ConexionDB(context: Context) {
    private val dbUrl: String
    private val dbUser: String
    private val dbPassword: String

    init {
        // Crear instancia de Properties
        val properties = Properties()

        // Cargar el archivo .properties
        try {
            // Usar el Context para acceder a assets
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Obtener valores de las propiedades
        dbUrl = properties.getProperty("dburl") ?: throw IllegalArgumentException("db_url no definido")
        dbUser = properties.getProperty("user") ?: throw IllegalArgumentException("db_user no definido")
        dbPassword = properties.getProperty("password") ?: throw IllegalArgumentException("db_password no definido")
    }

    fun obtenerConexion(): Connection? {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    // Modificamos la función insertAlumno para cumplir con los requisitos
    fun insertAlumno(usuario: String, contrasenia: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO Alumno (nombre, contrasenia, puntuacion, id_grupo) VALUES (?, ?, ?, ?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, contrasenia)  // Usamos el nombre de usuario como contraseña
                statement.setInt(3, 0)  // Puntuación inicializada a 0
                statement.setNull(4, java.sql.Types.NULL)  // id_grupo será NULL
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    fun alumnoExiste(usuario: String, contrasenia: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                // Consulta para verificar si ya existe un alumno con las mismas credenciales
                val query = "SELECT COUNT(*) FROM Alumno WHERE nombre = ? AND contrasenia = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, contrasenia)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true  // El alumno ya existe
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return false  // El alumno no existe
    }


    // Función para insertar un nuevo Grupo en la tabla Grupo
    fun insertGrupo(nombreGrupo: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "INSERT INTO Grupo (nombre_grupo) VALUES (?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombreGrupo)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    // Función para verificar si un grupo ya existe por su nombre
    fun grupoExiste(nombreGrupo: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                // Consulta para verificar si ya existe un grupo con el mismo nombre
                val query = "SELECT COUNT(*) FROM Grupo WHERE nombre_grupo = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombreGrupo)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true  // El grupo ya existe
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return false  // El grupo no existe
    }


    fun selectAlumnoPorCredenciales(nombre: String, contrasenia: String): Alumno? {
        val conexion = obtenerConexion()
        var alumno: Alumno? = null

        if (conexion != null) {
            try {
                val query = "SELECT id_alumno, nombre, contrasenia, puntuacion, id_grupo FROM Alumno WHERE nombre = ? AND contrasenia = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombre)
                statement.setString(2, contrasenia)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next()) {
                    alumno = Alumno(
                        idAlumno = resultSet.getInt("id_alumno"),
                        nombre = resultSet.getString("nombre"),
                        contraseña = resultSet.getString("contrasenia"),  // Usar "contrasenia" en lugar de "contraseña"
                        puntuacion = resultSet.getInt("puntuacion"),
                        idGrupo = if (resultSet.getObject("id_grupo") != null) resultSet.getInt("id_grupo") else null
                    )
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }

        return alumno
    }






    // Función para obtener todos los alumnos
    fun obtenerAlumnos(): List<String> {
        val conexion = obtenerConexion()
        val listaAlumnos = mutableListOf<String>()

        if (conexion != null) {
            try {
                val query = "SELECT id_alumno, nombre FROM Alumno"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val nombre = resultSet.getString("nombre")
                    listaAlumnos.add(nombre)
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return listaAlumnos
    }

    // Función para obtener todos los grupos
    fun obtenerGrupos(): List<String> {
        val conexion = obtenerConexion()
        val listaGrupos = mutableListOf<String>()

        if (conexion != null) {
            try {
                val query = "SELECT id_grupo, nombre_grupo FROM Grupo"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val nombreGrupo = resultSet.getString("nombre_grupo")
                    listaGrupos.add(nombreGrupo)
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return listaGrupos
    }

    // Función para asociar un alumno a un grupo
    fun asignarAlumnoAGrupo(idAlumno: Int, idGrupo: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "UPDATE Alumno SET id_grupo = ? WHERE id_alumno = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, idGrupo)
                statement.setInt(2, idAlumno)
                val rowsAffected = statement.executeUpdate()
                return rowsAffected > 0  // Si se actualizó al menos una fila, es exitoso
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return false
    }

    // Función para obtener el ID del alumno por su nombre
    fun obtenerIdPorNombreAlumno(nombreAlumno: String): Int? {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "SELECT id_alumno FROM Alumno WHERE nombre = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombreAlumno)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next()) {
                    return resultSet.getInt("id_alumno")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return null  // Devuelve null si no encuentra el alumno
    }

    // Función para obtener el ID del grupo por su nombre
    fun obtenerIdPorNombreGrupo(nombreGrupo: String): Int? {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "SELECT id_grupo FROM Grupo WHERE nombre_grupo = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombreGrupo)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next()) {
                    return resultSet.getInt("id_grupo")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return null  // Devuelve null si no encuentra el grupo
    }

    fun obtenerAlumnosPorGrupo(idGrupo: Int): List<Alumno> {
        val conexion = obtenerConexion()
        val listaAlumnos = mutableListOf<Alumno>()

        if (conexion != null) {
            try {
                val query = "SELECT id_alumno, nombre, contrasenia, puntuacion, id_grupo FROM Alumno WHERE id_grupo = ? ORDER BY puntuacion DESC"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, idGrupo)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val alumno = Alumno(
                        idAlumno = resultSet.getInt("id_alumno"),
                        nombre = resultSet.getString("nombre"),
                        contraseña = resultSet.getString("contrasenia"),
                        puntuacion = resultSet.getInt("puntuacion"),
                        idGrupo = if (resultSet.getObject("id_grupo") != null) resultSet.getInt("id_grupo") else null
                    )
                    listaAlumnos.add(alumno)
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }

        return listaAlumnos
    }

    /**
     * Obtiene la puntuación actual del alumno
     */
    fun obtenerPuntuacion(usuario: String, callback: (Int) -> Unit) {
        Thread {
            val conexion = obtenerConexion()
            var puntuacion = 0

            if (conexion != null) {
                try {
                    val query = "SELECT puntuacion FROM Alumno WHERE nombre = ?"
                    val statement: PreparedStatement = conexion.prepareStatement(query)
                    statement.setString(1, usuario)
                    val resultSet: ResultSet = statement.executeQuery()

                    if (resultSet.next()) {
                        puntuacion = resultSet.getInt("puntuacion")
                    }

                    statement.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }

            // Llamamos al callback con la puntuación obtenida
            callback(puntuacion)
        }.start()
    }

    /**
     * Actualiza la puntuación del alumno en la base de datos
     */
    fun actualizarPuntuacion(usuario: String, puntos: Int) {
        Thread {
            val conexion = obtenerConexion()
            obtenerPuntuacion(usuario) { puntuacionActual ->
                var nuevaPuntuacion = puntuacionActual + puntos
                if (nuevaPuntuacion < 0) nuevaPuntuacion = 0  // Evitar valores negativos

                if (conexion != null) {
                    try {
                        val query = "UPDATE Alumno SET puntuacion = ? WHERE nombre = ?"
                        val statement: PreparedStatement = conexion.prepareStatement(query)
                        statement.setInt(1, nuevaPuntuacion)
                        statement.setString(2, usuario)
                        statement.executeUpdate()
                        statement.close()
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }
}
