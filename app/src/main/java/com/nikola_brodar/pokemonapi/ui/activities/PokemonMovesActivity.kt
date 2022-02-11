package com.nikola_brodar.pokemonapi.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikola_brodar.data.database.model.DBPokemonMoves
import com.nikola_brodar.domain.ResultState
import com.nikola_brodar.pokemonapi.R
import com.nikola_brodar.pokemonapi.databinding.ActivityPokemonMovesBinding
import com.nikola_brodar.pokemonapi.ui.adapters.PokemonMovesAdapter
import com.nikola_brodar.pokemonapi.ui.utilities.hide
import com.nikola_brodar.pokemonapi.ui.utilities.show
import com.nikola_brodar.pokemonapi.viewmodels.PokemonViewModel
import kotlinx.android.synthetic.main.activity_pokemon.*


class PokemonMovesActivity : BaseActivity(R.id.no_internet_layout) {

    val pokemonViewModel: PokemonViewModel by viewModels()

    private lateinit var pokemonAdapter: PokemonMovesAdapter
    var pokemonLayoutManager: LinearLayoutManager? = null

    private lateinit var binding: ActivityPokemonMovesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonMovesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbarWeather))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        viewLoaded = true

        initializeUi()

        pokemonViewModel.pokemonMovesData.observe(this, Observer { items ->
            when( items ) {
                is ResultState.Loading -> {
                    showProgressBar()
                    hideAllUiElements()
                }

                is ResultState.Success -> {
                    hideProgressBar()
                    displayAllUiElements()
                    successUpdateUi(items.data as List<DBPokemonMoves> )
                }
                is ResultState.Error -> {
                    hideProgressBar()
                    somethingWentWrong(items)
                }
            }
        })

        pokemonViewModel.getPokemonMovesFromLocalStorage()
    }

    fun hideAllUiElements() {
        binding.tvTotalNumber.visibility = View.GONE
        binding.pokemonList.visibility = View.GONE
    }

    fun displayAllUiElements() {
        binding.tvTotalNumber.visibility = View.VISIBLE
        binding.pokemonList.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.hide()
    }

    private fun showProgressBar() {
        progressBar.show()
    }

    private fun somethingWentWrong(items: ResultState.Error) {
        showSnackbarSync( items.message + items.exception.toString(), true, binding.mainLayout )
    }

    private fun successUpdateUi(pokemonData: List<DBPokemonMoves>) {

        Log.d(
            ContentValues.TAG,
            "Data is: ${pokemonData.joinToString { "-" + it.name }}"
        )
        binding.progressBar.visibility = View.GONE

        binding.tvTotalNumber.text = "Total number of moves is: " + pokemonData.size
        pokemonAdapter.updateDevices(pokemonData.toMutableList())
    }

    private fun initializeUi() {

        pokemonLayoutManager = LinearLayoutManager(
            this@PokemonMovesActivity,
            RecyclerView.VERTICAL,
            false
        )

        pokemonAdapter = PokemonMovesAdapter(mutableListOf())

        binding.pokemonList.apply {
            layoutManager = pokemonLayoutManager
            adapter = pokemonAdapter
        }
        binding.pokemonList.adapter = pokemonAdapter
    }


    override fun onNetworkStateUpdated(available: Boolean) {
        super.onNetworkStateUpdated(available)
        if( viewLoaded == true )
            updateConnectivityUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, PokemonActivity::class.java)
                intent.putExtra("displayCurrentPokemonData", true)
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, PokemonActivity::class.java)
        intent.putExtra("displayCurrentPokemonData", true)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

}