package com.behad.behadmanagment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behad.adid.BehadADID
import com.behad.adid.BehadCallBack
import com.behad.auth.ServiceLocator
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var adId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BehadADID.getAdId(
            this,
            object : BehadCallBack {
                override fun onFailure(e: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(string: String?) {
                    string?.let {
                        adId = it
                        ServiceLocator.init(
                            it,
                            this@MainActivity,
                            "",
                            "test-management",
                        )
                    }
                }
            },
        )
    }
}
