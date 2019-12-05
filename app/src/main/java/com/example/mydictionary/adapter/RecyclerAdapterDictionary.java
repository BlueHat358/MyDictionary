package com.example.mydictionary.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydictionary.DetailActivity;
import com.example.mydictionary.R;
import com.example.mydictionary.db.Model;

import java.util.ArrayList;

public class RecyclerAdapterDictionary extends RecyclerView.Adapter<RecyclerAdapterDictionary.viewHolder> {

    private ArrayList<Model> list = new ArrayList<>();

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.model, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvWord, tvTranslate;

        public viewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvTranslate = itemView.findViewById(R.id.tv_translate);
        }

        public void bind(final Model model) {
            tvWord.setText(model.getWord());
            tvTranslate.setText(model.getTranslate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.ITEM_WORD, model.getWord());
                    intent.putExtra(DetailActivity.ITEM_TRANSLATE, model.getTranslate());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public void replaceAll(ArrayList<Model> items) {
        list = items;
        notifyDataSetChanged();
    }
}
