package com.example.appdefilmes.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityFormLoginBinding
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#000000")
        binding.editEmail.requestFocus()

        binding.btnEnter.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                email.isEmpty() -> {
                    binding.containerEmail.helperText = "Preencha o email!"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF9800")
                }
                senha.isEmpty() -> {
                    binding.containerSenha.helperText = "Preencha a senha!"
                    binding.containerSenha.boxStrokeColor = Color.parseColor("#FF9800")
                }
                else -> {
                    autenticacao(email,senha)
                }
            }
        }

        binding.txtScreenCadastre.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun autenticacao(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener { autenticacao ->
            if(autenticacao.isSuccessful){
                Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                navegarTelaPrincipal()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao fazer o login do usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navegarTelaPrincipal(){ //Navegação entre a tela de login e a tela principal
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser //Verifica o usuario atual que fez o login

        if(usuarioAtual != null){
            navegarTelaPrincipal()
        }
    }
}