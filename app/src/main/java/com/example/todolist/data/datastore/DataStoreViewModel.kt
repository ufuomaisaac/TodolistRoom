package com.example.todolist.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.di.AppModule.Companion.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreViewModel(
    private val repository: UserPreferenceRepository) : ViewModel() {

    fun saveUserDetails( password: String, userName: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.saveUserDetails( password = password, userName = userName)
        }
    }

    var password = repository.password

    var userName = repository.userName
}



