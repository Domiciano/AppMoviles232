package com.example.icesiapp231

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.icesiapp231.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}