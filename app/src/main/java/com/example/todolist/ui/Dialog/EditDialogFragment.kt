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

        // Set the custom layout for the dialog
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_dialog, null)
        val editText = view.findViewById<EditText>(R.id.editText)

        editText.setText(currentItemTodo.title)

        builder.setView(view) // Set the custom layout as the dialog's content
            .setTitle("Edit Item Dialog") // Set the dialog title
            .setPositiveButton("OK") { dialog, _ ->
                val enteredText = editText.text.toString()
                val newItem = currentItemTodo.copy(title = enteredText)
                taskViewModel.insert(newItem)

                dialog.dismiss() // Dismiss the dialog
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                //
                dialog.dismiss() // Dismiss the dialog
            }

        return builder.create() // Create and return the dialog
    }

    /*override fun editItem(todoItem: TodoItem): String {
        val currentItemTitle = todoItem.title
        editTextTitle = currentItemTitle
        return currentItemTitle
    }*/
}