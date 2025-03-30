package com.example.appnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appnote.data.Note
import com.example.appnote.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    // Biến private để chứa danh sách ghi chú
    private val _notes = MutableLiveData<List<Note>>()

    // Biến public để Activity quan sát nhưng không chỉnh sửa được
    val notes: LiveData<List<Note>> get() = _notes
    private val customScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    // Tải toàn bộ ghi chú từ database
    fun loadNotes() {
        customScope.launch {
            val data = repository.getAllNotes()
            _notes.postValue(data)
        }
    }

    fun searchNotes(keyword:String){
        customScope.launch {
            val results = repository.searchNotes(keyword)
            _notes.postValue(results)
        }
    }

    fun addNote(note: Note){
        customScope.launch {
            repository.insert(note)
            loadNotes()
        }
    }

    fun deleteNote(note: Note){
        customScope.launch {
            repository.delete(note)
            loadNotes()
        }
    }

    override fun onCleared() {
        super.onCleared()
        customScope.cancel()
    }
}