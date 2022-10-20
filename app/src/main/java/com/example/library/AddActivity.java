package com.example.library;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText book_title, year, author, pages, read_pages, summary;
    Button add_button;
    int reading_stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        book_title = findViewById(R.id.book_title_update);
        year = findViewById(R.id.year_update);
        author = findViewById(R.id.author_update);
        pages = findViewById(R.id.pages_update);
        read_pages = findViewById(R.id.read_pages_update);
        summary = findViewById(R.id.summary_update);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (book_title.getText().toString().length() == 0){
                    book_title.setError("Judul buku harus diisi");
                } else if (year.getText().toString().length() == 0) {
                    year.setError("Tahun terbit buku harus diisi");
                } else if (author.getText().toString().length() == 0){
                    author.setError("Pengarang buku harus diisi");
                } else if (pages.getText().toString().length() == 0){
                    pages.setError("Jumlah halaman harus diisi");
                } else if (read_pages.getText().toString().length() == 0){
                    read_pages.setError("Jumlah halaman yang dibaca harus diisi");
                } else if (summary.getText().toString().length() == 0){
                    summary.setError("Summary atau review harus diisi");
                } else if (Integer.parseInt(read_pages.getText().toString()) > Integer.parseInt(pages.getText().toString())){
                    read_pages.setError("Halaman yang telah dibaca tidak boleh melebihi halaman total");
                } else {
                    DBHelper myDB = new DBHelper(AddActivity.this);
                    myDB.addBook(
                            book_title.getText().toString().trim(),
                            Integer.parseInt(year.getText().toString().trim()),
                            author.getText().toString().trim(),
                            Integer.parseInt(pages.getText().toString().trim()),
                            Integer.parseInt(read_pages.getText().toString().trim()),
                            summary.getText().toString().trim(),
                            Integer.parseInt(String.valueOf(reading_stat))
                    );
                }

            }
        });
    }
}