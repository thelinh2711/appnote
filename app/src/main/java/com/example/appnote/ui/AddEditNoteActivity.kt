package com.example.appnote.ui

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appnote.R
import com.example.appnote.data.Note
import com.example.appnote.data.NoteDatabase
import com.example.appnote.repository.NoteRepository
import com.example.appnote.viewmodel.NoteViewModel
import com.example.appnote.viewmodel.NoteViewModelFactory

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var btnSave: ImageButton
    private lateinit var btnBack: ImageButton
    private var noteId: Int = -1

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NoteRepository(NoteDatabase.getInstance(this).noteDao())
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        initView();
    }

    private fun initView() {
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        btnSave = findViewById(R.id.btnSave)
        btnBack = findViewById(R.id.btnBack)

        noteId = intent.getIntExtra("note_id", -1)
        val noteTitle = intent.getStringExtra("note_title") ?: ""
        val noteContent = intent.getStringExtra("note_content") ?: ""

        // Nếu là ghi chú có sẵn → hiển thị
        if (noteId != -1) {
            etTitle.setText(noteTitle)
            etContent.setText(noteContent)
        }
        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            if(title.isEmpty() && content.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập tiêu đề hoặc nội dung", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val note = Note(
                id = noteId,
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )

            if(noteId==-1){
                //Thêm ghi chú mới
                viewModel.addNote(note)
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                //cập nhật note
                showUpdateConfirmation(note)
            }

        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun AddEditNoteActivity.showUpdateConfirmation(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Cập nhật ghi chú?")
            .setMessage("Bạn có chắc chắn muốn thay đổi nội dung này không?")
            .setPositiveButton("Thay đổi") {_, _, ->
                viewModel.updateNote(note)
                setResult(Activity.RESULT_OK)
                finish()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}


