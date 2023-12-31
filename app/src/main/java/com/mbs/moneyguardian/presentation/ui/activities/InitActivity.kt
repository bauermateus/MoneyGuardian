package com.mbs.moneyguardian.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mbs.moneyguardian.databinding.ActivityInitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}