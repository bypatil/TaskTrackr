package com.example.tasktrackr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TaskViewModel(private val taskRepository:TaskRepository): ViewModel() {

    private val _allTask= MutableLiveData<List<Task>>()
    val allTask: LiveData<List<Task>> get() =_allTask

    fun addTask(task:Task){
        taskRepository.insertTask(task)
    }

    fun updateTask(task:Task){
        taskRepository.updateTask(task)
    }

    fun deleteTask(task:Task){
        taskRepository.deleteTask(task)
    }

    fun fetchAllTask(){
        taskRepository.getAllTask{ task ->
            //post the result back to the LiveData
            _allTask.postValue(task)

        }
    }
}