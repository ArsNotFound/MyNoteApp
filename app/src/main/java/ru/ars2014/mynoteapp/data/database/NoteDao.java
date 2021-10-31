package ru.ars2014.mynoteapp.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.ars2014.mynoteapp.data.model.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id")
    LiveData<List<Note>> getAll();

    @Query("SELECT * FROM note WHERE id = (:id)")
    LiveData<Note> getByID(Long id);

    @Query("SELECT * FROM note WHERE title LIKE (:text) OR content LIKE (:text) ORDER BY id")
    LiveData<List<Note>> searchByText(String text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note")
    void deleteAll();
}
