package com.flywith24.device.admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mManager by lazy { getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    private val mComponentName by lazy {
        ComponentName(
            this,
            CustomDeviceAdminReceiver::class.java
        )
    }

    fun addAdmin(view: View) {
        if (!mManager.isAdminActive(mComponentName))
            startActivityForResult(Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName)
                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "hhhh")
            }, 1)
        else Snackbar.make(view, "已激活，无需重复激活", Snackbar.LENGTH_SHORT).show()
    }

    fun removeAdmin(view: View) {
        mManager.removeActiveAdmin(mComponentName)
    }
}