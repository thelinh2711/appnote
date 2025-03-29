package com.example.appnote.repository

import com.example.appnote.data.Note
import com.example.appnote.data.NoteDAO

class NoteRepository(private val noteDAO: NoteDAO) {
    fun getAllNotes(): List<Note> {
        return noteDAO.getAllNotes()
    }

    fun insert(note: Note){
        noteDAO.insert(note)
    }

    fun delete(note: Note){
        noteDAO.delete(note)
    }

    fun searchNotes(keyword: String):List<Note>{
        return noteDAO.searchNotes("%$keyword%")
    }
}