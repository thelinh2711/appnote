package com.example.appnote

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnote.data.Note
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

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NoteRepository(
                NoteDatabase.getInstance(this).noteDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter setup
        noteAdapter = NoteAdapter(
            notes = listOf(),
            onNoteClick = { note ->
                val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                    putExtra("note_id", note.id)
                    putExtra("note_title", note.title)
                    putExtra("note_content", note.content)
                }
                startActivity(intent)
            },
            onDeleteClick = { note ->
                AlertDialog.Builder(this)
                    .setTitle("Xoá ghi chú?")
                    .setMessage("Bạn có chắc chắn muốn xoá ghi chú \"${note.title}\" không?")
                    .setPositiveButton("Xoá") { _, _ ->
                        viewModel.deleteNote(note)
                    }
                    .setNegativeButton("Huỷ", null)
                    .show()
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        // Quan sát dữ liệu từ ViewModel
        viewModel.notes.observe(this) { notes ->
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

        // Tải dữ liệu ban đầu
        viewModel.loadNotes()

        // Sự kiện nút thêm
        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this, AddEditNoteActivity::class.java))
        }

        // Nút tìm kiếm (chưa làm)
        binding.btnSearch.setOnClickListener {
            Toast.makeText(this, "Chức năng tìm kiếm chưa được triển khai", Toast.LENGTH_SHORT).show()
        }

        // Nút đồng hồ (chưa làm)
        binding.btnClock.setOnClickListener {
            Toast.makeText(this, "Đồng hồ hiện chưa hoạt động", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes()
    }
}
