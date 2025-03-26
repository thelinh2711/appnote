package com.example.appnote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int = 0,

    @ColumnInfo(name = "note_title")
    val title: String,

    @ColumnInfo(name = "note_content")
    val content: String,

    @ColumnInfo(name = "audio_path")
    val audioPath: String? = null,

    @ColumnInfo(name = "created_time")
    val timestamp: Long = System.currentTimeMillis()
)