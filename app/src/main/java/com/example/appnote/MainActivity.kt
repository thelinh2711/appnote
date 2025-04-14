package com.example.appnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnote.data.NoteDatabase
import com.example.appnote.databinding.ActivityMainBinding
import com.example.appnote.repository.NoteRepository
import com.example.appnote.ui.AddEditNoteActivity
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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        viewModel.notes.observe(this) {notes ->
            if (notes.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.ivEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.ivEmptyState.visibility = View.GONE
                binding.tvEmptyState.visibility = View.GONE
                noteAdapter.updateList(notes)
            }
        }

        // Load ghi chú ban đầu
        viewModel.loadNotes()

        // Sự kiện thêm ghi chú mới
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }

        // (Optional) nút tìm kiếm
        binding.btnSearch.setOnClickListener {
            // TODO: mở search UI hoặc filter
        }

        // (Optional) nút đồng hồ
        binding.btnClock.setOnClickListener {
            // TODO: mở đồng hồ hoặc hiển thị thời gian
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes() // Reload khi quay về màn chính
    }
}