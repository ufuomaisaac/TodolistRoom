package com.example.todolist.data.repository

import com.example.todolist.data.db.TodoDataBase
import com.example.todolist.data.db.entities.TodoItem

class TodoRepository(var db : TodoDataBase){
    suspend fun insert(todo : TodoItem) = db.todoItemDao().insert(todo)

    fun getAllTodoItem() = db.todoItemDao().getAll()

    suspend fun deleteAll() = db.todoItemDao().deleteAll()

    suspend fun delete(todo: TodoItem) = db.todoItemDao().delete(todo)
}