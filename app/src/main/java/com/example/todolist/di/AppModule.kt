package com.example.todolist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class AppModule {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "authetication")
    }

}