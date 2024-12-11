package com.example.firebaseconandroid

import CustomSpinnerAdapter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FormularioOferta : AppCompatActivity() {

    // Referencia a la base de datos de Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulario_oferta)

        // Inicializar referencia a Firebase
        database = FirebaseDatabase.getInstance().reference

        // Obtener referencias a los elementos de la vista
        val tituloOferta = findViewById<EditText>(R.id.etTituloOferta)
        val descripcionOferta = findViewById<EditText>(R.id.etDescripcionOferta)
        val tipoOferta = findViewById<Spinner>(R.id.spTipoOferta)
        val sectorOferta = findViewById<Spinner>(R.id.spSectorOferta)
        val experienciaOferta = findViewById<Spinner>(R.id.spExperienciaOferta)
        val ubicacionOferta = findViewById<Spinner>(R.id.spUbicacionOferta)
        val salarioOferta = findViewById<EditText>(R.id.etSalarioOferta)
        val btnRegistrarOferta = findViewById<Button>(R.id.btnRegistrarOferta)

        // --SPINNERS (desplegables)
        // Lista de opciones para los Spinners
        val sectores = arrayOf("Tech/Informática", "Finanzas", "Salud", "Educación", "Construcción")
        val tiposEmpleo = arrayOf("Jornada Completa", "Media Jornada", "Freelance")
        val experiencias = arrayOf("Menos de 1 año", "1 a 3 años", "4 a 5 años", "Más de 5 años")
        val ubicaciones = arrayOf("Valencia", "Madrid", "Barcelona")

        // Configurar adaptadores para los Spinners
        val sectorAdapter = CustomSpinnerAdapter(this, sectores)
        sectorOferta.adapter = sectorAdapter

        val tipoEmpleoAdapter = CustomSpinnerAdapter(this, tiposEmpleo)
        tipoOferta.adapter = tipoEmpleoAdapter

        val experienciaAdapter = CustomSpinnerAdapter(this, experiencias)
        experienciaOferta.adapter = experienciaAdapter

        val ubicacionAdapter = CustomSpinnerAdapter(this, ubicaciones)
        ubicacionOferta.adapter = ubicacionAdapter

        // Configurar acción del botón
        btnRegistrarOferta.setOnClickListener {
            val titulo = tituloOferta.text.toString().trim()
            val descripcion = descripcionOferta.text.toString().trim()
            val tipo = tipoOferta.selectedItem.toString()
            val sector = sectorOferta.selectedItem.toString()
            val experiencia = experienciaOferta.selectedItem.toString()
            val ubicacion = ubicacionOferta.selectedItem.toString()
            val salario = salarioOferta.text.toString().trim()

            // Validar campos vacíos
            if (titulo.isEmpty() || descripcion.isEmpty() || salario.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto de oferta
            val oferta = hashMapOf(
                "titulo" to titulo,
                "descripcion" to descripcion,
                "tipo" to tipo,
                "sector" to sector,
                "experiencia" to experiencia,
                "ubicacion" to ubicacion,
                "salario" to salario
            )

            // Guardar en Firebase
            database.child("ofertas").push().setValue(oferta)
                .addOnSuccessListener {
                    Toast.makeText(this, "Oferta registrada correctamente.", Toast.LENGTH_SHORT).show()

                    // Reiniciar campos
                    tituloOferta.text.clear()
                    descripcionOferta.text.clear()
                    tipoOferta.setSelection(0)
                    sectorOferta.setSelection(0)
                    experienciaOferta.setSelection(0)
                    ubicacionOferta.setSelection(0)
                    salarioOferta.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al registrar la oferta. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
