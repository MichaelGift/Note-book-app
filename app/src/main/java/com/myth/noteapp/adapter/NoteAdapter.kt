package com.myth.noteapp.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myth.noteapp.databinding.NoteLayoutBinding
import com.myth.noteapp.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    class NoteViewHolder(private val itembinding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(itembinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.noteBody == newItem.noteBody
                    && oldItem.noteTitle == newItem.noteTitle
        }

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


}