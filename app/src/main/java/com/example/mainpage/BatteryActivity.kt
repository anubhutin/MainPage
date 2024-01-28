package com.example.mainpage

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BatteryActivity : AppCompatActivity() {
    private lateinit var batteryProgress: ProgressBar
    private lateinit var tvBatteryLevel: TextView
    private lateinit var tvPlugInValue: TextView
    private lateinit var tvVoltageValue: TextView
    private lateinit var tvTemperatureValue: TextView
    private lateinit var tvTechnologyValue: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)
        // Initialize TextViews
        batteryProgress = findViewById(R.id.batteryProgress)
        tvBatteryLevel = findViewById(R.id.tvBatteryLevel)
        tvPlugInValue = findViewById(R.id.tvPlugInValue)
        tvVoltageValue = findViewById(R.id.tvVoltageValue)
        tvTemperatureValue = findViewById(R.id.tvTemperatureValue)
        tvTechnologyValue = findViewById(R.id.tvTechnologyValue)

        // Register receiver
        registerReceiver()
    }
    private fun registerReceiver() {
        registerReceiver(batteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private val batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            val batteryIsCharging = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            val batteryTemperature = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10)
            val batteryVoltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)?.div(1000)
            val batteryTechnology = intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)

            // Set battery level text
            batteryLevel?.let {
                tvBatteryLevel.text = "$it%"
                batteryProgress.progress = it
            }

            // Set plug state text
            batteryIsCharging?.let {
                tvPlugInValue.text = if (it == 0) "Plug out" else "Plug in"
            }

            // Set voltage text
            batteryVoltage?.let {
                tvVoltageValue.text = "${it}V"
            }

            // Set temperature text
            batteryTemperature?.let {
                tvTemperatureValue.text = "$it°C"
            }

            // Set technology text
            batteryTechnology?.let {
                tvTechnologyValue.text = it
            }
        }
    }
}