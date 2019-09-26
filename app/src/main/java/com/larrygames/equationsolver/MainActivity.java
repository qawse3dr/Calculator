package com.larrygames.equationsolver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static TextView  equation;
    public static TextView  answer;
    private static boolean isShift=false;
    private static boolean isRad = true;
    private static boolean infinity = false;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        equation = (TextView)findViewById(R.id.equation);
        answer = (TextView)findViewById(R.id.answer);
    }
    public static boolean getIsRad(){
        return isRad;
    }
    public static boolean getIsShift(){
        return isShift;
    }
    public static void setInfinity(boolean infinity1){
        infinity = infinity1;
    }

    public void shift(View v){
        if (!isShift){
            isShift=true;
            ((Button)findViewById(R.id.sin)).setText("asin");
            ((Button)findViewById(R.id.cos)).setText("acos");
            ((Button)findViewById(R.id.tan)).setText("atan");
        }else{
            isShift=false;
            ((Button)findViewById(R.id.sin)).setText("sin");
            ((Button)findViewById(R.id.cos)).setText("cos");
            ((Button)findViewById(R.id.tan)).setText("tan");
        }
    }
    public void sqrt(View view){
        equation.setText(equation.getText().toString()+"\u221A(");
    }
    public void clearAll(View view){
        equation.setText("");
    }
    public void del(View view){
        int delIndex=1; // Will find how many char need to be deleted (ie sin( vs 1)
        String[] strequation =  equation.getText().toString().split("");
        String newEquation ="" ;


        if (strequation[strequation.length-1].equals("g")){
                delIndex = 4;}
        if (strequation[strequation.length-1].equals("(")) {


            if (strequation[strequation.length-2].equals("\u221A")){
                delIndex = 2;
            }
            else if (strequation[strequation.length-2].equals("n")){
                if (strequation[strequation.length-3].equals("l")){
                    delIndex = 3;
                }
                else if (strequation[strequation.length-3].equals("i")) {
                    delIndex = 4;
                    try {
                        if (strequation[strequation.length-5].equals("a")){
                            delIndex=5;
                        }
                    } catch(Exception e){
                        ;
                    }
                }
                else if (strequation[strequation.length-3].equals("a")) {
                    delIndex = 4;
                    try {
                        if (strequation[strequation.length-5].equals("a")){
                            delIndex=5;
                        }
                    } catch(Exception e){
                        ;
                    }
                }
            }
            else if (strequation[strequation.length-2].equals("s")){
                delIndex = 4;
                try {
                    if (strequation[strequation.length-5].equals("a")){
                        delIndex=5;
                    }
                } catch(Exception e){
                    ;
                }
            }
        }
        for (int numIndex=0; numIndex<strequation.length-delIndex;numIndex++){
            newEquation+=strequation[numIndex];
        }
        equation.setText(newEquation);

    }
    public void log(View view){
        equation.setText(equation.getText().toString()+"log");
    }
    public void ln(View view){
        equation.setText(equation.getText().toString()+"ln(");
    }
    public void e(View view){
        equation.setText(equation.getText().toString()+"e");
    }
    public void pi(View view){
        equation.setText(equation.getText().toString()+"\u03C0");
    }
    public void sin(View view){
        if(!isShift){
            equation.setText(equation.getText().toString()+"sin(");}
        else {
            equation.setText(equation.getText().toString()+"asin(");}
        }

    public void cos(View view){
        if(!isShift){
            equation.setText(equation.getText().toString()+"cos(");}
        else {
            equation.setText(equation.getText().toString()+"acos(");
        }
    }
    public void tan(View view){
        if (!isShift){
            equation.setText(equation.getText().toString()+"tan(");}
        else{
            equation.setText(equation.getText().toString()+"atan(");
        }
    }
    public void pow(View view){
        equation.setText(equation.getText().toString()+"^");
    }
    public void abs(View view){
        equation.setText(equation.getText().toString()+"|");
    }
    public void leftBrack(View view){
        equation.setText(equation.getText().toString()+"(");
    }
    public void rightBrack(View view){
        equation.setText(equation.getText().toString()+")");
    }
    public void fact(View view){
        equation.setText(equation.getText().toString()+"!");
    }
    public void num(View view){
        equation.setText(equation.getText()+((Button)view).getText().toString());
    }
    public void plus(View view){
        equation.setText(equation.getText().toString()+"+");
    }
    public void minus(View view){
        equation.setText(equation.getText().toString()+"-");
    }
    public void muti(View view){
        equation.setText(equation.getText().toString()+"*");
    }
    public void div(View view){
        equation.setText(equation.getText().toString()+"/");
    }
    public void decimal(View view){
        equation.setText(equation.getText().toString()+".");
    }
    public void radOrDeg(View view){
        if (isRad){
            ((Button)view).setText("DEG");
            isRad=false;
        }else{
            ((Button)view).setText("RAD");
            isRad=true;
        }

    }
    public void solve(View view){
        try {
            double ans = new Calculator().solve(equation.getText().toString());
            if (infinity){
                answer.setText("Undefinded");
                infinity = false;
            }else{
            answer.setText(String.valueOf(ans));}
        }catch (Exception e){
            answer.setText("ERROR");
            e.printStackTrace();
        }


    }
    public void equal(View view){
        equation.setText(equation.getText().toString()+"=");
    }
    public void x(View view){
        equation.setText(equation.getText().toString()+"X");
    }
    public void y(View view){
        equation.setText(equation.getText().toString()+"Y");
    }

}
