package com.example.leveltest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.leveltest.R
import com.example.leveltest.orientation.Orientation
import com.example.leveltest.orientation.OrientationListener
import com.example.leveltest.orientation.OrientationProvider
import com.example.leveltest.view.LevelView

class MainFragment : Fragment(R.layout.fragment_main), OrientationListener {

    var provider: OrientationProvider? = null

    private var levelView: LevelView? = null

    companion object {
        fun newInstance() = MainFragment()

        var CONTEXT: MainFragment? = null

        fun getContext(): MainFragment? {
            return CONTEXT
        }

        fun getProvider(): OrientationProvider? {
            return getContext()!!.provider
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CONTEXT = this
        levelView = view.findViewById(R.id.main_levelView)
    }

    override fun onResume() {
        super.onResume()
        Log.d("Level", "Level resumed")
        provider = OrientationProvider.getInstance()

        // orientation manager
        if (provider!!.isSupported) {
            provider!!.startListening(this)
        } else {
            Toast.makeText(context, getText(R.string.not_supported), Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        if (provider!!.isListening) {
            provider!!.stopListening()
        }
    }

    override fun onOrientationChanged(
        orientation: Orientation?,
        pitch: Float,
        roll: Float,
        balance: Float
    ) {
        levelView!!.onOrientationChanged(orientation, pitch, roll, balance)
    }

    override fun onCalibrationSaved(success: Boolean) {
        Toast.makeText(
            context, if (success) R.string.calibrate_saved else R.string.calibrate_failed,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCalibrationReset(success: Boolean) {
        Toast.makeText(
            context, if (success) R.string.calibrate_restored else R.string.calibrate_failed,
            Toast.LENGTH_LONG
        ).show()
    }

}