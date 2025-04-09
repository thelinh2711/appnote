package com.example.appnote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appnote.data.NoteDatabase
import com.example.appnote.databinding.ActivityMainBinding
import com.example.appnote.repository.NoteRepository
import com.example.appnote.ui.adapter.NoteAdapter
import com.example.appnote.viewmodel.NoteViewModel
import com.example.appnote.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter

    private val viewModel: NoteViewModel by viewModels{
        NoteViewModelFactory(
            NoteRepository(
                NoteDatabase.getInstance(this).noteDao()
            )
        )
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        noteAdapter = NoteAdapter(
            notes = TODO(),
            onNoteClick = TODO(),
            onDeleteClick = TODO()
        )


    }
}