package com.example.challenge_2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.challenge_2.model.User
import com.example.challenge_2.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://challenge-binar-152a8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        userRef = database.reference.child("user")
        binding.EtEmail.text.toString()
        binding.btnRegis.setOnClickListener {
            val email = binding.EtEmail.text.toString()
            val password = binding.EtPassword.text.toString()
            Log.e("Data Email", email+password)
            register(email, password)
        }
    }

    private fun register(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val username = binding.EtUsername.text.toString()
                    val phone = binding.EtNoTelp.text.toString()
                    val user = mAuth.currentUser
                    val userId = user?.uid
                    saveUserData(userId, username, password, email, phone)
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, UserActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Register Gagal", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserData(userId: String?, username: String,password: String, email: String, phone: String) {
        val user = User(userId!!, username, password, email, phone)
        userRef.child(userId).setValue(user)
    }
}