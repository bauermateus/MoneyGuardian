package com.mbs.moneyguardian

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity.getSignInClient
import com.mbs.moneyguardian.auth.GoogleAuthUiClient
import com.mbs.moneyguardian.auth.SignInViewModel
import com.mbs.moneyguardian.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            lifecycleScope.launch {
                GoogleAuthUiClient(
                    this@MainActivity,
                    getSignInClient(this@MainActivity)
                ).signOut()
            }
        }
    }

    private fun onClick() {
        binding.fab.setOnClickListener {
            binding.fab.apply {
                rotation = 45f
            }
        }
    }

}