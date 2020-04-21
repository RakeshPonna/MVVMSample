package com.rocky.mvvm.ui.auth

import androidx.lifecycle.LiveData
import com.rocky.mvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    /*fun onSuccess(loginResponse: LiveData<String>)*/
    fun onSuccess(user: User)
    fun onFailure(message: String)
}
