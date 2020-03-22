package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    public static final String UPDATED_NOTE = "note_text";
    private EditText etNote;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> noteList;

    EditNoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etNote = findViewById(R.id.etNote);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnCancel = findViewById(R.id.btnCancel);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }

        noteViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);

        noteList = noteViewModel.getNote(noteId);
        noteList.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                etNote.setText(note.getNote());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdate();
            }
        });
    }

    private void updateNote() {

        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void cancelUpdate() {
        finish();
    }
}
