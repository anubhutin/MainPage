package com.example.mainpage

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class CompassActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccelerometer: Sensor
    private lateinit var sensorMagneticField: Sensor
    private val floatGravity = FloatArray(3)
    private val floatGeoMagnetic = FloatArray(3)
    private val floatOrientation = FloatArray(3)
    private val floatRotationMatrix = FloatArray(9)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        imageView = findViewById(R.id.imageView)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
        val sensorEventListenerAccelerometer = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                floatGravity[0] = event.values[0]
                floatGravity[1] = event.values[1]
                floatGravity[2] = event.values[2]

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic)
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation)

                imageView.rotation = (-floatOrientation[0] * 180 / Math.PI).toFloat()
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // Handle accuracy changes if needed
            }
        }
        val sensorEventListenerMagneticField = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                floatGeoMagnetic[0] = event.values[0]
                floatGeoMagnetic[1] = event.values[1]
                floatGeoMagnetic[2] = event.values[2]

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic)
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation)

                imageView.rotation = (-floatOrientation[0] * 180 / Math.PI).toFloat()
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // Handle accuracy changes if needed
            }
        }

        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun resetButton(view: View) {
        imageView.rotation = 180f
    }
}