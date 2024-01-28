package com.example.mainpage

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.widget.TextView
import kotlin.math.sqrt

class DisplayActivity : AppCompatActivity() {

    private lateinit var screenSizeText: TextView
    private lateinit var androidVersionText: TextView
    private lateinit var osVersionText: TextView
    private lateinit var mobileOperatorText: TextView
    private lateinit var manufacturerText: TextView
    private lateinit var modelText: TextView
    private lateinit var buildNumberText: TextView
    private lateinit var osNameText: TextView
    private lateinit var sdkText: TextView
    private lateinit var kernelText: TextView
    private lateinit var bootLoaderText: TextView
    private lateinit var frontCameraResolutionText: TextView
    private lateinit var backCameraResolutionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display3)

        initializeViews()
        displayDeviceInfo()
        getMobileOperatorInfo()
        getCameraResolutions()
    }

    private fun initializeViews() {
        screenSizeText = findViewById(R.id.screen_size_text)
        androidVersionText = findViewById(R.id.android_version_text)
        osVersionText = findViewById(R.id.os_version_text)
        mobileOperatorText = findViewById(R.id.mobile_operator_text)
        manufacturerText = findViewById(R.id.manufacturer_text)
        modelText = findViewById(R.id.model_text)
        buildNumberText = findViewById(R.id.build_number_text)
        osNameText = findViewById(R.id.os_name_text)
        sdkText = findViewById(R.id.sdk_text)
        kernelText = findViewById(R.id.kernel_text)
        bootLoaderText = findViewById(R.id.boot_loader_text)
        frontCameraResolutionText = findViewById(R.id.front_camera_resolution_text)
        backCameraResolutionText = findViewById(R.id.back_camera_resolution_text)
    }

    private fun displayDeviceInfo() {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT.toString()
        val kernelVersion = System.getProperty("os.version")

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val screenSizeInches = sqrt((widthPixels * widthPixels + heightPixels * heightPixels).toDouble()) / displayMetrics.xdpi

        val buildNumber = Build.ID
        val osName = "Android"
        val bootLoader = Build.BOOTLOADER

        screenSizeText.text = "${String.format("%.2f", screenSizeInches)} inches"
        androidVersionText.text = "$osVersion"
        osVersionText.text = "$osVersion"
        manufacturerText.text = "$manufacturer"
        modelText.text = "$model"
        buildNumberText.text = "$buildNumber"
        osNameText.text = "$osName"
        sdkText.text = " $sdkVersion"
        kernelText.text = " $kernelVersion"
        bootLoaderText.text = "$bootLoader"
    }

    private fun getMobileOperatorInfo() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val mobileOperatorName = telephonyManager.networkOperatorName
        mobileOperatorText.text = " $mobileOperatorName"
    }

    private fun getCameraResolutions() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val cameraIdList = cameraManager.cameraIdList

        for (cameraId in cameraIdList) {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            val lensFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)

            val streamConfigurationMap =
                cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

            val resolutions = streamConfigurationMap?.getOutputSizes(android.graphics.ImageFormat.JPEG)

            if (lensFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                val frontCameraResInMegapixels = if (!resolutions.isNullOrEmpty()) {
                    calculateMegapixels(resolutions[0].width, resolutions[0].height)
                } else {
                    "Unknown"
                }
                frontCameraResolutionText.text = "$frontCameraResInMegapixels MP"
            } else if (lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                val backCameraResInMegapixels = if (!resolutions.isNullOrEmpty()) {
                    calculateMegapixels(resolutions[0].width, resolutions[0].height)
                } else {
                    "Unknown"
                }
                backCameraResolutionText.text = "$backCameraResInMegapixels MP"
            }
        }
    }

    private fun calculateMegapixels(width: Int, height: Int): String {
        val totalPixels = width * height
        val megapixels = totalPixels / 1000000.0
        return String.format("%.2f", megapixels)
    }
}