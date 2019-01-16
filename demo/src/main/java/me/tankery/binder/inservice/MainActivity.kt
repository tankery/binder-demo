package me.tankery.binder.inservice

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException
import android.util.Log
import me.tankery.binder.R

private const val TAG = "binder.activity"

class MainActivity : Activity(), ServiceConnection {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MainService::class.java)
        bindService(intent, this, Service.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    override fun onServiceConnected(name: ComponentName?, remote: IBinder?) {
        Log.i(TAG, "Service connected, got binder $remote in activity")

        val data = Parcel.obtain()
        try {
            data.writeString("Hello World!")
            remote?.transact(BINDER_TRANSACT_CODE, data, null, 0)
        } catch (e: RemoteException) {
            Log.w(TAG, "Got error to send from activity", e)
        } finally {
            data.recycle()
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
    }
}
