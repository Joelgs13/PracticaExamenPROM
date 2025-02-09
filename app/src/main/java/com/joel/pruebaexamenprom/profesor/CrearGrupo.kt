package com.joel.pruebaexamenprom.profesor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joel.pruebaexamenprom.R
import com.joel.pruebaexamenprom.bbdd.ConexionDB

/**
 * Actividad que permite crear un nuevo grupo en la base de datos.
 */
class CrearGrupo : AppCompatActivity() {

    /**
     * Metodo llamado cuando se crea la actividad.
     * Configura la interfaz de usuario y maneja el evento de creación de un nuevo grupo.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_grupo) // Carga el layout "crear_grupo.xml"

        val editTextNombreGrupo = findViewById<EditText>(R.id.editTextNombreGrupo)
        val buttonCrearGrupo = findViewById<Button>(R.id.button_crear_grupo)

        /**
         * Acción a realizar cuando se hace clic en el botón "Crear Grupo".
         * Valida el campo de texto y si es válido, intenta crear el grupo en la base de datos.
         */
        buttonCrearGrupo.setOnClickListener {
            val nombreGrupo = editTextNombreGrupo.text.toString()

            // Validamos que el campo no esté vacío
            if (nombreGrupo.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Usamos un Thread para ejecutar la verificación y la inserción en segundo plano
                Thread {
                    val conexionDB = ConexionDB(applicationContext)

                    // Verificamos si el grupo ya existe
                    val grupoExiste = conexionDB.grupoExiste(nombreGrupo)

                    if (grupoExiste) {
                        // Si el grupo ya existe, mostramos un mensaje en el hilo principal
                        runOnUiThread {
                            Toast.makeText(this, "El grupo ya existe", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Si el grupo no existe, insertamos el nuevo grupo
                        val resultado = conexionDB.insertGrupo(nombreGrupo)

                        // Actualizamos la UI en el hilo principal después de completar la tarea
                        runOnUiThread {
                            if (resultado) {
                                Toast.makeText(this, "Grupo creado exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Error al crear el grupo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }.start()
            }
        }
    }
}
