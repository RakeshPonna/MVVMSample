package com.rocky.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rocky.mvvm.R
import com.rocky.mvvm.data.db.entities.User
import com.rocky.mvvm.databinding.ActivityLoginBinding
import com.rocky.mvvm.ui.home.HomeActivity
import com.rocky.mvvm.util.hide
import com.rocky.mvvm.util.show
import com.rocky.mvvm.util.snackBar
import com.rocky.mvvm.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  /*tigtly coupled */
        /*
  //        val api = NetworkApi()

          val networkConnectionIntercepter = NetworkConnectionIntercepter(this)
          val api = NetworkApi(networkConnectionIntercepter)
          val db = AppDatabase(this)
          val repository = UserRepository(api, db)
          val factory = AuthViewModelFactory(repository)
          */


        val activityLoginBinding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login);
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        activityLoginBinding.viewmodel = viewModel
        viewModel.authListener = this
//        AppDatabase.invoke(this)

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
//        toast("login started")
    }

    /* override fun onSuccess(loginResponse: LiveData<String>) {
 //        toast("login success")
         loginResponse.observe(this, Observer {
             progress_bar.hide()
             toast(it)
         })
     }*/

    override fun onSuccess(user: User) {
        toast("login success :: ${user.name}")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackBar("$message")
        //toast(message)
    }
}
