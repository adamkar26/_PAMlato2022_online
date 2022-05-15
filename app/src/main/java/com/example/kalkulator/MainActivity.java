package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // okno na wynik
            TextView result = findViewById(R.id.result);
            CharSequence resultText = result.getText();

            //pobieram klikniety przycisk
            Button button = findViewById(view.getId());
            CharSequence button_text = button.getText();

            //usuwam zero wiodace
            if(resultText.equals("0")){
                // 0. jest okej
                if(button_text.equals(".")){
                    result.append(".");
                }
                // nie moze byc na poczatku znak
                else if(!Character.isDigit(button_text.charAt(0))){
                    return;
                }
                else{
                    result.setText(button_text);
                }

            }
            // blokad przed dwoma znakami kolo siebie
            else if(!Character.isDigit(resultText.charAt(resultText.length()-1)) &&
                    !Character.isDigit(button_text.charAt(button_text.length()-1))){
                return;
            }
            // blokada przez Z0C
            else if(resultText.length() > 1
                    && resultText.charAt(resultText.length()-2) != '.'
                    && resultText.charAt(resultText.length()-1) == '0'
                    && Character.isDigit(button_text.charAt(0))
                    && !Character.isDigit(resultText.charAt(resultText.length()-2))){
                return;
            }
            else{
                result.append(button_text);

            }

        }
    }

    class EqualsListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            TextView result = findViewById(R.id.result);
            String resultText = result.getText().toString();

            //walidacja wyrazenia
            if(!Character.isDigit(resultText.charAt(resultText.length()-1)))
                return;

            //szukam liczb
            String[] elements = resultText.split("[+,\\-,/,*]");
            ArrayList<Double> numbers = new ArrayList<>();
            for(String s: elements){
                numbers.add(Double.parseDouble(s));
            }

            // szukam operatorow
            Pattern pattern = Pattern.compile("[+,\\-,/,*]");
            Matcher matcher = pattern.matcher(resultText);
            ArrayList<String> operators = new ArrayList<>();
            while (matcher.find()){
                operators.add(matcher.group());
            }

            double outcome = numbers.get(0);

            for (int i=0; i<operators.size(); i++){
                switch (operators.get(i)) {
                    case "+":
                        outcome += numbers.get(i + 1);
                        break;
                    case "-":
                        outcome -= numbers.get(i + 1);
                        break;
                    case "*":
                        outcome *= numbers.get(i + 1);
                        break;
                    case "/":
                        if (numbers.get(i + 1) == 0)
                            Toast.makeText(getBaseContext(), "Nie dziel przez 0!", Toast.LENGTH_SHORT).show();
                        else {
                            outcome /= numbers.get(i + 1);
                        }
                        break;
                }

            }
            result.setText(Double.valueOf(outcome).toString());

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ustawienie listnerow
        findViewById(R.id.bK).setOnClickListener(new ButtonListener());
        findViewById(R.id.b0).setOnClickListener(new ButtonListener());
        findViewById(R.id.b1).setOnClickListener(new ButtonListener());
        findViewById(R.id.b2).setOnClickListener(new ButtonListener());
        findViewById(R.id.b3).setOnClickListener(new ButtonListener());
        findViewById(R.id.b4).setOnClickListener(new ButtonListener());
        findViewById(R.id.b5).setOnClickListener(new ButtonListener());
        findViewById(R.id.b6).setOnClickListener(new ButtonListener());
        findViewById(R.id.b7).setOnClickListener(new ButtonListener());
        findViewById(R.id.b8).setOnClickListener(new ButtonListener());
        findViewById(R.id.b9).setOnClickListener(new ButtonListener());
        findViewById(R.id.bM).setOnClickListener(new ButtonListener());
        findViewById(R.id.bMin).setOnClickListener(new ButtonListener());
        findViewById(R.id.bPlus).setOnClickListener(new ButtonListener());
        findViewById(R.id.bD).setOnClickListener(new ButtonListener());

        findViewById(R.id.bR).setOnClickListener(new EqualsListener());
    }
}