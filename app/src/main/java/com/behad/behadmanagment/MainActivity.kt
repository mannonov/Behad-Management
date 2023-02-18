package com.behad.behadmanagment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behad.adid.BehadADID
import com.behad.adid.BehadCallBack
import com.behad.auth.auth.network.AuthCallBack
import com.behad.auth.BackendErrorException
import com.behad.auth.NotFoundException
import com.behad.auth.BehadUserManager
import com.behad.auth.auth.model.User

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityRE"
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
                        BehadUserManager.init(
                            it,
                            this@MainActivity,
                            "",
                            "rangli",
                            "sadhjasbd",
                        )
                    }
                    getUserData()
                }
            },
        )
    }

    private fun getUserData() {
        BehadUserManager.getUserData(object : AuthCallBack {
            override fun onLoading() {
                Log.d(TAG, "onLoading: ")
            }

            override fun onSuccess(user: User) {
                Log.d(TAG, "onSuccess: $user")
            }

            override fun onNotFoundUser(notFoundException: NotFoundException) {
                Log.d(TAG, "onNotFoundUser: ")
            }

            override fun onBackendError(e: BackendErrorException) {
                Log.d(TAG, "onBackendError: ")
            }

            override fun onFailure(e: Exception) {
                Log.d(TAG, "onFailure: ")
            }
        })
    }
}
