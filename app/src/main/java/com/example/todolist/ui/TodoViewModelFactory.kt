package com.example.todolist.ui

import android.app.ApplicationErrorReport
import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.repository.TodoRepository

class TodoViewModelFactory(private var repository: TodoRepository) :  ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}