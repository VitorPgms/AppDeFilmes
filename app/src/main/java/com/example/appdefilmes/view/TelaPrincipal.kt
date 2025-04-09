package com.example.appdefilmes.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdefilmes.R
import com.example.appdefilmes.adapter.AdapterCategoria
import com.example.appdefilmes.api.Api
import com.example.appdefilmes.databinding.ActivityTelaPrincipalBinding
import com.example.appdefilmes.model.Categoria
import com.example.appdefilmes.model.Categorias
import com.example.appdefilmes.model.Filme
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Usuario saiu do App! ", Toast.LENGTH_SHORT).show()
        }

        //Config Retrofit
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://stackmobile.com.br/")
            .build()
            .create(Api::class.java)

        retrofit.listaCategorias().enqueue(object  : Callback<Categorias>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.code() == 200){
                    response.body()?.let {
                        adapterCategoria.listaCategorias.addAll(it.categorias)
                        adapterCategoria.notifyDataSetChanged()
                        binding.containerProgressBar.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.txtCarregando.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<Categorias>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}