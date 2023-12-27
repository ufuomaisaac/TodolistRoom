package com.example.todolist.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.prefs.Preferences

class UserDataStore(private val dataStore: DataStore<Preferences>,
                    context: Context) {

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"

        private val Context.dataStore  by preferencesDataStore(
            name = USER_PREFERENCES_NAME
        )
    }


    private suspend fun savePassword(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore

    }

    private suspend fun update(key: String, value: String) {

    }


}