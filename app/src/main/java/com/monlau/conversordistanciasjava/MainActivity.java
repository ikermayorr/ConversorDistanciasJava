package com.monlau.conversordistanciasjava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ImageView categoryImage;
    private Button btnContinue;

    private final String[] categories = {
            "Distance",
            "Temperature",
            "Volume",
            "Mass and Weight",
            "Time",
            "Digital Information",
            "Currency",
            "Area",
            "Speed",
            "Pressure"
    };

    private final Map<String, Integer> images = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        categoryImage = findViewById(R.id.categoryImage);
        btnContinue = findViewById(R.id.btnContinue);

        // Relacionar cada categoría con su imagen
        images.put("Distance", R.drawable.distance);
        images.put("Temperature", R.drawable.temperature);
        images.put("Volume", R.drawable.volume);
        images.put("Mass and Weight", R.drawable.mass);
        images.put("Time", R.drawable.time);
        images.put("Digital Information", R.drawable.digital);
        images.put("Currency", R.drawable.currency);
        images.put("Area", R.drawable.area);
        images.put("Speed", R.drawable.speed);
        images.put("Pressure", R.drawable.pressure);

        // Crear el adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(adapter);

        // Cambiar la imagen cuando seleccionamos una categoría
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedCategory = categories[position];

                        int imageResource = images.get(selectedCategory);

                        Bitmap bitmap = BitmapFactory.decodeResource(
                                getResources(),
                                imageResource
                        );

                        categoryImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        categoryImage.setImageDrawable(null);
                    }
                }
        );

        // Abrir la segunda actividad
        btnContinue.setOnClickListener(v -> {

            String selectedCategory =
                    categorySpinner.getSelectedItem().toString();

            Intent intent = new Intent(
                    MainActivity.this,
                    ConversionActivity.class
            );

            intent.putExtra("CATEGORY", selectedCategory);

            startActivity(intent);
        });
    }
}