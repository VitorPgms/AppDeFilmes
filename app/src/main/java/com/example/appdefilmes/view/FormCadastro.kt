package com.example.appdefilmes.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityFormCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding.editEmail.requestFocus()


        binding.btVamosLa.setOnClickListener {
            val email = binding.editEmail.text.toString()

            if (!email.isEmpty()){
                binding.containerSenha.visibility = View.VISIBLE
                binding.btVamosLa.visibility = View.GONE
                binding.btContinuar.visibility = View.VISIBLE
                binding.txtTitulo.setText("Um mundo de séries e filmes \n ilimitados espera por você.")
                binding.txtDescricao.setText("Crie uma conta para saber mais sobre \n nosso App de Filmes ")
                binding.containerEmail.boxStrokeColor = Color.parseColor("#4CAF50FF")
                binding.containerEmail.helperText = ""
                binding.containerHeader.visibility = View.VISIBLE
            } else {
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerEmail.helperText = "O email é obrigatorio."
            }
        }

        binding.btContinuar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(!email.isEmpty() && !senha.isEmpty()){
                cadastro(email, senha)
            } else if (senha.isEmpty()){
                binding.containerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerSenha.helperText = "A senha é obrigatoria."
                binding.containerEmail.boxStrokeColor = Color.parseColor("#4CAF50FF")
            } else if (email.isEmpty()){
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerEmail.helperText = "O email é obrigatorio."
            }
        }

        binding.txtEntrar.setOnClickListener {
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
        }
    }

    private fun cadastro(email:String, senha:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
            if(cadastro.isSuccessful){
                Toast.makeText(this, "Cadastro realizdo com sucesso!", Toast.LENGTH_SHORT).show()
                binding.containerEmail.helperText = ""
                binding.containerSenha.helperText = ""
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF018786")
                binding.containerSenha.boxStrokeColor = Color.parseColor("#FF018786")
            }
        }.addOnFailureListener {
            val erro = it

            when{
                erro is FirebaseAuthWeakPasswordException -> {
                    binding.containerSenha.helperText = "Digite uma senha com no mínimo 6 caracteres!"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                }
                erro is FirebaseAuthUserCollisionException -> {
                    binding.containerEmail.helperText = "Essa conta já foi cadastrada!"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                }
                erro is FirebaseNetworkException -> {
                    binding.containerSenha.helperText = "Você não esta conectado há internet"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                }
                else -> {
                    Toast.makeText(this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}