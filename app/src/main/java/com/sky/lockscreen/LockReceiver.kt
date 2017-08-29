package com.sky.lockscreen

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by sky on 17-8-29.
 */
class LockReceiver : DeviceAdminReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }
}