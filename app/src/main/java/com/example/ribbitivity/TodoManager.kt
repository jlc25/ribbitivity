package com.example.ribbitivity

import com.example.ribbitivity.data.FileHandler
import com.example.ribbitivity.model.Todo

class TodoManager(private val fileHandler: FileHandler) {

    fun addTask(task: String) {
        val newTask = Todo(task, false)
        fileHandler.writeTask(newTask)
    }

    fun removeTask(task: String) {
        val tasks = fileHandler.readTasks().toMutableList()
        tasks.removeAll { it.task == task }
        saveTasks(tasks)
    }
    private fun saveTasks(tasks: List<Todo>) {
        fileHandler.saveTasksToFile(tasks)
    }

    fun updateTaskStatus(task: String, completed: Boolean) {
        val tasks = fileHandler.readTasks().toMutableList()
        val updatedTask = tasks.find { it.task == task }

        updatedTask?.let {
            val updatedIndex = tasks.indexOf(it)
            tasks[updatedIndex] = it.copy(status = completed)
            saveTasks(tasks)
        }
    }
}