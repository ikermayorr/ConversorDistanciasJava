package com.monlau.conversordistanciasjava;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ConversionActivity extends AppCompatActivity {

    private TextView txtCategory;
    private TextView txtResult;
    private EditText edtValue;

    private Spinner spinnerFrom;
    private Spinner spinnerTo;

    private Button btnConvert;
    private Button btnBack;

    private final Map<String, Unit> units = new LinkedHashMap<>();

    private final Map<String, CurrencyUnit> currencies =
            new LinkedHashMap<>();

    // Clase para unidades normales
    private static class Unit {

        double factor;
        double offset;

        Unit(double factor, double offset) {
            this.factor = factor;
            this.offset = offset;
        }
    }

    // Clase específica para monedas
    private static class CurrencyUnit {

        String code;
        double rateToEUR;

        CurrencyUnit(String code, double rateToEUR) {
            this.code = code;
            this.rateToEUR = rateToEUR;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        txtCategory = findViewById(R.id.txtCategory);
        txtResult = findViewById(R.id.txtResult);
        edtValue = findViewById(R.id.edtValue);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        btnConvert = findViewById(R.id.btnConvert);
        btnBack = findViewById(R.id.btnBack);

        // Recibir la categoría desde MainActivity
        String category = getIntent().getStringExtra("CATEGORY");

        if (category == null) {
            category = "Distance";
        }

        final String selectedCategory = category;

        txtCategory.setText(selectedCategory + " converter");

        // Cargar las unidades correspondientes
        if (selectedCategory.equals("Currency")) {
            loadCurrencies();
            setupSpinners(currencies.keySet().toArray(new String[0]));
        } else {
            loadUnits(selectedCategory);
            setupSpinners(units.keySet().toArray(new String[0]));
        }

        btnConvert.setOnClickListener(v -> convert(selectedCategory));

        btnBack.setOnClickListener(v -> finish());
    }

    // Preparar los dos Spinner
    private void setupSpinners(String[] names) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                names
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        if (names.length > 1) {
            spinnerTo.setSelection(1);
        }
    }

    // Cargar las unidades de cada categoría
    private void loadUnits(String category) {

        units.clear();

        switch (category) {

            case "Distance":
                units.put("Meter (m)", new Unit(1, 0));
                units.put("Kilometer (km)", new Unit(1000, 0));
                units.put("Centimeter (cm)", new Unit(0.01, 0));
                units.put("Millimeter (mm)", new Unit(0.001, 0));
                units.put("Mile (mi)", new Unit(1609.344, 0));
                units.put("Foot (ft)", new Unit(0.3048, 0));
                break;

            case "Temperature":
                units.put("Celsius (°C)", new Unit(1, 0));
                units.put("Fahrenheit (°F)",
                        new Unit(5.0 / 9.0, -32.0 * 5.0 / 9.0));
                units.put("Kelvin (K)", new Unit(1, -273.15));
                break;

            case "Volume":
                units.put("Liter (L)", new Unit(1, 0));
                units.put("Milliliter (mL)", new Unit(0.001, 0));
                units.put("Cubic meter (m³)", new Unit(1000, 0));
                units.put("US Gallon (gal)", new Unit(3.785411784, 0));
                units.put("Cup (cup)", new Unit(0.2365882365, 0));
                break;

            case "Mass and Weight":
                units.put("Kilogram (kg)", new Unit(1, 0));
                units.put("Gram (g)", new Unit(0.001, 0));
                units.put("Milligram (mg)", new Unit(0.000001, 0));
                units.put("Tonne (t)", new Unit(1000, 0));
                units.put("Pound (lb)", new Unit(0.45359237, 0));
                units.put("Ounce (oz)", new Unit(0.028349523125, 0));
                break;

            case "Time":
                units.put("Second (s)", new Unit(1, 0));
                units.put("Minute (min)", new Unit(60, 0));
                units.put("Hour (h)", new Unit(3600, 0));
                units.put("Day (d)", new Unit(86400, 0));
                break;

            case "Digital Information":
                units.put("Bit (b)", new Unit(0.125, 0));
                units.put("Byte (B)", new Unit(1, 0));
                units.put("Kilobyte (KB)", new Unit(1024, 0));
                units.put("Megabyte (MB)", new Unit(1048576, 0));
                units.put("Gigabyte (GB)", new Unit(1073741824, 0));
                units.put("Terabyte (TB)", new Unit(1099511627776.0, 0));
                break;

            case "Area":
                units.put("Square meter (m²)", new Unit(1, 0));
                units.put("Square kilometer (km²)", new Unit(1000000, 0));
                units.put("Square centimeter (cm²)", new Unit(0.0001, 0));
                units.put("Hectare (ha)", new Unit(10000, 0));
                units.put("Acre (ac)", new Unit(4046.8564224, 0));
                break;

            case "Speed":
                units.put("Meter per second (m/s)", new Unit(1, 0));
                units.put("Kilometer per hour (km/h)", new Unit(1.0 / 3.6, 0));
                units.put("Mile per hour (mph)", new Unit(0.44704, 0));
                units.put("Foot per second (ft/s)", new Unit(0.3048, 0));
                break;

            case "Pressure":
                units.put("Pascal (Pa)", new Unit(1, 0));
                units.put("Kilopascal (kPa)", new Unit(1000, 0));
                units.put("Bar", new Unit(100000, 0));
                units.put("Atmosphere (atm)", new Unit(101325, 0));
                units.put("PSI (psi)", new Unit(6894.757293, 0));
                break;
        }
    }

    // Cargar monedas con EUR como referencia
    private void loadCurrencies() {

        currencies.clear();

        // Tasas de ejemplo para la práctica.
        // Representan cuántos euros equivale una unidad de moneda.
        currencies.put("Euro (EUR)", new CurrencyUnit("EUR", 1.0));
        currencies.put("US Dollar (USD)", new CurrencyUnit("USD", 0.92));
        currencies.put("British Pound (GBP)", new CurrencyUnit("GBP", 1.17));
        currencies.put("Japanese Yen (JPY)", new CurrencyUnit("JPY", 0.0062));
        currencies.put("Swiss Franc (CHF)", new CurrencyUnit("CHF", 1.05));
    }

    // Realizar la conversión
    private void convert(String category) {

        String input = edtValue.getText().toString().trim();

        if (input.isEmpty()) {
            showMessage("Please enter a value");
            return;
        }

        double value;

        try {
            value = Double.parseDouble(input.replace(",", "."));

            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            showMessage("Please enter a valid number");
            return;
        }

        String from = spinnerFrom.getSelectedItem().toString();
        String to = spinnerTo.getSelectedItem().toString();

        double result;

        if (category.equals("Currency")) {

            CurrencyUnit source = currencies.get(from);
            CurrencyUnit target = currencies.get(to);

            // Convertir primero a euros y después a la moneda destino
            double valueInEUR = value * source.rateToEUR;

            result = valueInEUR / target.rateToEUR;

        } else {

            Unit source = units.get(from);
            Unit target = units.get(to);

            // Convertir a la unidad de referencia
            double baseValue = value * source.factor + source.offset;

            // Convertir de la referencia a la unidad destino
            result = (baseValue - target.offset) / target.factor;
        }

        if (Double.isNaN(result) || Double.isInfinite(result)) {
            showMessage("This conversion cannot be performed");
            return;
        }

        String formattedResult = String.format(
                Locale.getDefault(),
                "%.6f",
                result
        );

        txtResult.setText(
                value + " " + from + " = " + formattedResult + " " + to
        );

        showMessage("Conversion completed");
    }

    // Mostrar mensajes mediante Snackbar
    private void showMessage(String message) {

        Snackbar.make(
                findViewById(R.id.main),
                message,
                Snackbar.LENGTH_SHORT
        ).show();
    }
}