package com.example.todolist.ui.Dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.todolist.R

class CustomDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Set the custom layout for the dialog
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_dialog, null)
        val editText = view.findViewById<EditText>(R.id.editTextt)

        builder.setView(view) // Set the custom layout as the dialog's content
            .setTitle("Custom Dialog") // Set the dialog title
            .setPositiveButton("OK") { dialog, _ ->
                // Handle OK button click here
                val enteredText = editText.text.toString()
                // Do something with the entered text
                dialog.dismiss() // Dismiss the dialog
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Handle Cancel button click here
                dialog.dismiss() // Dismiss the dialog
            }

        return builder.create() // Create and return the dialog
    }
}