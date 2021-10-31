package ru.ars2014.mynoteapp.ui.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.ars2014.mynoteapp.data.model.Note;

public class NoteContract extends ActivityResultContract<Note, Note> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Note input) {
        Intent intent = new Intent(context, NoteActivity.class);
        intent.putExtra(NoteActivity.NOTE_EXTRA, input);
        return intent;
    }

    @Override
    public Note parseResult(int resultCode, @Nullable Intent result) {
        if (resultCode != Activity.RESULT_OK || result == null) {
            return null;
        }

        return result.getParcelableExtra(NoteActivity.NOTE_EXTRA);
    }
}
