package com.example.todolist.others

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.ui.TaskViewModel

class TodoAdapter(var taskViewModel: TaskViewModel) : RecyclerView.Adapter<TodoAdapter.viewHolder>() {
    var listTodo: List<TodoItem> = emptyList()
    lateinit var myCallback: Callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemtodo, parent, false)
        return viewHolder(view)
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var isDone: CheckBox = itemView.findViewById(R.id.itemCheckBox)

        init {
            isDone.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val itemPosition = absoluteAdapterPosition
            val isCheckedStatus = isDone.isChecked
            Log.d("MainActivity", itemPosition.toString())
            if (itemPosition != RecyclerView.NO_POSITION) {
                val currentItem = listTodo[itemPosition]
                Log.d("MainActivity", currentItem.toString())
                myCallback?.onCheckedChanged(currentItem, isCheckedStatus)

//                isDone.setOnCheckedChangeListener { _, isChecked ->
//                    Log.d("MainActivity",isChecked.toString() )
//                    myCallback?.onCheckedChanged(currentItem, isChecked) }
            }
        }
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val itemTodo = listTodo[position]
        holder.isDone.text = itemTodo.title
        holder.isDone.isChecked = itemTodo.status == 1

//        holder.isDone.setOnCheckedChangeListener { _, isChecked ->
//            callback?.onCheckedChanged(itemTodo, isChecked)
//        }

    }

    override fun getItemCount(): Int {
        return listTodo.size
    }

    fun setCallback(callback: Callback?) {
        if (callback != null) {
            this.myCallback = callback
        }
    }

    // Callback interface, used to notify when an item's checked status changed
//    interface Callback {
//        fun onCheckedChanged(item: TodoItem, isChecked: Boolean)
//    }
    interface Callback {
        fun onCheckedChanged(item: TodoItem, ischecked: Boolean)
    }

    fun setTask(list: List<TodoItem>) {
        this.listTodo = list
        notifyDataSetChanged()
        notifyItemInserted(listTodo.size - 1)
    }

    fun deleteItem(itemPosition: Int) :TodoItem {
        val item = listTodo[itemPosition]
        taskViewModel.delete(item)
        notifyDataSetChanged()
        return item

    }

    fun addItem(itemPosition: Int, item: TodoItem) {
        val item = listTodo[itemPosition]
        taskViewModel.delete(item)
        val newItem = TodoItem(0 , item.title, item.status)
        taskViewModel.insert(newItem)
    }
}
