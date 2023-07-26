package com.example.todolist.ui

import com.example.todolist.data.db.entities.TodoItem

interface AddDialogListener {
    fun onAddButtonClicked(newTask : TodoItem)
}