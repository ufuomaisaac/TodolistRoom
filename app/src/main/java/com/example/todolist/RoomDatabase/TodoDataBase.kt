package com.example.todolist.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 1 )
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {

        @Volatile
         var databaseInstance: TodoDataBase? = null

        fun getDatabase(context: Context): TodoDataBase {

            val tempInstance = databaseInstance
            if (tempInstance != null) {
                return tempInstance
            }
            else {
                val instance = Room.databaseBuilder(
                    context,
                    TodoDataBase::class.java,
                    "todo_database"
                ).build()
                databaseInstance = instance
                return instance
            }

        }

    }
}