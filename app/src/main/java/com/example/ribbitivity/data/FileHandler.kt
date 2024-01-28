package com.example.ribbitivity.data

import android.content.Context
import com.example.ribbitivity.model.Todo
import java.io.FileNotFoundException
import java.io.IOException

class FileHandler(private val context: Context) {

    private val fileName = "tasks.txt"

    fun readTasks(): List<Todo> {
        val tasks = mutableListOf<Todo>()
        try {
            val fileInputStream = context.openFileInput(fileName)
            fileInputStream.bufferedReader().useLines { lines ->
                lines.forEach {
                    val taskInfo = it.split(",")
                    val task = taskInfo[0]
                    val status = taskInfo[1].toBoolean()
                    tasks.add(Todo(task, status))
                }
            }
            fileInputStream.close()
        } catch (e: FileNotFoundException) {
            // File doesn't exist, so create it
            createEmptyFile()
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
    private fun createEmptyFile() {
        try {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                // Write an empty string or any default content to the file
                it.write("".toByteArray())
            }
        } catch (e: IOException) {
            // Handle the IOException, e.g., log an error or throw an exception
            e.printStackTrace()
        }
    }
    fun clearAllTasks() {
        // Clear all tasks by overwriting the file with an empty string
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write("".toByteArray())
        }
    }

}