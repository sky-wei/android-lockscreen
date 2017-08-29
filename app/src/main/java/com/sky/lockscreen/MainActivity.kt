package com.sky.lockscreen

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

/**
 * Created by sky on 17-8-29.
 */
class MainActivity : Activity() {

    var mDevicePolicyManager: DevicePolicyManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 获取服务
        mDevicePolicyManager = getSystemService(
                Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        if (!lockScreen()) {

            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getLockComponentName())
                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.app_name))
            }

            // 跳转到激活界面
            startActivityForResult(intent, 0)
            return
        }

        finish()
    }

    /**
     * 锁屏
     */
    fun lockScreen(): Boolean {

        if (isAdminActive()) {
            // 锁屏
            mDevicePolicyManager!!.lockNow()
            return true
        }
        return false
    }

    fun isAdminActive(): Boolean {
        return mDevicePolicyManager!!.isAdminActive(getLockComponentName())
    }

    fun getLockComponentName(): ComponentName {
        return ComponentName(this, LockReceiver::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (RESULT_OK == resultCode) {
            // 进行锁屏处理
            lockScreen()
        } else {
            Toast.makeText(this, "不激活无法进行锁屏", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}