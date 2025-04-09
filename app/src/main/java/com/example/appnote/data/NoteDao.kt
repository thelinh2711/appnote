package com.example.appnote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY created_time DESC")
    fun getAllNotes() : List<Note>

    @Query("SELECT * FROM notes WHERE note_title LIKE:keyword ORDER BY created_time DESC")
    fun searchNotes(keyword : String) : List<Note>
}