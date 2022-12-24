package com.example.dosagemdeinsulina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdicionarActivity extends AppCompatActivity {

    public EditText et_referencia_hgt;
    public EditText et_referencia_carboRefeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        et_referencia_hgt = findViewById(R.id.et_referencia_hgt);
        et_referencia_carboRefeicao = findViewById(R.id.et_referencia_carboRefeicao);

        pegar_ultima_ref();

    }

    public void pegar_ultima_ref(){
        try {

            Database database = new Database(this);
            SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM valores_referencia ORDER BY id DESC", null);
            cursor.moveToFirst();

            String hgtRef = cursor.getString(1);
            String carboRef = cursor.getString(2);

            et_referencia_hgt.setText(hgtRef);
            et_referencia_carboRefeicao.setText(carboRef);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void salvar(View view){

        String ref_hgt = et_referencia_hgt.getText().toString();
        String ref_carboRefeicao = et_referencia_carboRefeicao.getText().toString();

        if(ref_hgt.isEmpty() || ref_carboRefeicao.isEmpty()){

            Toast.makeText(this, "Não pode haver campos em branco!", Toast.LENGTH_SHORT).show();

        }else{

            try {

                Database db = new Database(this);
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("hgt", ref_hgt);
                values.put("carbo_refeicao", ref_carboRefeicao);

                sqLiteDatabase.insert("valores_referencia", null, values);
                sqLiteDatabase.close();
                Toast.makeText(this, "Valores criados com sucesso!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }
}