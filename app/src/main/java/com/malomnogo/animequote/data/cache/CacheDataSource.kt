package com.malomnogo.animequote.data.cache

import android.content.Context

interface CacheDataSource {

    interface Save {
        suspend fun saveQuote(value: String)
    }

    interface Read {
        suspend fun lastQuote(): String
    }

    interface Mutable : Save, Read

    class Base(context: Context) : Mutable {

        private val preferences =
            context.getSharedPreferences("quotePrefs", Context.MODE_PRIVATE)

        override suspend fun saveQuote(value: String) {
            preferences.edit().putString(KEY, value).apply()
        }

        override suspend fun lastQuote() = preferences.getString(KEY, "")!!

        companion object {
            private const val KEY = "quote"
        }
    }
}