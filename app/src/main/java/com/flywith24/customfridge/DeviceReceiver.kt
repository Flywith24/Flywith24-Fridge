package com.flywith24.customfridge

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


/**
 * @author Flywith24
 * @date   2020/8/23Ø
 * time   20:13
 * description
 */
class DeviceReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context?, intent: Intent?) {
        // 设备管理：可用
        Toast.makeText(context, "设备管理：可用", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context?, intent: Intent?) {
        // 设备管理：不可用
        Toast.makeText(context, "设备管理：不可用", Toast.LENGTH_SHORT).show()
    }
}