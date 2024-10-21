package com.example.tasktrackr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val onUserClick: (Task) -> Unit):
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return TaskViewHolder(view)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int,){
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{ onUserClick(user)}
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val taskName: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(task: Task){
            val formattedTime = String.format("%02d:%02d", task.hour, task.minute)
            taskName.text = "${task.task}- $formattedTime"
        }
    }
    class TaskDiffCallback: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}