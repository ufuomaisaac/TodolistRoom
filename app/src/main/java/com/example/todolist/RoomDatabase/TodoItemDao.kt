package com.example.todolist.RoomDatabase

import androidx.core.content.contentValuesOf
import androidx.room.*

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todo_table")
    fun getAll(): List<TodoItem>


    @Query("SELECT * FROM todo_table WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): TodoItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(student: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Query("DELETE FROM todo_table")
    fun deleteAll()

}