package com.nikola_brodar.pokemonapi.ui.activities

import android.graphics.Typeface
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nikola_brodar.pokemonapi.App
import com.nikola_brodar.pokemonapi.R
import com.nikola_brodar.pokemonapi.connectivity.network.ConnectivityChangedEvent
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
open class BaseActivity(noWifiViewId: Int = 0) : AppCompatActivity() {

    protected var viewLoaded = false

    protected var networkAvailable: Boolean = true
        private set

    private val noWifiFrame by lazy { if (noWifiViewId != 0) findViewById<FrameLayout>(noWifiViewId) else null }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkStateChangedEvent(connectivityChangedEvent: ConnectivityChangedEvent) {
        networkAvailable = connectivityChangedEvent.networkAvailable
        onNetworkStateUpdated(connectivityChangedEvent.networkAvailable)
    }

    open fun onNetworkStateUpdated(available: Boolean) {}

    override fun onResume() {
        super.onResume()
        App.ref.eventBus.register(this)
        // If needed to check this immediately when application start
        //if( !ConnectivityMonitor.isAvailable() )
        //    App.ref.eventBus.post(ConnectivityChangedEvent(false))
    }

    override fun onPause() {
        super.onPause()
        App.ref.eventBus.unregister(this)
    }

    fun updateConnectivityUi() {
//        if (noWifiFrame != null ) {
//            noWifiFrame?.visibility = if (networkAvailable) View.GONE else View.VISIBLE
//        }
    }

    fun showSnackbarSync(message: String, isLong: Boolean, mainContentRoot: View) {
        val snackbar = Snackbar.make(
            mainContentRoot,
            message,
            if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        )

        val textColor = ContextCompat.getColor(this, R.color.sunflower_yellow_500)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.sunflower_green_300) )

        val snackbarText = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackbarText.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        snackbarText.setTextColor(textColor)
        snackbarText.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        snackbarText.textSize = 17f
        snackbar.show()
    }

}