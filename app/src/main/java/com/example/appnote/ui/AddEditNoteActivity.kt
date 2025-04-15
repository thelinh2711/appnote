package com.example.appnote.ui

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appnote.R
import com.example.appnote.data.Note
import com.example.appnote.data.NoteDatabase

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var btnSave: ImageButton
    private lateinit var btnBack: ImageButton


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

        val noteId = intent.getIntExtra("note_id", -1)
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
            val note = Note(title=title, content = content)
            Thread {
                NoteDatabase.getInstance(this).noteDao().insert(note)
                runOnUiThread {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }.start()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}