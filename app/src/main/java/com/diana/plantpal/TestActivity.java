package com.diana.plantpal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    String resultPlant[]={"Лаванда, базилик", "Пальмы, гибискус", "Фикусы, бугенвилии", "Алоэ, бархатцы", "Калатея, пеперомия",
    "Драцены, аспарагусы", "Кампсис, замиокулькасы", "Фиалки, сансевиерии", "Папоротники, сенполии", "Бегонии, каланхоэ", "Хлорофитум, калатея", "Диффенбахия, пеперомия",
    "Фикусы, монстеры", "Аспидистра, пеперомия", "Шеффлеры, кампсисы", "Спатифиллумы, аспарагусы", "Вриезии, азалии", "Циперусы, орхидеи",  "Бегонии, папоротники", "Фиалки, суккуленты", "Кампсисы, аспарагусы", "Замиокулькасы, калатеи",
    "Сансевиерии, диффенбахии", "Хлорофитумы, бегонии", "Спатифиллумы, аглаонемы", "Каланхоэ, Герань", "Драцены, пеперомии", "Монстеры, азалии", "Калатеи, бархатцы", "Фикусы, монстеры", "Суккуленты, орхидеи", "Пальмы, гибискус",  "Диффенбахии, папоротники", "Кампсисы, сенполии",
    "Бегонии, каланхоэ", "Хлорофитумы, замиокулькасы", "Сансевиерии, аглаонемы", "Спатифиллумы, аспидистра", "Каланхоэ, пеперомии", "Фикусы, азалии", "Пальмы, гибискус","Кампсисы, монстеры",  "Сенполии, орхидеи", "Замиокулькасы, бархатцы", "Бегонии, папоротники",
    "Хлорофитумы, калатеи", "Диффенбахии, сансевиерии", "Калатеи, суккуленты"};
    Button resultbutton;
    RadioGroup optionsRadioGroup1, optionsRadioGroup2,optionsRadioGroup3;
    RadioButton radioButton00, radioButton01, radioButton02, radioButton03, radioButton10, radioButton11, radioButton12, radioButton20, radioButton21, radioButton22, radioButton23;
    int answers[]={-1, -1, -1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        resultbutton = findViewById(R.id.ResultButton);
        radioButton00=findViewById(R.id.var1Button);
        radioButton01=findViewById(R.id.var2Button);
        radioButton02=findViewById(R.id.var3Button);
        radioButton03=findViewById(R.id.var4Button);
        radioButton10=findViewById(R.id.var1Button2);
        radioButton11=findViewById(R.id.var2Button2);
        radioButton12=findViewById(R.id.var3Button2);
        radioButton20=findViewById(R.id.var1Button3);
        radioButton21=findViewById(R.id.var2Button3);
        radioButton22=findViewById(R.id.var3Button3);
        radioButton23=findViewById(R.id.var4Button3);
        resultbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkResult();
                // Проверка наличия ответов на все вопросы
                if (answers[0] == -1 || answers[1] == -1 || answers[2] == -1) {
                    // Отобразить сообщение пользователю
                    Toast.makeText(TestActivity.this, "Ответьте на все вопросы", Toast.LENGTH_SHORT).show();
                } else {
                    // Все ответы предоставлены, обрабатываем результат
                    processResults();
                }
            }
        });
    }
    private void checkResult(){
        if (radioButton00.isChecked()) answers[0]=0;
        if (radioButton01.isChecked()) answers[0]=1;
        if (radioButton02.isChecked()) answers[0]=2;
        if (radioButton03.isChecked()) answers[0]=3;
        if (radioButton10.isChecked()) answers[1]=0;
        if (radioButton11.isChecked()) answers[1]=1;
        if (radioButton12.isChecked()) answers[1]=2;
        if (radioButton20.isChecked()) answers[2]=0;
        if (radioButton21.isChecked()) answers[2]=1;
        if (radioButton22.isChecked()) answers[2]=2;
        if (radioButton23.isChecked()) answers[2]=3;
    }
    private void processResults() {
        int result=0;
        result=answers[0]*12+answers[1]*4+answers[2];
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Результат теста");
        builder.setMessage(resultPlant[result]);
        builder.setPositiveButton("Прочитано", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //Обновляет активити
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}