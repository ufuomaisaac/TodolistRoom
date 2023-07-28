package com.example.todolist.ui

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.todolist.R
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.ItemtodoBinding

class TodoDialog(context: Context, var addDialogListener: AddDialogListener) :
    AppCompatDialog(context) {

    var addtv = findViewById<TextView>(R.id.tvAdd)
    var etName = findViewById<TextView>(R.id.etName)
    var tvCancel = findViewById<TextView>(R.id.tvCancel)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_todo)

        addtv?.setOnClickListener {
        val name = etName?.text.toString()
            //val amount = etAmount.text.toString().toInt()
//            if(name.isNullOrEmpty()) {
//                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val item = TodoItem(0,name,0)
            addDialogListener.onAddButtonClicked(item)
            dismiss()
        }

        tvCancel?.setOnClickListener {
            cancel()
        }
    }
}
