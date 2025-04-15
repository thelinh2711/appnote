package com.example.appnote.repository

import com.example.appnote.data.Note
import com.example.appnote.data.NoteDao

class NoteRepository(private val noteDAO: NoteDao) {
    fun getAllNotes(): List<Note> {
        return noteDAO.getAllNotes()
    }

    fun insert(note: Note){
        noteDAO.insert(note)
    }

    fun delete(note: Note){
        noteDAO.delete(note)
    }

    fun update(note: Note){
        noteDAO.update(note)
    }
    fun searchNotes(keyword: String):List<Note>{
        return noteDAO.searchNotes("%$keyword%")
    }
}