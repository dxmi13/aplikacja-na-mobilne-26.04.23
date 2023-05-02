package com.example.apkaaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Aktywnosc2 extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> usersList;
    List<String> usersDataList;

    List<String> userTelephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc2);

        listView = findViewById(R.id.list_view);
        usersList = new ArrayList<>();
        usersDataList = new ArrayList<>();
        userTelephone = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM myTable", null);
        while (cursor.moveToNext()) {
            String user = cursor.getString(2) + " " + cursor.getString(3);
            String userData = "ID: " + cursor.getString(0) + ", Płeć: " + cursor.getString(1) + ", Imię: " + cursor.getString(2) + ", Nazwisko: " + cursor.getString(3) + ", Data urodzenia: " + cursor.getString(4) + ", Telefon: " + cursor.getString(5) + ", Filmy: " + cursor.getString(6) + ", Literatura: " + cursor.getString(7) + ", Muzyka: " + cursor.getString(8) + ", Sport: " + cursor.getString(9) + ",";
            userTelephone.add(cursor.getString(5));
            usersList.add(user);
            usersDataList.add(userData);
        }
        cursor.close();
        db.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userData = usersDataList.get(position);
                String telefon = userTelephone.get(position);
                String user = usersList.get(position);

                Intent intent = new Intent(Aktywnosc2.this, Aktywnosc3.class);
                intent.putExtra("user", user);
                intent.putExtra("userData", userData);
                intent.putExtra("telefon", telefon);
                startActivity(intent);
            }
        });
    }

    private SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = null;
        try {
            db = this.openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);
        } catch (SQLiteException ex) {
            Log.e("Database", "Błąd przy otwieraniu bazy danych", ex);
        }
        return db;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.aktywnosc1:
                aktywnosc1();
                return true;
            case R.id.aktywnosc3:
                aktywnosc3();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aktywnosc1(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void aktywnosc3(){
        Intent i = new Intent(this, Aktywnosc3.class);
        startActivity(i);
    }
}