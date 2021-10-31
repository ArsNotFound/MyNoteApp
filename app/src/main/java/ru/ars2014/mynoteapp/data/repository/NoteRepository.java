package ru.ars2014.mynoteapp.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.ars2014.mynoteapp.data.database.AppDatabase;
import ru.ars2014.mynoteapp.data.database.NoteDao;
import ru.ars2014.mynoteapp.data.model.Note;

public class NoteRepository {
    private final NoteDao noteDao;
    private final LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        noteDao = db.getNoteDao();
        allNotes = noteDao.getAll();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Note> getByID(Long id) {
        return noteDao.getByID(id);
    }

    public LiveData<List<Note>> searchByText(String text) {
        return noteDao.searchByText(text);
    }

    public void insertAll(Note... notes) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insertAll(notes);
        });
    }

    public void update(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.update(note);
        });
    }

    public void delete(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }
}
