package com.monlau.conversordistanciasjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Obtener el Spinner del XML
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        // 2. Crear la lista de categorías
        String[] categories = {
                "Distance",
                "Temperature",
                "Volume",
                "Mass and Weight",
                "Time",
                "Digital Information",
                "Area",
                "Speed",
                "Pressure"
        };

        // 3. Crear el adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        // 4. Configurar el desplegable
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        // 5. Conectar el adaptador al Spinner
        categorySpinner.setAdapter(adapter);
        // Obtener el ImageView
        ImageView categoryImage = findViewById(R.id.categoryImage);

// Crear un mapa para relacionar categorías e imágenes
        Map<String, Integer> images = new HashMap<>();

        images.put("Distance", R.drawable.distance);
        images.put("Temperature", R.drawable.temperature);
        images.put("Volume", R.drawable.volume);
        images.put("Mass and Weight", R.drawable.mass);
        images.put("Time", R.drawable.time);
        images.put("Digital Information", R.drawable.digital);
        images.put("Area", R.drawable.area);
        images.put("Speed", R.drawable.speed);
        images.put("Pressure", R.drawable.pressure);

// Detectar cuándo cambia la categoría
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        String selectedCategory =
                                parent.getItemAtPosition(position).toString();

                        // Obtener el recurso de imagen
                        int imageResource = images.get(selectedCategory);

                        // Cargar la imagen como Bitmap
                        Bitmap bitmap = BitmapFactory.decodeResource(
                                getResources(),
                                imageResource
                        );

                        // Mostrar la imagen
                        categoryImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        categoryImage.setImageDrawable(null);
                    }
                }
        );
    }
}
