package com.example.aymeric.ladistrib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StoreActivity extends AppCompatActivity {

    int[] IMAGES = {R.drawable.ink_cartible_icon, R.drawable.red_pen_icon, R.drawable.sheet_icon, R.drawable.pen_icon};

    String[] NAMES = {"Ink Cartridge", "Red Pen", "Sheet(s)", "Black Pen"};

    String[] UNIT_PRICE = {"0.10", "0.50", "0.05", "0.40"};

    String[] QUANTITY = {"0", "0", "0", "0",
            "0", "0", "0", "0"};

    private ListView list;
    private TextView balance_store_txt;
    private TextView textView_moins;
    private TextView textView_plus;
    private TextView quantity;
    int qt = 0;

    double balance_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        list = findViewById(R.id.store_listView);
        StoreActivity.CustomAdapter customAdapter = new StoreActivity.CustomAdapter();
        list.setAdapter(customAdapter);

        balance_store_txt = findViewById(R.id.store_balance);
        balance_store = getIntent().getExtras().getDouble("balanceValue");

        balance_store_txt.setText("Balance : "+balance_store+" €");

    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout_store, null);

            ImageView imageView = view.findViewById(R.id.store_image_view);
            TextView textView_name = view.findViewById(R.id.store_name);
            TextView textView_unit_price = view.findViewById(R.id.store_unit_price);
            TextView textView_qt = view.findViewById(R.id.store_quantity);
            textView_plus = view.findViewById(R.id.textViewPlus);
            textView_moins = view.findViewById(R.id.textViewMoins);
            quantity = findViewById(R.id.store_quantity);
            int qt[];

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_unit_price.setText(UNIT_PRICE[i]+"€");
            textView_qt.setText(QUANTITY[i]);


            return view;
        }
    }

}
