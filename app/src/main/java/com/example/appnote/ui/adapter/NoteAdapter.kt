package com.example.appnote.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnote.R
import com.example.appnote.data.Note
import java.util.zip.Inflater

class NoteAdapter(
    private var notes: List<Note>,
    private val onNoteClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val ivVoiceIcon: ImageView = itemView.findViewById(R.id.ivVoiceIcon)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.tvTitle.text = note.title
        holder.tvTimestamp.text =
            java.text.SimpleDateFormat("dd MMM yyyy, HH:mm").format(java.util.Date(note.timestamp))
        holder.ivVoiceIcon.visibility = if (note.audioPath != null) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener { onNoteClick(note) }
        holder.btnDelete.setOnClickListener {
            onDeleteClick(note)
        }
    }
}