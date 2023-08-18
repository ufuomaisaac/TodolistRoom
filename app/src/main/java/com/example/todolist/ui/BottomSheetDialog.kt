package com.example.todolist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.databinding.NewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(var addDialogListener: AddDialogListener) : BottomSheetDialogFragment()  {
    lateinit var binding : NewTaskBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewTaskBinding.inflate(inflater, container, false )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }

    fun saveData() {
        var name = binding.newTaskText.text.toString()
        val newTask = TodoItem(0,name, 0)
        addDialogListener.onAddButtonClicked(newTask)
        dismiss()
    }
}