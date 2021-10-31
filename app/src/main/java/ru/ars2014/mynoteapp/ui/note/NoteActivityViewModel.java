package ru.ars2014.mynoteapp.ui.note;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import ru.ars2014.mynoteapp.data.model.Note;

public class NoteActivityViewModel extends BaseObservable {

    public ObservableField<Note> note = new ObservableField<>();
}
