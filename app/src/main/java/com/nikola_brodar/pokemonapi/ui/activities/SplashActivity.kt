package com.nikola_brodar.pokemonapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nikola_brodar.pokemonapi.R
import com.nikola_brodar.pokemonapi.connectivity.network.ConnectivityMonitor
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.IO) {
            delay(SPLASH_DISPLAY_LENGTH)
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                startApp()
            }
        }
    }

    private fun startApp() {

        if (ConnectivityMonitor.isAvailable()) {
            val intent = Intent(this@SplashActivity, PokemonActivity::class.java)
            startActivity(intent)
            finish()
        } else if (!ConnectivityMonitor.isAvailable()) {
            tvTurnOn.visibility = View.VISIBLE
            tvTurnOn.text = getString(R.string.internet_not_available)
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                checkIfNetworkAndGpsAreTurnedOn()
            }
        }
    }

    private fun checkIfNetworkAndGpsAreTurnedOn() {
        if (!ConnectivityMonitor.isAvailable()) {
            tvTurnOn.text = getString(R.string.internet_not_available)
        }  else {
            val intent = Intent(this@SplashActivity, PokemonActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}