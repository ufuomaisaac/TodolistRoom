package com.example.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var isChecked = MutableLiveData<String>()
}