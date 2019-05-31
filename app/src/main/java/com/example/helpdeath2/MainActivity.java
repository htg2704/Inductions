package com.example.helpdeath2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;


public class MainActivity extends AppCompatActivity {
    EditText correct, guess;
    TextView result;
    Button submit, check;
    LinearLayout bg;

    public static final String mypref = "MyPrefs";
    public static final String c_age = "correctage";
    SharedPreferences sh;
    int correctage;
    String cage;
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

        sh = getSharedPreferences(mypref, Context.MODE_PRIVATE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                cage = getAge();
                SharedPreferences.Editor editor = sh.edit();
                if(cage!=null&&!"".equals(cage))
                {
                    editor.putString(c_age, cage);
                    editor.commit();
                    correct.setText("");
                    Toast.makeText(getApplicationContext(),"Correct age saved",Toast.LENGTH_LONG).show();}
                else{
                    Toast.makeText(getApplicationContext(),"Please enter some age",Toast.LENGTH_LONG).show();
                   }
                    x=0;

            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                int a,Guess;
                String z = sh.getString(c_age, "default");
                String guess = getGuess();
                if(z!=null&&!"".equals(z)){
                    a = Integer.parseInt(z);
                    if(guess!=null&&!"".equals(guess)){
                        Guess = Integer.parseInt(guess);
                        compare(a,Guess);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Please enter some guess",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter correct age first",Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    public String getAge(){
    String corr = correct.getText().toString();
    /*int a=0;*/

    return corr;
    }

    public String getGuess(){
        String corr = guess.getText().toString();

        return corr;
    }

    public void compare(int ca, int ga){
        String resultlabel = "";
        SharedPreferences sh1 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
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
            bg.setBackgroundColor(Color.WHITE);
            x=0;
        }

        if(ga!=ca){
            x++;

            editor.putInt("times", x);
            editor.apply();
            int y = sh.getInt("times", x);
            if(y>4){
                Toast.makeText(getApplicationContext(),"Game over for you, you failed " + y + " times",Toast.LENGTH_LONG).show();
                y=0;
                x=0;
                String z = sh.getString(c_age, "default");
                int a = Integer.parseInt(z);
                guess.setText("");
                correct.setText("");
                result.setText("The correct age was "+ a + " years\n"+"Submit new correct age to play again");
                return;
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
