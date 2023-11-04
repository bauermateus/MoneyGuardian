package com.mbs.moneyguardian

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity.getSignInClient
import com.mbs.moneyguardian.auth.GoogleAuthUiClient
import com.mbs.moneyguardian.auth.SignInViewModel
import com.mbs.moneyguardian.databinding.ActivityMainBinding
import com.mbs.moneyguardian.utils.animations.animatedRotation
import com.mbs.moneyguardian.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
        observe()
    }

    private fun onClick() {
        binding.fab.setOnClickListener {
            mainViewModel.setState()
        }
    }

    private fun observe() {
        mainViewModel.uiState.observe(this) { uiState ->
            with(binding) {
                fab.animatedRotation(if (uiState.isExpanded) 45f else 0f)
            }
        }
    }

}