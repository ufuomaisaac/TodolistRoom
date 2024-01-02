package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.datastore.UserPreferenceRepository

class DataStoreViewModelFactory(
    private val repository: UserPreferenceRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserPreferenceRepository::class.java)
            .newInstance(repository)
    }
}