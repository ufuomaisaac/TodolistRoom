package com.example.todolist

import android.app.appsearch.GlobalSearchSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.RoomDatabase.TodoDataBase
import com.example.todolist.RoomDatabase.TodoItem
import com.example.todolist.RoomDatabase.TodoItemDao
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var appDataBase : TodoDataBase
    lateinit var database : TodoDataBase
    lateinit var todoList : MutableList<TodoItem>
    lateinit var adapter : TodoAdapter
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        todoList = mutableListOf<TodoItem>()

        database = TodoDataBase.getDatabase(this)
        appDataBase = Room.databaseBuilder(applicationContext, TodoDataBase::class.java, "todo_database").build()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TodoAdapter(this)
        binding.recyclerView.adapter = adapter
        adapter.setTask(todoList)



        binding.fab.setOnClickListener {
            writeData()
        }

    }

    fun writeData() {
        val taskTitle = findViewById<EditText>(R.id.newTask)

        if (taskTitle != null) {
            var newTask = TodoItem(0, taskTitle.text.toString(), 0)

            GlobalScope.launch {
                appDataBase.todoItemDao().insert(newTask)
            }
            todoList.add(newTask)
            adapter.setTask(todoList)
        }
    }

}