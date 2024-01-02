package com.example.todolist

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.data.datastore.UserPreferenceRepository



private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class MyApp : Application() {
    companion object {
       lateinit var userPreferenceRepository: UserPreferenceRepository

    }
    override fun onCreate() {
        super.onCreate()
        userPreferenceRepository = UserPreferenceRepository(dataStore = dataStore)
        Log.d("APPLICATION", "my app is working")
    }
}
