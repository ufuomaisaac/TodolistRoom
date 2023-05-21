package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.RoomDatabase.TodoDataBase
import com.example.todolist.RoomDatabase.TodoItem
import com.example.todolist.databinding.NewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BottomSheetDialog() : BottomSheetDialogFragment() {
    lateinit var taskViewModel: TaskViewModel
    lateinit var binding : NewTaskBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewTaskBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel =  ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }

    fun saveData() {
        taskViewModel.title.value = binding.newTaskText.text.toString()
        binding.newTaskText.text.clear()
        dismiss()
    }
}