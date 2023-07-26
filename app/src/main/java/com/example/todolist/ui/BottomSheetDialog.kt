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
    lateinit var taskViewModel: TaskViewModel
    lateinit var binding : NewTaskBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewTaskBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        //taskViewModel =  ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            Log.d("MainActivity1", "beforesavebutton")
            saveData()
            Log.d("MainActivity1", "aftersavebutton")
        }
    }

    fun saveData() {

        //taskViewModel.title.value = binding.newTaskText.text.toString()
        //binding.newTaskText.text.clear()
        //dismiss()

        var name = binding.newTaskText.text.toString()
        //if(name.isEmpty()) {
        //    Toast.makeText(context, "Please enter task name", Toast.LENGTH_SHORT).show()
       //     Log.d("MainActivity1", name.toString())
        //}

        Log.d("MainActivity1", "withinsavebutton")
        val newTask = TodoItem(0,name, 0)
        addDialogListener.onAddButtonClicked(newTask)
        Log.d("MainActivity1", newTask.toString())
        dismiss()
    }
}