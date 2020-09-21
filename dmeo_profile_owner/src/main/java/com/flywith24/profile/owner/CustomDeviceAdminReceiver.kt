package com.flywith24.profile.owner

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * @author yyz (杨云召)
 * @date   2020/9/21
 * time   10:13
 * description
 */
class CustomDeviceAdminReceiver : DeviceAdminReceiver() {

    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        val manager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(context, CustomDeviceAdminReceiver::class.java)

        manager.setProfileName(componentName, "mdm")
        manager.setProfileEnabled(componentName)
    }
}