package com.flywith24.profile.owner

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.PERMISSION_POLICY_AUTO_GRANT
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mManager by lazy { getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    private val mComponentName by lazy {
        ComponentName(
            this,
            CustomDeviceAdminReceiver::class.java
        )
    }

    private fun checkProfile(
        view: View,
        disable: () -> Unit = {
            Snackbar.make(view, "请先激活 Profile Owner", Snackbar.LENGTH_SHORT).show()
        },
        enable: () -> Unit
    ) {
        if (mManager.isProfileOwnerApp(mComponentName.packageName)) enable.invoke()
        else disable.invoke()
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun addProfile(view: View) = checkProfile(view, disable = {
        val component by lazy {
            ComponentName(
                this,
                CustomDeviceAdminReceiver::class.java.name
            )
        }

        val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE).apply {
            putExtra(
                DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                component
            )
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 1)
        }

    }, enable = {
        Snackbar.make(view, "无需重复激活", Snackbar.LENGTH_SHORT).show()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkProfile(screenCapture) {
            screenCapture.isChecked = !mManager.getScreenCaptureDisabled(mComponentName)
            screenCapture.setOnCheckedChangeListener { _, isChecked ->
                mManager.setScreenCaptureDisabled(mComponentName, !isChecked)
            }
        }
        checkProfile(volumeMuted) {
            volumeMuted.isChecked = mManager.isMasterVolumeMuted(mComponentName)
            volumeMuted.setOnCheckedChangeListener { _, isChecked ->
                mManager.setMasterVolumeMuted(mComponentName, isChecked)
            }
        }
        checkProfile(hidden) {
            //启用/禁用 设置
            hidden.isChecked =
                mManager.isApplicationHidden(mComponentName, "com.android.documentsui")
            hidden.setOnCheckedChangeListener { _, isChecked ->
                mManager.setApplicationHidden(mComponentName, "com.android.documentsui", isChecked)
            }
        }
        checkProfile(camera) {
            //启用/禁用 设置
            camera.isChecked = mManager.getCameraDisabled(mComponentName)
            camera.setOnCheckedChangeListener { _, isChecked ->
                mManager.setCameraDisabled(mComponentName, isChecked)
            }
        }
    }

    fun setPermissionPolicy(view: View) = checkProfile(view) {
        mManager.setPermissionPolicy(mComponentName, PERMISSION_POLICY_AUTO_GRANT)
    }

}