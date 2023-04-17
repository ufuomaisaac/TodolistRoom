package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.RoomDatabase.TodoItem

class TodoAdapter(var activity : MainActivity) : RecyclerView.Adapter<TodoAdapter.viewHolder>() {

     lateinit var listTodo : MutableList<TodoItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.itemtodo, parent, false)
        return viewHolder(view)
    }

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var isDone : CheckBox = itemView.findViewById(R.id.itemCheckBox)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val itemTodo = listTodo[position]
        holder.isDone.text = itemTodo.title
        holder.isDone.isChecked = if (itemTodo.status == 1) true else false
    }


    override fun getItemCount(): Int {
        return listTodo.size
    }

    fun setTask(list : MutableList<TodoItem>) {
        this.listTodo = list
        notifyDataSetChanged()
        notifyItemInserted(listTodo.size - 1)
    }
}