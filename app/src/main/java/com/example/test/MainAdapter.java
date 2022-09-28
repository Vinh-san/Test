package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private final List<String> strings;
    private Click click;

    public MainAdapter(List<String> strings) {
        this.strings = strings;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        String item = strings.get(position);
        if (item == null) {
            return;
        }

        holder.textView.setText(item);

        holder.textView.setOnClickListener(v -> click.transfer(strings, item));
        holder.imageDelete.setOnClickListener(v -> click.event(strings, item, position));
    }

    @Override
    public int getItemCount() {
        if (strings != null) {
            return strings.size();
        }
        return 0;
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageDelete, imageDownload;

        public MainHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview_name);
            imageDelete = itemView.findViewById(R.id.imageview_delete);
            imageDownload = itemView.findViewById(R.id.imageview_download);
        }
    }
}