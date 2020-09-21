package com.flywith24.device.admin

import android.app.admin.DeviceAdminInfo.USES_POLICY_LIMIT_PASSWORD
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mManager by lazy { getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    private val mComponentName by lazy {
        ComponentName(
            this,
            CustomDeviceAdminReceiver::class.java
        )
    }

    private fun checkAdmin(
        view: View,
        disable: () -> Unit = {
            Snackbar.make(view, "请先激活设备管理器", Snackbar.LENGTH_SHORT).show()
        },
        enable: () -> Unit
    ) {
        if (mManager.isAdminActive(mComponentName)) enable.invoke()
        else disable.invoke()
    }

    fun addAdmin(view: View) {
        checkAdmin(
            view,
            enable = {
                Snackbar.make(view, "已激活，无需重复激活", Snackbar.LENGTH_SHORT).show()
            },
            disable = {
                startActivityForResult(Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                    putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName)
                    putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "hhhh")
                }, 1)
            })
    }

    fun removeAdmin(view: View) {
        mManager.removeActiveAdmin(mComponentName)
    }

    fun setPasswordQuality(view: View) {
        checkAdmin(view) {
            mManager.setPasswordQuality(
                mComponentName,
                USES_POLICY_LIMIT_PASSWORD
            )
            startActivity(Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD))
        }
    }

    fun lockNow(view: View) = checkAdmin(view) {
        mManager.lockNow()
    }

    fun resetPassword(view: View) = checkAdmin(view) {
        mManager.resetPassword("1234", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY)
    }

    fun setCameraDisabled(view: View) = checkAdmin(view) {
        /*mManager.setCameraDisabled(mComponentName, !mManager.getCameraDisabled(mComponentName))
        if (mManager.getCameraDisabled(mComponentName))
            Snackbar.make(view, "相机已启用", Snackbar.LENGTH_SHORT).show()
        else Snackbar.make(view, "相机已禁用", Snackbar.LENGTH_SHORT).show()*/
        mManager.wipeData((DevicePolicyManager.WIPE_EXTERNAL_STORAGE))

    }

}