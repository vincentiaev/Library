package com.example.library;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, year_input, author_input, pages_input, read_pages_input, summary_input;
    Button update_btn, delete_btn;

    String id, title, year, author, pages, read_pages, summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.book_title_update);
        year_input = findViewById(R.id.year_update);
        author_input = findViewById(R.id.author_update);
        pages_input = findViewById(R.id.pages_update);
        read_pages_input = findViewById(R.id.read_pages_update);
        summary_input = findViewById(R.id.summary_update);
        update_btn = findViewById(R.id.update_button);
        delete_btn = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_input.getText().toString().length() == 0){
                    title_input.setError("Judul buku harus diisi");
                } else if (year_input.getText().toString().length() == 0) {
                    year_input.setError("Tahun terbit buku harus diisi");
                } else if (author_input.getText().toString().length() == 0){
                    author_input.setError("Pengarang buku harus diisi");
                } else if (pages_input.getText().toString().length() == 0){
                    pages_input.setError("Jumlah halaman harus diisi");
                } else if (read_pages_input.getText().toString().length() == 0){
                    read_pages_input.setError("Jumlah halaman yang dibaca harus diisi");
                } else if (summary_input.getText().toString().length() == 0){
                    summary_input.setError("Summary atau review harus diisi");
                } else if (Integer.parseInt(read_pages_input.getText().toString()) > Integer.parseInt(pages_input.getText().toString())){
                    read_pages_input.setError("Halaman yang telah dibaca tidak boleh melebihi halaman total");
                } else {
                    DBHelper myDB = new DBHelper(UpdateActivity.this);
                    title = title_input.getText().toString().trim();
                    year = year_input.getText().toString().trim();
                    author = author_input.getText().toString().trim();
                    pages = pages_input.getText().toString().trim();
                    read_pages = read_pages_input.getText().toString().trim();
                    summary = summary_input.getText().toString().trim();
                    myDB.updateData(id, title, year, author, pages, read_pages, summary);
                }

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData (){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("year")
                && getIntent().hasExtra("author") && getIntent().hasExtra("pages")
                && getIntent().hasExtra("read_pages") && getIntent().hasExtra("summary")){
            //gettin data dr intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            year = getIntent().getStringExtra("year");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");
            read_pages = getIntent().getStringExtra("read_pages");
            summary = getIntent().getStringExtra("summary");

            //set data
            title_input.setText(title);
            year_input.setText(year);
            author_input.setText(author);
            pages_input.setText(pages);
            read_pages_input.setText(read_pages);
            summary_input.setText(summary);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}