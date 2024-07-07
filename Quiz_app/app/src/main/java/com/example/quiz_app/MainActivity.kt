package com.example.quiz_app

import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferenceManager = SharedPreferenceManager(this)
        val username = sharedPreferenceManager.getString("username")
        if(username.isEmpty()) {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this,QuizQuestionActivity::class.java)
            intent.putExtra(Constant.USER_NAME,username)
            startActivity(intent)
            finish()
        }

    }
}

