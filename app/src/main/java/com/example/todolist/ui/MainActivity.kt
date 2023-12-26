package com.example.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.db.TodoDataBase
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.Dialog.AddDialogListener
import com.example.todolist.ui.Dialog.BottomSheetDialog
import com.example.todolist.ui.Dialog.EditDialogFragment
import com.google.android.material.snackbar.Snackbar


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

    override fun onCheckedChanged(item: TodoItem, isChecked : Boolean) {
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
                     val item = adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                     val snackbar = Snackbar.make(binding.layout,R.string.snackbar_text, Snackbar.LENGTH_LONG)
                         .setAction(R.string.action_button, View.OnClickListener {
                             taskViewModel.insert(item)
                         }).show()
                 }

                 ItemTouchHelper.RIGHT -> {
                     val currentItem = adapter.addItem((viewHolder.absoluteAdapterPosition))
                     val customDialog = EditDialogFragment(taskViewModel, currentItem)
                     customDialog.show(supportFragmentManager, "customDialog")
                 }
             }
         }
     }
}