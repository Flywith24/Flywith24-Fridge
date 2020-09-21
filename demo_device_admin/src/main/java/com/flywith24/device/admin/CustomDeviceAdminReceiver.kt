package com.flywith24.device.admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * @author yyz (杨云召)
 * @date   2020/9/21
 * time   10:13
 * description
 */
class CustomDeviceAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        Toast.makeText(context, "设备管理员开启", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        Toast.makeText(context, "设备管理员关闭", Toast.LENGTH_SHORT).show()
    }

}