package com.example.tasktrackr

class TaskRepository(private val taskDao: TaskDao) {

    fun insertTask(task: Task){
        //run insert operation in a seperate thread
        Thread{
            taskDao.insertTask(task)
        }.start()
    }

    fun updateTask(task: Task){
        //run update operation in a seperate theread
        Thread{
            taskDao.updateTask(task)
        }.start()
    }

    fun deleteTask(task: Task){
        //run delete opeartion in a seperate theread
        Thread{
            taskDao.deleteTask(task)
        }.start()

    }

    fun getAllTask(callback: (List<Task>) -> Unit){
        //run query operation in a seperate therad
        Thread{
            val task = taskDao.getAllTask()
            //pass the result back to the callback
            callback(task)
        }.start()
    }
}