package com.example.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.todolist.data.db.entities.TodoItem

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todo_table")
    fun getAll(): LiveData<MutableList<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Query("DELETE FROM todo_table")
    fun deleteAll()

}