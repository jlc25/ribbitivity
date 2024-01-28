package com.example.ribbitivity.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ribbitivity.R
import com.example.ribbitivity.data.FileHandler
import com.example.ribbitivity.model.Todo
import com.example.ribbitivity.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var fileHandler: FileHandler

    private val _tasks = mutableListOf<Todo>()
    private lateinit var textView: TextView
    private lateinit var editTextTask: EditText
    private lateinit var btnConfirmAdd: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        textView = binding.textList
        editTextTask = binding.editTextTask
        btnConfirmAdd = binding.btnConfirmAdd

        val btnAddTask: Button = binding.btnAddTask
//        val btnRemoveTask: Button = binding.btnRemoveTask
        val btnClearAllTasks: Button = binding.btnClearAllTasks

        fileHandler = FileHandler(requireContext())

        // Initialize tasks from the file
        _tasks.addAll(fileHandler.readTasks())
        updateUI()

        btnAddTask.setOnClickListener {
            // Show the EditText and Confirm button
            editTextTask.visibility = View.VISIBLE
            btnConfirmAdd.visibility = View.VISIBLE
        }

        btnConfirmAdd.setOnClickListener {
            val taskDescription = editTextTask.text.toString()
            if (taskDescription.isNotBlank()) {
                addTask(taskDescription)
                // Hide the EditText and Confirm button after adding the task
                editTextTask.visibility = View.GONE
                btnConfirmAdd.visibility = View.GONE
                // Clear the EditText
                editTextTask.text.clear()
            }
        }

//        btnRemoveTask.setOnClickListener {
//            removeTask("Task to Remove")
//        }
        btnClearAllTasks.setOnClickListener {
            clearAllTasks()
        }

        return view
    }
    private fun clearAllTasks() {
        _tasks.clear()
        fileHandler.clearAllTasks()
        updateUI()
    }
    private fun addTask(taskDescription: String) {
        val newTask = Todo(taskDescription, false)
        _tasks.add(newTask)
        saveTasksToFile()
        updateUI()
    }

//    private fun removeTask(taskDescription: String) {
//        _tasks.removeAll { it.task == taskDescription }
//        saveTasksToFile()
//        updateUI()
//    }

    private fun saveTasksToFile() {
        fileHandler.saveTasksToFile(_tasks)
    }

    private fun updateUI() {
        val tasksText = _tasks.joinToString("\n") { it.task }
        textView.text = tasksText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
