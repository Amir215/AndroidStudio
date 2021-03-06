package com.example.roomdatabase.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomdatabase.Database.NoteDao;
import com.example.roomdatabase.Database.NoteRoomDatabase;

public class EditNoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "EditViewModel");
        noteDB = NoteRoomDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
    }

    public LiveData<Note> getNote(String noteId) {
        return noteDao.getNote(noteId);
    }
}
