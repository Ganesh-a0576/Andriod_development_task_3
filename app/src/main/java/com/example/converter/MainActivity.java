package com.example.converter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnit;
    private Spinner toUnit;
    private Button convertButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Corrected line

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        convertButton = findViewById(R.id.convertButton);
        resultView = findViewById(R.id.resultView);

        // Set up the spinners with the array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });

        // Set dynamic text size based on user preferences
        float userPreferredTextSize = getResources().getConfiguration().fontScale * 18; // Example scaling
        resultView.setTextSize(userPreferredTextSize);
    }

    private void convertUnits() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input);
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();

        double result = performConversion(value, from, to);
        resultView.setText(String.format("%.2f %s", result, to));
    }

    private double performConversion(double value, String from, String to) {
        if (from.equals(to)) {
            return value;
        }

        // Conversion logic
        switch (from) {
            case "cm":
                if (to.equals("m")) {
                    return value / 100;
                }
                break;
            case "m":
                if (to.equals("cm")) {
                    return value * 100;
                }
                break;
            case "g":
                if (to.equals("kg")) {
                    return value / 1000;
                }
                break;
            case "kg":
                if (to.equals("g")) {
                    return value * 1000;
                }
                break;
        }

        // If conversion not possible, return the original value
        return value;
    }
}
