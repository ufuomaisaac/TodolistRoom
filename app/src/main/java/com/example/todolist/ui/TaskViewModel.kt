package com.example.todolist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TaskViewModel(repository: TodoRepository) : ViewModel() {
    var list = repository.getAllTodoItem()
    var title = MutableLiveData<String>()
}