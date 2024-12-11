package com.example.firebaseconandroid

import CustomSpinnerAdapter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FormularioBusqueda : AppCompatActivity() {

    // Referencia a la base de datos de Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulario_busqueda)

        // Inicializar referencia a Firebase
        database = FirebaseDatabase.getInstance().reference

        // Obtener referencias a los elementos de la vista
        val nombre = findViewById<EditText>(R.id.etNombre)
        val descripcion = findViewById<EditText>(R.id.etDescripcion)
        val spSector = findViewById<Spinner>(R.id.spSector)
        val spTipoEmpleo = findViewById<Spinner>(R.id.spTipoEmpleo)
        val spExperiencia = findViewById<Spinner>(R.id.spExperienciaBusqueda)
        val spUbicacion = findViewById<Spinner>(R.id.spUbicacionBusqueda)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)

        // --SPINNERS (desplegables)
        // Lista de opciones para los Spinners
        val sectores = arrayOf("Tech/Informática", "Finanzas", "Salud", "Educación", "Construcción")
        val tiposEmpleo = arrayOf("Jornada Completa", "Media Jornada", "Freelance")
        val experiencias = arrayOf("Menos de 1 año", "1 a 3 años", "4 a 5 años", "Más de 5 años")
        val ubicaciones = arrayOf("Valencia", "Madrid", "Barcelona")


        // Configurar adaptadores para los Spinners
        val sectorAdapter = CustomSpinnerAdapter(this, sectores)
        spSector.adapter = sectorAdapter

        val tipoEmpleoAdapter = CustomSpinnerAdapter(this, tiposEmpleo)
        spTipoEmpleo.adapter = tipoEmpleoAdapter

        val experienciaAdapter = CustomSpinnerAdapter(this, experiencias)
        spExperiencia.adapter = experienciaAdapter

        val ubicacionAdapter = CustomSpinnerAdapter(this, ubicaciones)
        spUbicacion.adapter = ubicacionAdapter

        // Configurar acción del botón
        btnEnviar.setOnClickListener {
            val nombreText = nombre.text.toString().trim()
            val descripcionText = descripcion.text.toString().trim()
            val sectorSeleccionado = spSector.selectedItem.toString()
            val tipoEmpleoSeleccionado = spTipoEmpleo.selectedItem.toString()
            val experienciaSeleccionada = spExperiencia.selectedItem.toString()
            val ubicacionSeleccionada = spUbicacion.selectedItem.toString()

            // Validar campos vacíos
            if (nombreText.isEmpty() || descripcionText.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto de búsqueda
            val busqueda = mapOf(
                "nombre" to nombreText,
                "descripcion" to descripcionText,
                "sector" to sectorSeleccionado,
                "tipoEmpleo" to tipoEmpleoSeleccionado,
                "experiencia" to experienciaSeleccionada,
                "ubicacion" to ubicacionSeleccionada
            )

            // Guardar en Firebase
            database.child("demandas").push().setValue(busqueda)
                .addOnSuccessListener {
                    Toast.makeText(this, "Demanda registrada correctamente.", Toast.LENGTH_SHORT).show()

                    // Reiniciar campos
                    nombre.text.clear()
                    descripcion.text.clear()
                    spSector.setSelection(0)
                    spTipoEmpleo.setSelection(0)
                    spExperiencia.setSelection(0)
                    spUbicacion.setSelection(0)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al registrar la búsqueda. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
