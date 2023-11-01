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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.fab.setOnClickListener {
            binding.fab.animatedRotation(45f)
        }
    }

}