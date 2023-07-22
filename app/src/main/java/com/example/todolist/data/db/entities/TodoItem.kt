package com.example.todolist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo var title: String,
    @ColumnInfo var status: Int
    )