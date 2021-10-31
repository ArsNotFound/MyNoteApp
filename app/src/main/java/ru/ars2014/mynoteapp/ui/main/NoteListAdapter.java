package ru.ars2014.mynoteapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.ars2014.mynoteapp.R;
import ru.ars2014.mynoteapp.data.model.Note;
import ru.ars2014.mynoteapp.databinding.NoteCardViewBinding;

class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public NoteListAdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteCardViewBinding binding = NoteCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(this);
        binding.getRoot().setOnLongClickListener(this);

        return NoteViewHolder.create(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = getItem(position);
        holder.bind(current.title, current.content);
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null)
            onClickListener.onClick(view);

    }

    @Override
    public boolean onLongClick(View view) {
        if (onLongClickListener != null)
            return onLongClickListener.onLongClick(view);
        return false;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public static class NoteDiff extends DiffUtil.ItemCallback<Note> {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.id.equals(newItem.id) && oldItem.content.equals(newItem.content) && oldItem.title.equals(newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.content.equals(newItem.content) && oldItem.title.equals(newItem.title);
        }
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView content;

        private NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            content = itemView.findViewById(R.id.card_content);
        }

        static NoteViewHolder create(View view) {
            return new NoteViewHolder(view);
        }

        public void bind(String title, String content) {
            this.title.setText(title);
            if (content.isEmpty()) {
                this.content.setVisibility(View.GONE);
            } else {
                this.content.setVisibility(View.VISIBLE);
                this.content.setText(content);
            }
        }
    }
}
