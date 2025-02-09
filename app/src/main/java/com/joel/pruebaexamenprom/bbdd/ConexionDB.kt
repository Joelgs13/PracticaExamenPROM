package com.joel.pruebaexamenprom.bbdd

import android.content.Context
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


    fun selectAlumnoPorCredenciales(nombre: String, contrasenia: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "SELECT COUNT(*) FROM Alumno WHERE nombre = ? AND contrasenia = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombre)
                statement.setString(2, contrasenia)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return false
    }

    // Función para obtener los datos de un Alumno
    fun selectAlumno(idAlumno: Int): String? {
        val conexion = obtenerConexion()
        var alumno: String? = null

        if (conexion != null) {
            try {
                val query = "SELECT nombre, contrasenia, puntuacion FROM Alumno WHERE id_alumno = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, idAlumno)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next()) {
                    val nombre = resultSet.getString("nombre")
                    val contrasenia = resultSet.getString("contrasenia")
                    val puntuacion = resultSet.getInt("puntuacion")
                    alumno = "Nombre: $nombre, Contrasenia: $contrasenia, Puntuación: $puntuacion"
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return alumno
    }

    // Función para obtener los datos de un Grupo
    fun selectGrupo(idGrupo: Int): String? {
        val conexion = obtenerConexion()
        var grupo: String? = null

        if (conexion != null) {
            try {
                val query = "SELECT nombre_grupo FROM Grupo WHERE id_grupo = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, idGrupo)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next()) {
                    grupo = resultSet.getString("nombre_grupo")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return grupo
    }

    // Función para actualizar un Alumno
    fun updateAlumno(idAlumno: Int, nombre: String, puntuacion: Int, idGrupo: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "UPDATE Alumno SET nombre = ?, puntuacion = ?, id_grupo = ? WHERE id_alumno = ?"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, nombre)
                statement.setInt(2, puntuacion)
                statement.setInt(3, idGrupo)
                statement.setInt(4, idAlumno)
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
}
