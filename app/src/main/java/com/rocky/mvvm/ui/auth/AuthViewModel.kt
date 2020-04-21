package com.rocky.mvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.rocky.mvvm.data.repository.UserRepository
import com.rocky.mvvm.ui.SignupActivity
import com.rocky.mvvm.util.ApiExceptions
import com.rocky.mvvm.util.Coroutines
import com.rocky.mvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirmation: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()
        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name should not be empty")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("email should not be empty")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("password should not be empty")
            return
        }
        if (password != passwordConfirmation) {
            authListener?.onFailure("password mismatch")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userSignUP(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            } catch (nie: NoInternetException) {
                authListener?.onFailure(nie.message!!)
            }
        }
    }

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email and password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            } catch (nie: NoInternetException) {
                authListener?.onFailure(nie.message!!)
            }
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

}


/*
class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email and password")
            return
        }
        //authListener?.onSuccess()
        // success
        // this is bad practice for UserRepository() accessing
        */
/*  val loginResponse = UserRepository().userLogin(email!!,password!!)
          authListener?.onSuccess(loginResponse)
  *//*

        */
/*Coroutines.main {
            val response = UserRepository().userLogin(email!!, password!!)
            if (response.isSuccessful) {
                authListener?.onSuccess(response.body()?.user!!)
            } else
                authListener?.onFailure("Error Code : ${response.code()}")
        }*//*

        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onSignup(view: View) {

    }

}*/
