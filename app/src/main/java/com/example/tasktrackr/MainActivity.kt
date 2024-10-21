package com.example.tasktrackr

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskName: EditText
    private lateinit var time: TimePicker
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button
    private lateinit var recyclerView: RecyclerView


    private var currentTask: Task? = null
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        //Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter { task -> onUserClicked(task) }
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Initialize Views
        taskName = findViewById(R.id.taskName)
        time = findViewById(R.id.time)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Set the TimePicker to 24-hour mode
        time.setIs24HourView(true)

        // Handle time selection
        time.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectedHour = hourOfDay
            selectedMinute = minute
        }


        //Initialize Database and ViewModel
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        val taskRepository = TaskRepository(taskDao)
        taskViewModel = TaskViewModel(taskRepository)

        //fetch all users when the activity starts
        taskViewModel.fetchAllTask()
        taskViewModel.allTask.observe(this) { users ->
            taskAdapter.submitList(users)//use submitList
        }

        //Add User Button click
        buttonAdd.setOnClickListener {
            val name = taskName.text.toString()
            val hour = time.hour
            val minute = time.minute


            if (name.isNotEmpty()) {
                val task = Task(0, name, hour, minute)//ID is auto-genereated
                taskViewModel.addTask(task)
                taskName.text.clear()
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                taskViewModel.fetchAllTask()
            } else {
                Toast.makeText(this, "plz fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Update User Button click
        buttonUpdate.setOnClickListener {
            currentTask?.let {
                val name = taskName.text.toString()
                val hour = time.hour
                val minute = time.minute


                if (name.isNotEmpty()) {
                    val updateTask = it.copy(task = name, hour = hour, minute = minute)
                    taskViewModel.updateTask(updateTask) // Call update function in ViewModel
                    taskName.text.clear()

                    Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                    taskViewModel.fetchAllTask() // Refresh the list after update
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "No task selected for update", Toast.LENGTH_SHORT).show()
            }

        }
        //delete user button click
        buttonDelete.setOnClickListener {
            currentTask?.let {
                taskViewModel.deleteTask(it)
                taskName.text.clear()
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                currentTask = null

                taskViewModel.fetchAllTask()
            } ?: run {
                Toast.makeText(this, "No task selected for deletion", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun onUserClicked(task: Task) {
        currentTask = task
        taskName.setText(task.task)

        // Set the TimePicker to the selected task time
        time.hour = task.hour
        time.minute = task.minute
    }

}
