package com.example.aymeric.ladistrib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrayonActivity extends AppCompatActivity {

    private TextView nb_pen;
    private TextView nb_sheet;
    private TextView nb_pencil;
    private TextView nb_eraser;

    private TextView moins_pen;
    private TextView moins_sheet;
    private TextView moins_pencil;
    private TextView moins_eraser;

    private TextView plus_pen;
    private TextView plus_sheet;
    private TextView plus_pencil;
    private TextView plus_eraser;

    private TextView total;

    private Button buy;

    private int pen=0;
    private int sheet=0;
    private int pencil=0;
    private int eraser=0;
    private double price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crayon);

        nb_pen=findViewById(R.id.nb_pen);
        nb_sheet=findViewById(R.id.nb_sheet);
        nb_pencil=findViewById(R.id.nb_pencil);
        nb_eraser=findViewById(R.id.nb_eraser);

        moins_pen=findViewById(R.id.moins_pen);
        moins_sheet=findViewById(R.id.moins_sheet);
        moins_pencil=findViewById(R.id.moins_pencil);
        moins_eraser=findViewById(R.id.moins_eraser);

        plus_pen=findViewById(R.id.plus_pen);
        plus_sheet=findViewById(R.id.plus_sheet);
        plus_pencil=findViewById(R.id.plus_pencil);
        plus_eraser=findViewById(R.id.plus_eraser);

        total=findViewById(R.id.total);

        buy=findViewById(R.id.buy);


        moins_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pen>0){
                pen--;
                price= price - 1;
                nb_pen.setText(""+pen);
                total.setText("Total : "+price+" €");
                }else{
                    nb_pen.setText("0");
                }
            }
        });

        plus_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pen++;
                    price = price + 1;
                    nb_pen.setText(""+pen);
                    total.setText("Total : "+price+" €");
            }
        });

        moins_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sheet>0){
                    sheet--;
                    price= price - 0.1;
                    nb_sheet.setText(""+sheet);
                    total.setText("Total : "+price+" €");
                }else{
                    nb_sheet.setText("0");
                }
            }
        });

        plus_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheet++;
                price = price + 0.1;
                nb_sheet.setText(""+sheet);
                total.setText("Total : "+price+" €");
            }
        });

        moins_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pencil>0){
                    pencil--;
                    price= price-0.5;
                    nb_pencil.setText(""+pencil);
                    total.setText("Total : "+price+" €");
                }else{
                    nb_pencil.setText("0");
                }
            }
        });

        plus_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pencil++;
                price = price +0.5;
                nb_pencil.setText(""+pencil);
                total.setText("Total : "+price+" €");
            }
        });

        moins_eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eraser>0){
                    eraser--;
                    price= price - 0.2;
                    nb_eraser.setText(""+eraser);
                    total.setText("Total : "+price+" €");
                }else{
                    nb_eraser.setText("0");
                }
            }
        });

        plus_eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraser++;
                price = price + 0.2;
                nb_eraser.setText(""+eraser);
                total.setText("Total : "+price+" €");
            }
        });



    }
}
