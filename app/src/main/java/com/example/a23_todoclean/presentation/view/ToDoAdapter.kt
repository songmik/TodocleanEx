package com.example.a23_todoclean.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a23_todoclean.R
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.databinding.ViewholderTodoItemBinding

class ToDoAdapter: RecyclerView.Adapter<ToDoAdapter.ToDoItemViewHolder>() {

    private var toDoList: List<ToDoEntity> = listOf()
    private lateinit var toDoItemClickListener: (ToDoEntity) -> Unit
    private lateinit var toDoCheckListener: (ToDoEntity) -> Unit

    inner class ToDoItemViewHolder(
        private val binding: ViewholderTodoItemBinding,
        val toDoItemClickListener: (ToDoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindData(data: ToDoEntity) = with(binding){
            checkbox.text = data.title
            checkToDoComplete(data.hasCompleted)
        }

        fun bindViews(data: ToDoEntity) {
            binding.checkbox.setOnClickListener {
                toDoCheckListener(
                    data.copy(hasCompleted = binding.checkbox.isChecked)
                )
                checkToDoComplete(binding.checkbox.isChecked)
            }
            binding.root.setOnClickListener {
                toDoItemClickListener(data)
            }
        }

        private fun checkToDoComplete(isChecked: Boolean) = with(binding){
            checkbox.isChecked = isChecked
            container.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if(isChecked){
                        R.color.gray_300
                    } else {
                        R.color.white
                    }
                )
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(view, toDoItemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.bindData(toDoList[position])
        holder.bindViews(toDoList[position])
    }

    override fun getItemCount(): Int = toDoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setToDoList(toDoList: List<ToDoEntity>, toDoItemClickListener: (ToDoEntity) -> Unit, toDoCheckListener: (ToDoEntity) -> Unit){
        this.toDoList = toDoList
        this.toDoItemClickListener = toDoItemClickListener
        this.toDoCheckListener = toDoCheckListener
        notifyDataSetChanged()
    }
}