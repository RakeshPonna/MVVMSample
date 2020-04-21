package com.rocky.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rocky.mvvm.data.db.entities.Quote
import com.rocky.mvvm.data.db.entities.User
import net.simplifiedcoding.mvvmsampleapp.data.db.UserDao

@Database(
    entities = [User::class, Quote::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUSerDao(): UserDao
    abstract fun getQuoteDao() : QuoteDao

    companion object {
        private var instance: AppDatabase? = null
        private val lock = Any()

        operator fun invoke(ctx: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(ctx).also {
                instance = it
            }
        }

        private fun buildDatabase(ctx: Context) = Room.databaseBuilder(
            ctx.applicationContext,
            AppDatabase::class.java, "myDatabase"
        ).build()
    }


}