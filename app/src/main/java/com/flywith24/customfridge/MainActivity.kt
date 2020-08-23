package com.flywith24.customfridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    private val deviceManger by lazy { DeviceManger(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun enable(view: View) {
        deviceManger.enableDeviceManager()
    }

    fun disableCamera(view: View) {
        deviceManger.disableCamera(true)
    }
}