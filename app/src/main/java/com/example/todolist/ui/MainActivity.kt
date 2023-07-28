package com.example.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.db.TodoDataBase
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.others.TodoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    lateinit var appDataBase: TodoDataBase
    lateinit var todoList: MutableList<TodoItem>
    lateinit var adapter: TodoAdapter
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var databaseList : MutableList<TodoItem>
    lateinit var taskViewModel : TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDataBase = TodoDataBase.getDatabase(this)
        val repository = TodoRepository(appDataBase)
        val factory = TodoViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        supportActionBar?.hide()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter()
//        adapter.setCallback(this)
            binding.recyclerView.adapter = adapter

//        this enables enables the
        taskViewModel.list.observe(this, Observer { data ->
            todoList = data
            Log.d("Todolist101", todoList.toString())
            adapter.setTask(todoList)
        })

        binding.fab.setOnClickListener{
        BottomSheetDialog(object : AddDialogListener {
                override fun onAddButtonClicked(newTask: TodoItem) {
                   Log.d("MainActivity1", newTask.toString())
                    taskViewModel.insert(newTask)
                }
            }).show(supportFragmentManager, "newTextTask")
        }
      taskViewModel.title.observe(this, Observer { data ->
       writeData(data.toString())
      })

//        binding.fab.setOnClickListener {
//            TodoDialog(this@MainActivity, object : AddDialogListener{
//                override fun onAddButtonClicked(newTask: TodoItem) {
//                    taskViewModel.insert(newTask)
//                }
//            }).show()
//        }
    }

    fun writeData(newTask : String) {

        if (newTask != null) {
            var newTask = TodoItem(0, newTask,0)

            CoroutineScope(Dispatchers.IO).launch {
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
        CoroutineScope(Dispatchers.IO).launch {
            appDataBase.todoItemDao().deleteAll()
        }
        this.todoList.clear()
        databaseList.clear()
        adapter.setTask(todoList)
    }

     fun onCheckedChanged(item: TodoItem, isChecked: Boolean) {
        val newStatus = if (isChecked) 1 else 0
        val newItem = item.copy(
            status = newStatus
        )
//        val newTodo = TodoItem(
//            id = item.id,
//            title = item.title,
//            status = newStatus,
//        )

        CoroutineScope(Dispatchers.IO).launch {
            appDataBase.todoItemDao().insert(newItem)
        }
    }
}