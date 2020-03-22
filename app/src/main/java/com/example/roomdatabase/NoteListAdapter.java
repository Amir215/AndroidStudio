package com.example.roomdatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.roomdatabase.MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClickListener(Note note);
    }

    private Context mContext;
    private final LayoutInflater layoutInflater;
    private List<Note> notesList;
    private OnDeleteClickListener onDeleteClickListener;

    public NoteListAdapter(Context context, OnDeleteClickListener listener) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        if (notesList != null) {
            Note note = notesList.get(position);
            holder.setData(note.getNote(), position);
            holder.setListeners();
        } else {
            holder.noteItemView.setText(R.string.no_note);
        }
    }

    @Override
    public int getItemCount() {

        if (notesList != null) {
            return notesList.size();
        } else return 0;
    }

    public void setNotes(List<Note> notes) {
        notesList = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView;
        private int mPosition;
        private ImageView imgDelete, imgEdit;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.tvNote);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String note, int position) {

            noteItemView.setText(note);
            mPosition = position;
        }

        public void setListeners() {

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EditNoteActivity.class);
                    intent.putExtra("note_id", notesList.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onDeleteClickListener(notesList.get(mPosition));
                    }
                }
            });
        }
    }
}
