package com.example.todolist.ui.Dialog

import com.example.todolist.data.roomdb.entities.TodoItem

interface AddDialogListener {
    fun onAddButtonClicked(newTask : TodoItem)
}