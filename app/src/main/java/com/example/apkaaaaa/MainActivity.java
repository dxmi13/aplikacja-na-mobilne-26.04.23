package com.example.apkaaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    public void createDatabase() {
        SQLiteDatabase myDatabase = null;
        try {
            myDatabase = this.openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS myTable (id INTEGER PRIMARY KEY AUTOINCREMENT, plec VARCHAR, imie VARCHAR, nazwisko VARCHAR, data_urodzenia VARCHAR, telefon VARCHAR, filmy BOOLEAN, literatura BOOLEAN, muzyka BOOLEAN, sport BOOLEAN)");
        } finally {
            if (myDatabase != null) {
                Log.d("istnieje","Baza już istnieje.");
                myDatabase.close();
            }
            else{
                insertData("K", "Anna", "Nowak", "01-01-1990", "123456789", true, false, true, true);
            }
        }
    }

    public void insertData(String plec, String imie, String nazwisko, String data_urodzenia, String telefon, boolean filmy, boolean literatura, boolean muzyka, boolean sport) {
        SQLiteDatabase myDatabase = null;
        try {
            myDatabase = this.openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);
            String insertQuery = "INSERT INTO myTable (plec, imie, nazwisko, data_urodzenia, telefon, filmy, literatura, muzyka, sport) " +
                    "VALUES ('" + plec + "', '" + imie + "', '" + nazwisko + "', '" + data_urodzenia + "', '" + telefon + "', " + (filmy ? 1 : 0) + ", " + (literatura ? 1 : 0) + ", " + (muzyka ? 1 : 0) + ", " + (sport ? 1 : 0) + ")";
            myDatabase.execSQL(insertQuery);
        } finally {
            if (myDatabase != null) {
                myDatabase.close();
            }
        }
    }

    public void getData() {
        SQLiteDatabase myDatabase = null;
        Cursor cursor = null;
        try {
            myDatabase = this.openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);
            cursor = myDatabase.rawQuery("SELECT * FROM myTable", null);

            int idColumn = cursor.getColumnIndex("id");
            int genderColumn = cursor.getColumnIndex("plec");
            int firstNameColumn = cursor.getColumnIndex("imie");
            int lastNameColumn = cursor.getColumnIndex("nazwisko");
            int birthDateColumn = cursor.getColumnIndex("data_urodzenia");
            int phoneNumberColumn = cursor.getColumnIndex("telefon");
            int hasWatchedMovieColumn = cursor.getColumnIndex("filmy");
            int hasReadLiteratureColumn = cursor.getColumnIndex("literatura");
            int hasListenedToMusicColumn = cursor.getColumnIndex("muzyka");
            int isInterestedInSportsColumn = cursor.getColumnIndex("sport");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumn);
                String gender = cursor.getString(genderColumn);
                String firstName = cursor.getString(firstNameColumn);
                String lastName = cursor.getString(lastNameColumn);
                String birthDate = cursor.getString(birthDateColumn);
                String phoneNumber = cursor.getString(phoneNumberColumn);
                boolean hasWatchedMovie = cursor.getInt(hasWatchedMovieColumn) == 1;
                boolean hasReadLiterature = cursor.getInt(hasReadLiteratureColumn) == 1;
                boolean hasListenedToMusic = cursor.getInt(hasListenedToMusicColumn) == 1;
                boolean isInterestedInSports = cursor.getInt(isInterestedInSportsColumn) == 1;

                Log.d("Database", "ID: " + id + ", Płeć: " + gender + ", Imie: " + firstName + ", Nazwisko: " + lastName + ", Data urodzenia: " + birthDate + ", Telefon: " + phoneNumber + ", Filmy: " + hasWatchedMovie + ", Literatura: " + hasReadLiterature + ", Muzyka: " + hasListenedToMusic + ", Sport: " + isInterestedInSports);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (myDatabase != null) {
                myDatabase.close();
            }
        }
    }

    public void deleteDataById(int id) {
        SQLiteDatabase myDatabase = null;
        try {
            myDatabase = this.openOrCreateDatabase("myDatabase", MODE_PRIVATE, null);
            myDatabase.delete("myTable", "id=?", new String[]{String.valueOf(id)});
        } finally {
            if (myDatabase != null) {
                myDatabase.close();
            }
        }
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
            case R.id.aktywnosc2:
                aktywnosc2();
                return true;
            case R.id.aktywnosc3:
                aktywnosc3();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aktywnosc2(){
        Intent i = new Intent(this, Aktywnosc2.class);
        startActivity(i);
    }

    private void aktywnosc3(){
        Intent i = new Intent(this, Aktywnosc3.class);
        startActivity(i);
    }
}