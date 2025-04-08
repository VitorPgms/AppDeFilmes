package com.example.appdefilmes.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdefilmes.R
import com.example.appdefilmes.adapter.AdapterCategoria
import com.example.appdefilmes.databinding.ActivityTelaPrincipalBinding
import com.example.appdefilmes.model.Categoria
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    private lateinit var adapterCategoria: AdapterCategoria
    private val listaCategorias: MutableList<Categoria> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#000000")

        val recycleViewFilmes = binding.recyclerViewFilmes
        recycleViewFilmes.layoutManager = LinearLayoutManager(this )
        recycleViewFilmes.setHasFixedSize(true)
        adapterCategoria = AdapterCategoria(this, listaCategorias)
        recycleViewFilmes.adapter = adapterCategoria
        getCategorias()

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Usuario saiu do App! ", Toast.LENGTH_SHORT).show()
        }

    }

    private  fun getCategorias(){
        val categoria1 = Categoria("Categoria 1")
        listaCategorias.add(categoria1)

        val categoria2 = Categoria("Categoria 2")
        listaCategorias.add(categoria1)

        val categoria3 = Categoria("Categoria 3")
        listaCategorias.add(categoria1)

        val categoria4 = Categoria("Categoria 4")
        listaCategorias.add(categoria1)
    }

}