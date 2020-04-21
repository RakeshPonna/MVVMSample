package com.rocky.mvvm.ui.home.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rocky.mvvm.R
import com.rocky.mvvm.databinding.UserFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class UserFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: UserViewModelFactory by instance()

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userFragmentBinding: UserFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        userFragmentBinding.viewmodel = viewModel
        userFragmentBinding.lifecycleOwner = this
        return userFragmentBinding.root
    }

}
