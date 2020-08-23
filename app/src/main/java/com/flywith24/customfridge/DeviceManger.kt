package com.flywith24.customfridge

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast


/**
 * @author Flywith24
 * @date   2020/8/23
 * time   20:18
 * description
 */
class DeviceManger(private val context: Context) {
    private val manager =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private var componentName: ComponentName = ComponentName(context, DeviceReceiver::class.java)


    // 激活设备管理器
    fun enableDeviceManager() {
        //判断是否激活  如果没有就启动激活设备
        if (!manager.isAdminActive(componentName)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "激活设备管理器"
            )
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "设备已经激活,请勿重复激活", Toast.LENGTH_SHORT).show()
        }
    }

    // 取消激活设备管理器
    fun disableDeviceManager() {
        manager.removeActiveAdmin(componentName)
    }

    // 设置解锁方式 不需要激活就可以运行
    fun setNewPwd() {
        val intent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
        context.startActivity(intent)
    }

    // 设置解锁方式 需要激活设备管理器（配置策略）
    fun setLockMethod() {
        // PASSWORD_QUALITY_ALPHABETIC
        // 用户输入的密码必须要有字母（或者其他字符）。
        // PASSWORD_QUALITY_ALPHANUMERIC
        // 用户输入的密码必须要有字母和数字。
        // PASSWORD_QUALITY_NUMERIC
        // 用户输入的密码必须要有数字
        // PASSWORD_QUALITY_SOMETHING
        // 由设计人员决定的。
        // PASSWORD_QUALITY_UNSPECIFIED
        // 对密码没有要求。
        if (manager.isAdminActive(componentName)) {
            val intent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
            manager.setPasswordQuality(
                componentName,
                DevicePolicyManager.PASSWORD_QUALITY_NUMERIC
            )
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT).show()
        }
    }

    //立刻锁屏
    fun lockNow() {
        if (manager.isAdminActive(componentName)) {
            manager.lockNow()
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT).show()
        }
    }

    // 设置多长时间后锁屏
    fun lockByTime(time: Long) {
        if (manager.isAdminActive(componentName)) {
            manager.setMaximumTimeToLock(componentName, time)
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT).show()
        }
    }

    // 恢复出厂设置
    fun wipeData() {
        if (manager.isAdminActive(componentName)) {
            manager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE)
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT).show()
        }
    }

    // 设置密码锁
    fun resetPassword(password: String?) {
        if (manager.isAdminActive(componentName)) {
            manager.resetPassword(
                password,
                DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY
            )
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT).show()
        }
    }

    // 禁用相机
    fun disableCamera(disabled: Boolean) {
        if (manager.isAdminActive(componentName)) {
            manager.setCameraDisabled(componentName, disabled)
        } else {
            Toast.makeText(context, "请先激活设备", Toast.LENGTH_SHORT)
                .show()
        }
    }
}