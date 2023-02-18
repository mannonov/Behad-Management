package com.behad.behadmanagment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behad.adid.BehadADID
import com.behad.adid.BehadCallBack
import com.behad.auth.BackendErrorException
import com.behad.auth.BehadUserManager
import com.behad.auth.NotFoundException
import com.behad.auth.auth.database.DatabaseCallBack
import com.behad.auth.auth.model.BehadUser
import com.behad.auth.auth.model.User
import com.behad.auth.auth.network.AuthCallBack
import com.behad.auth.auth.ui.SignUpBottomSheetDialog

@SuppressLint("ResourceType")
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityRE"
    private val signUpDialog: SignUpBottomSheetDialog by lazy {
        SignUpBottomSheetDialog.Builder()
            .setTitle("Test title")
            .setTitleColor(getString(R.color.purple_200))
            .setButtonText("Button text")
            .setButtonColor(getString(R.color.teal_200))
            .setButtonTextColor(getString(R.color.black))
            .setAnyQuestionText("Any question text")
            .setAnyQuestionColor(getString(R.color.black))
            .setContactColor(getString(R.color.teal_700))
            .setContactText("Contact")
            .setContactUrl("https://bosing.uz/tgbehadbot")
            .build()
    }

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
                            "https://users.behad.uz",
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
                BehadUserManager.getUserDatabase(object : DatabaseCallBack {
                    override fun onSuccess(user: BehadUser) {
                        Log.d(TAG, "onSuccess: $user database")
                    }

                    override fun onFailure(e: Throwable) {
                        Log.d(TAG, "onFailure: $e database")
                    }
                })
            }

            override fun onUserNotFound(notFoundException: NotFoundException) {
                signUpDialog.showDialog(supportFragmentManager)
            }

            override fun onBackendError(e: BackendErrorException) {
                Log.d(TAG, "onBackendError: ")
            }

            override fun onFailure(e: Throwable) {
                Log.d(TAG, "onFailure: ")
            }
        })
    }
}
