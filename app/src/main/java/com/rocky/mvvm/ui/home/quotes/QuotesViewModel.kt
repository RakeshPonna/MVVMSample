package com.rocky.mvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.rocky.mvvm.data.repository.QuotesRepository
import com.rocky.mvvm.util.lazyDeferred

class QuotesViewModel(
    private val repository: QuotesRepository
) : ViewModel() {
    // it will be called whrn quotes needed byu lazy
    /*val quotes by lazy {
        // we cannot call suspend fun directly
        repository.getQuotes()
    }*/
    val quotes by lazyDeferred {
        // we cannot call suspend fun directly
        repository.getQuotes()
    }

}
