package com.example.todolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.RoomDatabase.TodoItem
import javax.security.auth.callback.Callback

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.viewHolder>() {

    lateinit var listTodo: MutableList<TodoItem>

    // A callback that gets invoked when an item is checked (or unchecked)
    private var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemtodo, parent, false)
        return viewHolder(view)
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var isDone: CheckBox = itemView.findViewById(R.id.itemCheckBox)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val itemTodo = listTodo[position]
        holder.isDone.text = itemTodo.title
        holder.isDone.isChecked = itemTodo.status == 1

        holder.isDone.setOnCheckedChangeListener { _, isChecked ->
            callback?.onCheckedChanged(itemTodo, isChecked)
        }


    }


    override fun getItemCount(): Int {
        return listTodo.size

    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    // Callback interface, used to notify when an item's checked status changed
    interface Callback {
        fun onCheckedChanged(item: TodoItem, isChecked: Boolean)
    }


    fun setTask(list: MutableList<TodoItem>) {
        this.listTodo = list
        notifyDataSetChanged()
        notifyItemInserted(listTodo.size - 1)
    }
}