package com.example.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.RoomDatabase.TodoItem

class TaskViewModel : ViewModel() {
    var list = MutableLiveData<MutableList<TodoItem>>()
    var title = MutableLiveData<String>()
    var isChecked = MutableLiveData<String>()
}