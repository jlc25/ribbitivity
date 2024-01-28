package com.example.ribbitivity.data

import android.content.Context
import com.example.ribbitivity.model.Todo
import java.io.FileNotFoundException

class FileHandler(private val context: Context) {

    private val fileName = "tasks.txt"

    fun readTasks(): List<Todo> {
        val tasks = mutableListOf<Todo>()
        try {
            context.openFileInput(fileName).bufferedReader().useLines { lines ->
                lines.forEach {
                    val taskInfo = it.split(",")
                    val task = taskInfo[0]
                    val status = taskInfo[1].toBoolean()
                    tasks.add(Todo(task, status))
                }
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("File was not found LIL BRO")
        }
        return tasks
    }

    fun writeTask(todo: Todo) {
        val taskString = "${todo.task},${todo.status}\n"
        context.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write(taskString.toByteArray())
        }
    }

    fun saveTasksToFile(tasks: List<Todo>) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            tasks.forEach { todo ->
                val taskString = "${todo.task},${todo.status}\n"
                it.write(taskString.toByteArray())
            }
        }
    }
}