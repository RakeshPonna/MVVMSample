package com.rocky.mvvm

import android.app.Application
import com.rocky.mvvm.data.db.AppDatabase
import com.rocky.mvvm.data.network.NetworkApi
import com.rocky.mvvm.data.network.NetworkConnectionIntercepter
import com.rocky.mvvm.data.preferences.PreferenceProvider
import com.rocky.mvvm.data.repository.QuotesRepository
import com.rocky.mvvm.data.repository.UserRepository
import com.rocky.mvvm.ui.auth.AuthViewModelFactory
import com.rocky.mvvm.ui.home.quotes.QuotesViewModelFactory
import com.rocky.mvvm.ui.home.users.UserViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        bind() from singleton { NetworkConnectionIntercepter(instance()) }
        bind() from singleton { NetworkApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { UserViewModelFactory(instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(),instance()) }
        bind() from singleton { QuotesViewModelFactory(instance()) }

    }
}