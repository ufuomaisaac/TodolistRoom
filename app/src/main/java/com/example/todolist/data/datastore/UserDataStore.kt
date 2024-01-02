package com.example.todolist.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.di.AppModule.Companion.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferenceRepository(private var dataStore: DataStore<Preferences>) {

    companion object{
        var passwordPreferenceKey = stringPreferencesKey("password")
        val usernamePreferenceKey = stringPreferencesKey("username")


        const val TAG = "UserPreferencesRepo"
        private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
    }


   /* val Context.dataStore by  preferencesDataStore(
        name = LAYOUT_PREFERENCE_NAME
    )*/
     suspend fun saveUserDetails( password: String, userName: String) {
        dataStore.edit {preference ->
            preference[passwordPreferenceKey] = password
            preference[usernamePreferenceKey] = userName
        }
    }

    var password: Flow<String> =
         dataStore.data
             .catch() {
                 if(it is IOException) {
                     Log.d(TAG, "Error reading preference.", it )
                     emit(emptyPreferences())
                 }
             }
            .map {preferences ->
             preferences[passwordPreferenceKey] ?: ""
    }

    var userName: Flow<String> =
        dataStore.data
            .catch() {
                if(it is IOException) {
                    Log.d(TAG, "Error reading preference.", it )
                    emit(emptyPreferences())
                }
            }
            .map {preferences ->
                preferences[usernamePreferenceKey] ?: ""
            }
}