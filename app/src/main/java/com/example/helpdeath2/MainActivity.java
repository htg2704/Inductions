package com.example.helpdeath2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    EditText correct, guess;
    TextView result;
    Button submit, check;
    LinearLayout bg;
    int x = 0, c = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correct = (EditText) findViewById(R.id.correct_age);
        guess = (EditText) findViewById(R.id.guess);
        result = (TextView) findViewById(R.id.result);
        submit = (Button) findViewById(R.id.button);
        check = (Button) findViewById(R.id.button2);
        bg = (LinearLayout) findViewById(R.id.back);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int correctage = getAge();

                Toast.makeText(getApplicationContext(),"Correct age saved",Toast.LENGTH_LONG).show();

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int correctage = getAge();
                SharedPreferences sh = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putInt(getString(R.string.app_name), correctage);
                editor.apply();
                int z = sh.getInt("Help Death 2", correctage);
                int guess = getGuess();
                compare(z,guess);
            }
        });

    }


    public int getAge(){
    String corr = correct.getText().toString();
    int a = Integer.parseInt(corr);
    return a;
    }

    public int getGuess(){
        String corr = guess.getText().toString();
        int a = Integer.parseInt(corr);
        return a;
    }

    public void compare(int ca, int ga){
        String resultlabel = "";
        SharedPreferences sh = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        if(ga>ca){
            resultlabel = "Your guess was wrong and it was more than correct age";
            result.setText(resultlabel);
            guess.setText("");

        }
        if(ga<ca){
            resultlabel = "Your guess was wrong and it was less than correct age";
            result.setText(resultlabel);
            guess.setText("");


        }
        if(ga==ca){
            resultlabel = "Your guess was correct";
            result.setText(resultlabel);
            guess.setText("");
            c++;
            editor.putInt(getString(R.string.app_name), c);


        }

        if(ga!=ca){
            x++;

            editor.putInt(getString(R.string.app_name), x);
            editor.apply();
            int y = sh.getInt(getString(R.string.app_name), x);
            if(y>4){
                Toast.makeText(getApplicationContext(),"Game over for you, you failed " + x + " times",Toast.LENGTH_LONG).show();
                y=0;
                guess.setText("");
                correct.setText("");
                result.setText("Result will be shown here");
            }
        int d;
            if((ga-ca)>=0)
                d = ga - ca;
            else
                d = ca - ga;

        if(d>15)
        {
            bg.setBackgroundColor(Color.RED);
        }
            if(d<5)
            {
                bg.setBackgroundColor(Color.GREEN);
            }
         if((d>5&&d<15)||d==0)
             bg.setBackgroundColor(Color.WHITE);
        }

        Toast.makeText(getApplicationContext(),"Failed: "+ x + " times\n" + "Guessed Correctly: "+ c + " times",Toast.LENGTH_LONG).show();
    }

}
