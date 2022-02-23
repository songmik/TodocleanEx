package com.example.a23_todoclean.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a23_todoclean.data.entity.ToDoEntity

class ToDoAdapter: RecyclerView.Adapter<ToDoAdapter.ToDoItemViewHolder>() {

    private var toDoList: List<ToDoEntity> = listOf()
    private lateinit var toDoItemClickListener: (ToDoEntity) -> Unit
    private lateinit var toDoCheckListener: (ToDoEntity) -> Unit

    inner class ToDoItemViewHolder(
        private val binding: ViewHolderToDoItemBinding,
        val toDoItemClickListener: (ToDoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindData(data: ToDoEntity) = with(binding){

        }

        fun bindViews(data: ToDoEntity) {

        }

        private fun checkToDoComplete(isChecked: Boolean) = with(binding){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = ViewHolderToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(view, toDoItemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.bindData(toDoList[position])
        holder.bindViews(toDoList[position])
    }

    override fun getItemCount(): Int = toDoList.size

    fun setToDoList(toDoList: List<ToDoEntity>, toDoItemClickListener: (ToDoEntity) -> Unit, toDoCheckListener: (ToDoEntity) -> Unit){
        this.toDoList = toDoList
        this.toDoItemClickListener = toDoItemClickListener
        this.toDoCheckListener = toDoCheckListener
        notifyDataSetChanged()
    }
}