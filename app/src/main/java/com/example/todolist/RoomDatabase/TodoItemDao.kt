package com.example.todolist.RoomDatabase

import androidx.core.content.contentValuesOf
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todo_table")
    fun getAll(): MutableList<TodoItem>


    @Query("SELECT * FROM todo_table WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): TodoItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Query("DELETE FROM todo_table")
    fun deleteAll()

}