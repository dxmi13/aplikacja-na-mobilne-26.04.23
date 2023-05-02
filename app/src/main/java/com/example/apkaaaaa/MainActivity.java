package com.example.apkaaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //createDatabase();
        //insertData("K", "Anna", "Nowak", "01-01-1990", "123456789", true, false, true, true);
        getData();

        Button zapiszButton = findViewById(R.id.zapis);
        zapiszButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wpisane_dane();
            }
        });

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

            int kolumnaID = cursor.getColumnIndex("id");
            int kolumnaPlci = cursor.getColumnIndex("plec");
            int imieKolumna = cursor.getColumnIndex("imie");
            int nazwiskoKolumna = cursor.getColumnIndex("nazwisko");
            int dataKolumna = cursor.getColumnIndex("data_urodzenia");
            int telefonKolumna = cursor.getColumnIndex("telefon");
            int filmyKolumna = cursor.getColumnIndex("filmy");
            int literaturaKolumna = cursor.getColumnIndex("literatura");
            int muzykaKolumna = cursor.getColumnIndex("muzyka");
            int sportKolumna = cursor.getColumnIndex("sport");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(kolumnaID);
                String plec = cursor.getString(kolumnaPlci);
                String imie = cursor.getString(imieKolumna);
                String nazwisko = cursor.getString(nazwiskoKolumna);
                String data = cursor.getString(dataKolumna);
                String telefon = cursor.getString(telefonKolumna);
                boolean filmy = cursor.getInt(filmyKolumna) == 1;
                boolean literatura = cursor.getInt(literaturaKolumna) == 1;
                boolean muzyka = cursor.getInt(muzykaKolumna) == 1;
                boolean sport = cursor.getInt(sportKolumna) == 1;

                Log.d("Database", "ID: " + id + ", Płeć: " + plec + ", Imie: " + imie + ", Nazwisko: " + nazwisko + ", Data urodzenia: " + data + ", Telefon: " + telefon + ", Filmy: " + filmy + ", Literatura: " + literatura + ", Muzyka: " + muzyka + ", Sport: " + sport);
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

    private void zapisz_do_bazy(String deklaracja_plci, String imie, String nazwisko, String telefon, String dataUrodzenia, boolean lubiFilmy, boolean lubiLiterature, boolean lubiMuzyke, boolean lubiSport){
        String toastMessage = deklaracja_plci + " " + imie + " " + nazwisko + " " + telefon + " " + dataUrodzenia + " " + lubiFilmy + " " + lubiLiterature + " "+ lubiMuzyke + " " + lubiSport;
        Log.d("aaaaa", toastMessage);

        try{
            insertData(deklaracja_plci, imie, nazwisko, dataUrodzenia, telefon, lubiFilmy, lubiLiterature, lubiMuzyke, lubiSport);
            Toast.makeText(getApplicationContext(), "Dane zostały zapisane", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void wpisane_dane(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        String deklaracja_plci = "";

        if (selectedId == R.id.mezczyzna) {
            deklaracja_plci = "M";
        } else if (selectedId == R.id.kobieta) {
            deklaracja_plci = "K";
        }

        EditText imieET = (EditText) findViewById(R.id.imie);
        EditText nazwiskoET = (EditText) findViewById(R.id.nazwisko);
        EditText dataUrodzeniaET = findViewById(R.id.data_urodzenia);
        EditText telefonET = findViewById(R.id.telefon);

        String imie = imieET.getText().toString();
        String nazwisko = nazwiskoET.getText().toString();
        String dataUrodzenia = dataUrodzeniaET.getText().toString();
        String telefon = telefonET.getText().toString();

        CheckBox filmCheckBox = findViewById(R.id.filmy);
        CheckBox literaturaCheckBox = findViewById(R.id.literatura);
        CheckBox muzykaCheckBox = findViewById(R.id.muzyka);
        CheckBox sportCheckBox = findViewById(R.id.sport);

        boolean lubiFilmy = filmCheckBox.isChecked();
        boolean lubiLiterature = literaturaCheckBox.isChecked();
        boolean lubiMuzyke = muzykaCheckBox.isChecked();
        boolean lubiSport = sportCheckBox.isChecked();
        zapisz_do_bazy(deklaracja_plci, imie, nazwisko, telefon, dataUrodzenia, lubiFilmy, lubiLiterature, lubiMuzyke, lubiSport);
    }





}