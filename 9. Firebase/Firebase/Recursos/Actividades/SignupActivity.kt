package com.example.icesiapp231

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.icesiapp231.databinding.ActivityLoginBinding
import com.example.icesiapp231.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    val binding by lazy{
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}