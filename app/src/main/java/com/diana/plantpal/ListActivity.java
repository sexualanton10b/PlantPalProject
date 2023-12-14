package com.diana.plantpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    String plantNames []={"Суккулент", "Пальма", "Гибискус", "Фикус", "Бугенвилия", "Алоэ", "Бархатцы", "Калатея", "Пеперомия", "Драцены"};
    int plantImages []={R.drawable.sykkylent, R.drawable.palma, R.drawable.gibiskus, R.drawable.ficus, R.drawable.bugenvilia, R.drawable.aloe, R.drawable.barhat, R.drawable.kalateya, R.drawable.peperomia, R.drawable.dracen};
    String plantDescriptions []={"Суккуленты - это такие растения, которые очень хорошо переносят яркое солнце и высокие температуры. Они не требуют постоянного полива, главное следить за их почвой.",
    "Многие виды пальм прекрасно растут в ярком солнце и тепле. Они могут поддерживать температуру выше 25 градусов, но требуют умеренного полива, не обязательно ежедневного. Важно избегать пересыхания земли и поддерживать уровень влажности.",
    "Гибискус любит яркий солнечный свет, однако, в жаркую погоду может потребоваться защита от прямых лучей. Температуры выше 25 градусов для гибискуса комфортны, но регулярный, но не слишком частый полив помогает сохранить здоровье растения.",
    "Фикус предпочитает яркий свет, но может переносить полутень. Температуры выше 25 градусов не являются проблемой, но регулярный уход и умеренный полив помогут растению процветать.",
    "Это растение обожает яркий солнечный свет и тепло. Высокие температуры в районе 25 градусов и выше обычно благоприятны. Растение требует регулярного полива, но избегайте переувлажнения.", "Алоэ прекрасно справляется с ярким солнцем и высокими температурами. Однако, оно предпочитает периодический, хороший полив, но не каждый день. Земля должна хорошо высохнуть между поливами, чтобы избежать гниения корней.",
    "Бархатцы предпочитают яркий, но рассеянный свет, их следует защищать от прямых солнечных лучей. Они комфортно чувствуют себя при температуре выше 18 градусов. Бархатцы требуют регулярного полива, но важно избегать переувлажнения грунта.",
    "Калатеи ценят полутень и умеренное освещение. Они предпочитают теплую среду, с температурой выше 18 градусов. Калатеи требуют регулярного полива, но важно не допускать пересыхания земли, а также обеспечивать высокую влажность воздуха.",
    "Пеперомия хорошо растет на ярком свете, но не переносит прямых солнечных лучей. Оптимальная температура для нее составляет выше 18 градусов. Регулярный полив и поддержание уровня влажности помогут растению процветать.",
    "Драцены могут выживать при разных условиях освещения, но предпочитают яркий, но рассеянный свет. Они комфортно чувствуют себя при температуре выше 18 градусов. Драцены требуют умеренного полива и предпочитают допускать небольшое пересыхание верхнего слоя грунта между поливами."};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView=(ListView) findViewById(R.id.customListView);
        CustomBaseAdapter customBaseAdapter=new CustomBaseAdapter(getApplicationContext(), plantNames, plantImages);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, PlantActivity.class);
                intent.putExtra("namePlant", plantNames[position]);
                intent.putExtra("desriptionPlant", plantDescriptions[position]);
                intent.putExtra("imagePlant", plantImages[position]);
                startActivity(intent);
            }
        });
    }

}
