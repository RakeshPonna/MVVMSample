package com.rocky.mvvm.data.repository

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rocky.mvvm.data.db.AppDatabase
import com.rocky.mvvm.data.db.entities.Quote
import com.rocky.mvvm.data.network.NetworkApi
import com.rocky.mvvm.data.network.SafeApiRequest
import com.rocky.mvvm.data.preferences.PreferenceProvider
import com.rocky.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MIN_INTERVAL = 6

class QuotesRepository(
    private val api: NetworkApi,
    private val db: AppDatabase,
    private val preferenceProvider: PreferenceProvider

) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedTimeStamp = preferenceProvider.getLastTimeStamp()
        if(lastSavedTimeStamp == null || isFetchNeeded(LocalDateTime.parse(lastSavedTimeStamp))){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
        /*if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                lastSavedTimeStamp == null || isFetchNeeded(LocalDateTime.parse(lastSavedTimeStamp))
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        ) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }*/
    }

    private fun isFetchNeeded(parse: LocalDateTime): Boolean {
        /*return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.HOURS.between(parse, LocalDateTime.now()) > MIN_INTERVAL
        } else {
            TODO("VERSION.SDK_INT < O")
        }*/
        return  ChronoUnit.HOURS.between(parse, LocalDateTime.now()) > MIN_INTERVAL
    }

    /* private fun isFetchNeeded(): Boolean {
         return true
     }*/

    private fun saveQuotes(it: List<Quote>) {
        Coroutines.io {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                preferenceProvider.saveLastSessionTimeStamp(LocalDateTime.now().toString())
//            }
            preferenceProvider.saveLastSessionTimeStamp(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(it)
        }

    }


}