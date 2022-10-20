package com.example.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id, title, year, author, pages, read_pages, summary;
    ProgressBar progressBar;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList year,
                  ArrayList author, ArrayList pages, ArrayList read_pages, ArrayList summary) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.pages = pages;
        this.read_pages = read_pages;
        this.summary = summary;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.id.setText(String.valueOf(id.get(position)));
        holder.title.setText(String.valueOf(title.get(position)));
        holder.year.setText(String.valueOf(year.get(position)));
        holder.author.setText(String.valueOf(author.get(position)));

        holder.read_pages.setText(String.valueOf(read_pages.get(position)));
        holder.pages.setText(String.valueOf(pages.get(position)));

        Log.d(String.valueOf(read_pages.get(position)), "read pages");
        Log.d(String.valueOf(pages.get(position)), "total pages");

        progressBar.setProgress(Integer.parseInt(String.valueOf(read_pages.get(position))));
        progressBar.setMax(Integer.parseInt(String.valueOf(pages.get(position))));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("year", String.valueOf(year.get(position)));
                intent.putExtra("author", String.valueOf(author.get(position)));
                intent.putExtra("pages", String.valueOf(pages.get(position)));
                intent.putExtra("read_pages", String.valueOf(read_pages.get(position)));
                intent.putExtra("summary", String.valueOf(summary.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView id, title, year, author, pages, read_pages, summary;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_txt);
            title = itemView.findViewById(R.id.title_txt);
            year = itemView.findViewById(R.id.year_txt);
            author = itemView.findViewById(R.id.author_txt);
            pages = itemView.findViewById(R.id.pages_txt);
            read_pages = itemView.findViewById(R.id.read_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}
