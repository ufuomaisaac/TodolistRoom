package com.example.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.db.TodoDataBase
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.others.TodoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), TodoAdapter.Callback {
    lateinit var binding: ActivityMainBinding
    lateinit var appDataBase: TodoDataBase
    lateinit var todoList: MutableList<TodoItem>
    lateinit var adapter: TodoAdapter
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDataBase = TodoDataBase.getDatabase(this)
        val repository = TodoRepository(appDataBase)
        val factory = TodoViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        supportActionBar?.hide()

         var itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(taskViewModel)  
        adapter.setCallback(this)
        binding.recyclerView.adapter = adapter


        taskViewModel.list.observe(this, Observer { data ->
            todoList = data
            Log.d("Todolist101", todoList.toString())
            adapter.setTask(todoList)
        })

        binding.fab.setOnClickListener {
            BottomSheetDialog(object : AddDialogListener {
                override fun onAddButtonClicked(newTask: TodoItem) {
                    Log.d("MainActivity1", newTask.toString() + "interface connector")
                    taskViewModel.insert(newTask)
                }
            }).show(supportFragmentManager, "newTextTask")
        }
    }

    override fun onCheckedChanged(item: TodoItem, isChecked: Boolean) {
        val newStatus = if (isChecked) 1 else 0
        val newItem = item.copy(
            status = newStatus
        )
        taskViewModel.insert(newItem)
    }



     val swipeGesture = object : SwipeGesture(this) {
         override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             when (direction) {
                 ItemTouchHelper.LEFT -> {
                     adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                     val snackbar = Snackbar.make(binding.layout,R.string.snackbar_text, Snackbar.LENGTH_LONG)
                         .setAction(R.string.action_button, View.OnClickListener {

                         }).show()
                     /*val builder = AlertDialog.Builder(this@MainActivity, R.style.MyDialogTheme )
                     builder.setTitle("Confimation")
                     builder.setMessage("Are you sure you want to delete this item")

                     builder.setPositiveButton("Yes") { dialog, which ->
                         adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                         dialog.dismiss()
                     }
                     builder.setNegativeButton("No") { dialog, which ->
                         dialog.dismiss()
                     }
                     val alertDialog = builder.create()
                     alertDialog.show()*/

                 }

                 ItemTouchHelper.RIGHT -> {
                     val archiveItem = todoList[viewHolder.absoluteAdapterPosition]
                     adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                     adapter.addItem(viewHolder.absoluteAdapterPosition, archiveItem)

                 }
             }
         }
     }
}