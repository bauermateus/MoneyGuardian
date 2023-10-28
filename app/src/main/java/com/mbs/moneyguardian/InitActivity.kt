package com.mbs.moneyguardian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mbs.moneyguardian.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}