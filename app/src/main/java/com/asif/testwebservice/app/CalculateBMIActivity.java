package com.asif.testwebservice.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(dataAdapter);


        bmiTextView = (TextView) findViewById(R.id.textBMIValue);

        heightFeetTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleBMI();

            }
        });

        heightInchTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleBMI();

            }
        });

        weightTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                handleBMI();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleBMI();

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

    private void handleBMI()
    {
        if (!ageTextEdit.getText().toString().equals(""))
            age = Double.parseDouble(ageTextEdit.getText().toString());
        else
            age = 0.0;

        if (!weightTextEdit.getText().toString().equals(""))
            weight = Double.parseDouble(weightTextEdit.getText().toString());
        else
            weight = 0.0;

        if (!heightFeetTextEdit.getText().toString().equals(""))
            heightFeet = Double.parseDouble(heightFeetTextEdit.getText().toString());
        else
            heightFeet = 0.0;

        if (!heightInchTextEdit.getText().toString().equals(""))
            heightInch = Double.parseDouble(heightInchTextEdit.getText().toString());
        else
            heightInch = 0.0;


        if(heightFeet > 0)
            height = (heightFeet * 12)  * 0.0254;
        else
            height = 0.0;

        if(heightInch > 0)
        {
            height = height + (heightInch * 0.0254);
        }


        //toast = Toast.makeText(getApplicationContext(), Double.toString(height)+Double.toString(weight), Toast.LENGTH_LONG);

        bmi = calculateBMI(height, weight);
        fBMI = Float.parseFloat(Double.toString(bmi));
        bmiTextView.setText(Float.toString(fBMI));
    }
}
