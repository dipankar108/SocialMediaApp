package com.hello.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hello.socialmediaapp.databinding.ActivityViewPostBinding

class ViewPost : AppCompatActivity() {
    lateinit var binding:ActivityViewPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}