package com.asif.testwebservice.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asif on 6/15/14.
 */
public class CalculateBMIActivity extends Activity {
    private EditText ageTextEdit;
    private EditText heightFeetTextEdit;
    private EditText heightInchTextEdit;
    private EditText weightTextEdit;
    private TextView bmiTextView;
    private ImageView imageView;

    private int count = 0;

    private double age;
    private double heightFeet;
    private double heightInch;
    private double height;
    private double weight;
    private float fBMI;

    private double bmi;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calculation);

        ageTextEdit = (EditText) findViewById(R.id.numberAge);

        imageView = (ImageView) findViewById(R.id.imgMaleFemale);

        imageView.setImageResource(R.drawable.male);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count % 2 == 0)
                {
                    imageView.setImageResource(R.drawable.male);
                }
                else
                {
                    imageView.setImageResource(R.drawable.female);
                }
                count += 1;
            }
        });

        heightFeetTextEdit = (EditText) findViewById(R.id.numberHeightFeet);


        heightInchTextEdit = (EditText) findViewById(R.id.numberHeightInch);




        weightTextEdit = (EditText) findViewById(R.id.numberWeight);

        Spinner weightSpinner = (Spinner) findViewById(R.id.spinnerWeight);
        List<String> list = new ArrayList<String>();
        list.add("KG");
        list.add("LB");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(dataAdapter);


        bmiTextView = (TextView) findViewById(R.id.textBMIValue);

        Button calculateButton = (Button)findViewById(R.id.buttonCalculateBMI);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ageTextEdit.getText().toString().equals(""))
                    age = Double.parseDouble(ageTextEdit.getText().toString());

                if (!weightTextEdit.getText().toString().equals(""))
                    weight = Double.parseDouble(weightTextEdit.getText().toString());

                if (!heightFeetTextEdit.getText().toString().equals(""))
                    heightFeet = Double.parseDouble(heightFeetTextEdit.getText().toString());

                if (!heightInchTextEdit.getText().toString().equals(""))
                    heightInch = Double.parseDouble(heightInchTextEdit.getText().toString());


                if(heightFeet > 0)
                    height = (heightFeet * 12)  * 0.0254;
                if(heightInch > 0)
                {
                    if(height > 0)
                    {
                        height = height + (heightInch * 0.0254);
                    }
                    else
                        height = heightInch * 0.0254;
                }

                //toast = Toast.makeText(getApplicationContext(), Double.toString(height), Toast.LENGTH_LONG);

                bmi = calculateBMI(height, weight);
                fBMI = Float.parseFloat(Double.toString(bmi));
                bmiTextView.setText(Float.toString(fBMI));
            }
        });
    }

    private double calculateBMI(double height, double weight)
    {
        double bmi;

        if(height > 0 && weight > 0)
        {
            bmi = weight/(height * height);
        }
        else
            bmi = 0.0;

        return bmi;
    }
}
