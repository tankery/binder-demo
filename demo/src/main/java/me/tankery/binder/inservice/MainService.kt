package me.tankery.binder.inservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.util.Log

private const val TAG = "binder.service"

const val BINDER_TRANSACT_CODE = IBinder.FIRST_CALL_TRANSACTION

class MainService : Service() {

    private val binder = object : Binder() {
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if (code == BINDER_TRANSACT_CODE) {
                val value = data.readString()
                Log.i(TAG, "Got value from activity: $value")
            }
            return super.onTransact(code, data, reply, flags)
        }
    }

    override fun onBind(intent: Intent) = binder

}
