package com.example.firebaseconandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Botones
        val searchButton: Button = findViewById(R.id.searchBtn)
        val offerButton: Button = findViewById(R.id.offerBtn)

        searchButton.setOnClickListener {
            startActivity(Intent(this, FormularioBusqueda::class.java))
        }

        offerButton.setOnClickListener {
            startActivity(Intent(this, FormularioOferta::class.java))
        }

    }
}