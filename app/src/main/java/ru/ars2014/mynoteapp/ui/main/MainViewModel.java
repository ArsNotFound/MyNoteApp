package ru.ars2014.mynoteapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.ars2014.mynoteapp.data.model.Note;
import ru.ars2014.mynoteapp.data.repository.NoteRepository;

public class MainViewModel extends AndroidViewModel {
    private final LiveData<List<Note>> allNotes;
    private final NoteRepository noteRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        noteRepository.insertAll(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public LiveData<List<Note>> search(String text) {
        return noteRepository.searchByText(text);
    }

    public LiveData<Note> get(Long id) {
        return noteRepository.getByID(id);
    }
}
