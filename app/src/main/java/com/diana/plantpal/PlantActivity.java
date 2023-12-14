package com.diana.plantpal;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PlantActivity extends AppCompatActivity {
    TextView plantname, plantdescription;
    ImageView planticon;
    String Name, Descriptioin; int Icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        plantname=findViewById(R.id.plantNameView);
        plantdescription=findViewById(R.id.plantDescriptionView);
        planticon=findViewById(R.id.plantIconView);
        getaAndSetIntentData();
        ActionBar ab=getSupportActionBar();
        if (ab!=null){
            ab.setTitle(Name);
        }
    }
    void getaAndSetIntentData() {
        if (getIntent().hasExtra("namePlant") && getIntent().hasExtra("desriptionPlant") && getIntent().hasExtra("imagePlant")) {
            //Getting Data from Intent
            Name = getIntent().getStringExtra("namePlant");
            Descriptioin = getIntent().getStringExtra("desriptionPlant");
            Icon = getIntent().getIntExtra("imagePlant", R.drawable.sykkylent);
            plantname.setText(Name);
            plantdescription.setText(Descriptioin);
            planticon.setImageResource(Icon);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}
