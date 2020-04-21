package com.rocky.mvvm.ui.home.users

import androidx.lifecycle.ViewModel
import com.rocky.mvvm.data.repository.UserRepository

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}
