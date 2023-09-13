package com.example.todolist.ui.Dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.todolist.R
import com.example.todolist.data.db.entities.TodoItem
import com.example.todolist.ui.TaskViewModel

class EditDialogFragment(private var taskViewModel: TaskViewModel, var currentItemTodo: TodoItem): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_dialog, null)
        val editText = view.findViewById<EditText>(R.id.editText)
        editText.setText(currentItemTodo.title)

        builder.setView(view)
            .setTitle("Edit Task")
            .setPositiveButton("OK") { dialog, _ ->
                val enteredText = editText.text.toString()
                val newItem = currentItemTodo.copy(title = enteredText)
                taskViewModel.insert(newItem)

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }
}

