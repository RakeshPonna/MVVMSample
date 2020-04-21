package com.rocky.mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rocky.mvvm.R
import com.rocky.mvvm.data.db.entities.User
import com.rocky.mvvm.databinding.ActivitySignupBinding
import com.rocky.mvvm.ui.auth.AuthListener
import com.rocky.mvvm.ui.auth.AuthViewModel
import com.rocky.mvvm.ui.auth.AuthViewModelFactory
import com.rocky.mvvm.ui.home.HomeActivity
import com.rocky.mvvm.util.hide
import com.rocky.mvvm.util.show
import com.rocky.mvvm.util.snackBar
import com.rocky.mvvm.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySignupBinding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup);
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        activitySignupBinding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer {
            if (it != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        toast("login success :: ${user.name}")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackBar("$message")
    }
}
