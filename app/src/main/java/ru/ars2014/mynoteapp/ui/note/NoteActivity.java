package ru.ars2014.mynoteapp.ui.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import ru.ars2014.mynoteapp.R;
import ru.ars2014.mynoteapp.data.model.Note;
import ru.ars2014.mynoteapp.databinding.ActivityNoteBinding;

public class NoteActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    public static final String NOTE_EXTRA = "NOTE";

    private NoteActivityViewModel viewModel;
    private ActivityNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setOnMenuItemClickListener(this::onMenuItemClick);

        Note note = getIntent().getParcelableExtra(NOTE_EXTRA);
        if (note == null) {
            binding.toolbar.setTitle(R.string.create_note_title);
            note = new Note("", "");
        } else {
            binding.toolbar.setTitle(R.string.edit_note_title);
        }

        viewModel = new NoteActivityViewModel();
        viewModel.note.set(note);

        binding.setViewmodel(viewModel);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.create_action) {
            if (Objects.requireNonNull(viewModel.note.get()).title.isEmpty()) {
                Toast.makeText(this, R.string.empty_title_error, Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = new Intent();
            intent.putExtra(NOTE_EXTRA, viewModel.note.get());
            setResult(RESULT_OK, intent);
            finish();

            return true;
        } else if (id == R.id.cancel_action) {
            setResult(RESULT_CANCELED);
            finish();

            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}