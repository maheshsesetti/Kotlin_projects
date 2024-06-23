package com.example.quiz_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferenceManager = SharedPreferenceManager(this)
        setContentView(R.layout.activity_login)
        val btn_start : Button = findViewById(R.id.btn_start)
        val et_input_text : EditText = findViewById(R.id.et_input_name)


        btn_start.setOnClickListener {
            if(et_input_text.text.isEmpty()) {
                Toast.makeText(this,"Please Enter Name .", Toast.LENGTH_LONG).show()
            } else {
                sharedPreferenceManager.putString("username", "${et_input_text.text}")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}