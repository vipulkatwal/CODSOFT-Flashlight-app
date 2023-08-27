package com.vk.kotlin.flashmagic

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private lateinit var toggleButton: ImageButton
    private var isFlashlightOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton = findViewById(R.id.toggleButton)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager?.cameraIdList?.firstOrNull()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        toggleButton.setOnClickListener {
            toggleFlashlight()
        }
    }

    private fun toggleFlashlight() {
        try {
            if (isFlashlightOn) {
                cameraManager?.setTorchMode(cameraId!!, false)
                isFlashlightOn = false
                toggleButton.setImageResource(R.drawable.ic_flashlight_off_icon)
            } else {
                cameraManager?.setTorchMode(cameraId!!, true)
                isFlashlightOn = true
                toggleButton.setImageResource(R.drawable.ic_flashlight_on_icon)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFlashlightOn) {
            toggleFlashlight()
        }
    }
}
