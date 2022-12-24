package com.example.dosagemdeinsulina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    public EditText editText1;
    public EditText editText2;
    public double resultado;
    public TextView tv_resultado;
    public TextView tv_aviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //

    }

    public void adicionar_valores_referencia(View view) {

        Intent intent = new Intent(this, AdicionarActivity.class);
        startActivity(intent);

    }

    public void calcular_resultado(View view) {

        tv_resultado = findViewById(R.id.resultado);

        editText1 = findViewById(R.id.et_hgt);
        String string_hgt = editText1.getText().toString();
        editText2 = findViewById(R.id.et_carboRefeicao);
        String string_carboRefeicao = editText2.getText().toString();

        if(string_hgt.isEmpty() || string_carboRefeicao.isEmpty()){

            Toast.makeText(this, "Favor inserir o HGT e CARBOIDRATO DA \nREFEIÇÃO conforme " +
                    "prescrição de seu médico!", Toast.LENGTH_LONG).show();

        }else if (Double.valueOf(string_hgt) <= 80){ // tive que passar string_hgt para Double para poder fazer a comparação
            Toast.makeText(this, "Não é necessário aplicar insulina, pois o HGT " +
                    "está igual ou menor que 80!", Toast.LENGTH_LONG).show();

        } else {

            try {
                Database db = new Database(this);
                SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM valores_referencia ORDER BY id DESC", null);
                cursor.moveToFirst();

                String string_hgtReferencia = cursor.getString(1); //pegar os valores referências do BD que estão em String
                String string_refeicaoReferencia = cursor.getString(2); // idem

                double hgtReferencia = Double.parseDouble(string_hgtReferencia);
                double refeicaoReferencia = Double.parseDouble(string_refeicaoReferencia);

                double hgt = Double.parseDouble(string_hgt);
                double carboRefeicao = Double.parseDouble(string_carboRefeicao);

                double dose1 = hgt / hgtReferencia;
                double dose2 = carboRefeicao / refeicaoReferencia;

                resultado = dose1 + dose2;
                DecimalFormat df = new DecimalFormat(".#"); // para usar uma casa depois da vírgula
                tv_resultado.setText(df.format(resultado) + " unidade(s)");

            }catch (Exception e){

                e.printStackTrace();

                Toast.makeText(this, "Não foram cadastrados valores \nde referência! Favor inseri-los."
                        , Toast.LENGTH_LONG).show();

            }

        }

    }

}