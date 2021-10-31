package ru.ars2014.mynoteapp.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.ars2014.mynoteapp.R;
import ru.ars2014.mynoteapp.data.model.Note;
import ru.ars2014.mynoteapp.databinding.ActivityMainBinding;
import ru.ars2014.mynoteapp.ui.note.NoteContract;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoteListAdapter adapter;
    private MainViewModel viewModel;

    private final ActivityResultLauncher<Note> noteLauncher = registerForActivityResult(new NoteContract(),
            new ActivityResultCallback<Note>() {
                @Override
                public void onActivityResult(Note result) {
                    if (result != null) {
                        viewModel.insert(result);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        adapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        binding.noteRv.setAdapter(adapter);
        adapter.setOnClickListener(view -> {
            int itemPosition = binding.noteRv.getChildAdapterPosition(view);
            Note item = adapter.getCurrentList().get(itemPosition);

            noteLauncher.launch(item);
        });
        adapter.setOnLongClickListener(view -> {
            int itemPosition = binding.noteRv.getChildAdapterPosition(view);
            Note item = adapter.getCurrentList().get(itemPosition);

            AlertDialog dialog = askDelete(item);
            dialog.show();
            return true;
        });

        binding.fab.setOnClickListener(view -> noteLauncher.launch(null));

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getAllNotes().observe(this, adapter::submitList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResults(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getResults(s);
                return true;
            }

            private void getResults(String newText) {
                String queryText = "%" + newText + "%";
                viewModel.search(queryText).observe(MainActivity.this, notes -> {
                            if (notes == null) {
                                return;
                            }
                            adapter.submitList(notes);
                        }
                );
            }
        });

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private AlertDialog askDelete(Note note) {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(R.string.delete_message)
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton(R.string.delete_button_text, (dialog, whichButton) -> {
                    this.viewModel.delete(note);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel_button_text, (dialog, whichButton) -> dialog.dismiss())
                .create();
    }
}
