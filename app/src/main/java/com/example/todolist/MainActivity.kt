package com.example.todolist

import android.app.appsearch.GlobalSearchSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.RoomDatabase.TodoDataBase
import com.example.todolist.RoomDatabase.TodoItem
import com.example.todolist.RoomDatabase.TodoItemDao
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var appDataBase: TodoDataBase
    lateinit var todoList: MutableList<TodoItem>
    lateinit var adapter: TodoAdapter
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var databaseList : MutableList<TodoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        todoList = mutableListOf<TodoItem>()


         appDataBase = TodoDataBase.getDatabase(this)
        GlobalScope.launch {
               var databaseList = async{ appDataBase.todoItemDao().getAll() }
                Log.d("databaseList", databaseList.toString())
                todoList = databaseList.await()
                adapter.setTask(todoList)
        }


         Log.d("Todolist", todoList.toString())


        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TodoAdapter(this)
        binding.recyclerView.adapter = adapter

        Log.d("databaseList", todoList.toString())

        loadData()

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
    fun loadData() {
        adapter.setTask(todoList)
    }
    fun deleteAll() {
        GlobalScope.launch {
            appDataBase.todoItemDao().deleteAll()
        }
        this.todoList.clear()
        databaseList.clear()

        adapter.setTask(todoList)
    }
}