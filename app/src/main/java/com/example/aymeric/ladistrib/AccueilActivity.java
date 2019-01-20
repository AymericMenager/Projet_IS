package com.example.aymeric.ladistrib;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Bluetooth libraries
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public class AccueilActivity extends AppCompatActivity {

    public static final int REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ENABLE_BLUETOOTH = 11;
    private BluetoothAdapter bluetoothAdapter;

    int[] IMAGES = {R.drawable.ink_cartible_icon, R.drawable.red_pen_icon, R.drawable.sheet_icon, R.drawable.pen_icon,
            R.drawable.ink_cartible_icon, R.drawable.red_pen_icon, R.drawable.sheet_icon, R.drawable.pen_icon};

    String[] NAMES = {"Ink Cartridge", "Red Pen", "Sheet(s)", "Black Pen",
            "Ink Cartridge", "Red Pen", "Sheet(s)", "Black Pen"};

    String[] UNIT_PRICE = {"0.10", "0.50", "0.05", "0.40",
            "0.10", "0.50", "0.05", "0.40"};

    String[] QUANTITY = {"1", "2", "200", "1",
            "1", "2", "200", "1"};

    String[] TOTAL_PRICE = {"0.10", "1.00", "10.0", "0.40",
            "0.10", "1.00", "10.0", "0.40"};

    private ListView list;
    private ImageView store_icon;
    private ImageView disconnect_btn;
    private TextView balance_txt;
    private ImageView bluetoothIcon;
    private ListView devicesList;
    private ArrayAdapter<String> listAdapter;

    double balance = 10.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        list = findViewById(R.id.list_purchase);
        CustomAdapter customAdapter = new CustomAdapter();
        list.setAdapter(customAdapter);

        store_icon=findViewById(R.id.store_icon);
        disconnect_btn = findViewById(R.id.power_button);
        balance_txt = findViewById(R.id.balance);
        bluetoothIcon = findViewById(R.id.bluetooth_icon);
        devicesList = findViewById(R.id.device_listview);

        balance_txt.setText("Balance : "+balance+" €");
        store_icon.setEnabled(false);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //On crée un "array" (une liste) pour afficher les appareils dispo à l'écran
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        devicesList.setAdapter(listAdapter);

        //we check bluetoothstate
        checkBluetoothState();

        //we register a dedicated receiver for some Bluetooth actions
        // On peut trouver les appareils...
        registerReceiver(deviceFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        //... Commencer la recherche d'appareils...
        registerReceiver(deviceFoundReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        //... Et stopper la recherche
        registerReceiver(deviceFoundReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        bluetoothIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bluetoothAdapter!=null && bluetoothAdapter.isEnabled()) {
                    //On check si on peut avoir accès à la position du téléphone
                    if (checkCoarseLocationPermission()) {
                        listAdapter.clear();
                        bluetoothAdapter.startDiscovery();
                    }

                } else {
                    checkBluetoothState();
                }

            }
        });

        store_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toStore = new Intent(AccueilActivity.this, StoreActivity.class);
                toStore.putExtra("balanceValue", balance);
                startActivity(toStore);
            }
        });

        disconnect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disco = new Intent(AccueilActivity.this, HomeActivity.class);
                startActivity(disco);
                Toast.makeText(AccueilActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        });

        //we check permission a start of the app
        checkCoarseLocationPermission();

    }

    class CustomAdapter extends BaseAdapter{


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
            view = getLayoutInflater().inflate(R.layout.customlayout, null);

            ImageView imageView = view.findViewById(R.id.image_view);
            TextView textView_name = view.findViewById(R.id.name);
            TextView textView_unit_price = view.findViewById(R.id.unit_price);
            TextView textView_total_price = view.findViewById(R.id.total_price);
            TextView textView_qt = view.findViewById(R.id.quantity);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_unit_price.setText(UNIT_PRICE[i]+"€");
            textView_total_price.setText(TOTAL_PRICE[i]+"€");
            textView_qt.setText("x"+QUANTITY[i]);


            return view;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(deviceFoundReceiver);
    }

    private boolean checkCoarseLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_ACCESS_COARSE_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    private void checkBluetoothState() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on your device !", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(this, "Device discovering process ...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bluetooth is enabled", Toast.LENGTH_SHORT).show();
                    bluetoothIcon.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "You need to enable Bluetooth", Toast.LENGTH_SHORT).show();
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            checkBluetoothState();
        }
    }

    // Fonction qui regarde si l'app peut avoiraccès à la position de l'appareil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Access coarse location allowed. You can scan Bluetooth devices", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Access coarse location forbidden. You can't scan Bluetooth devices", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // we need to implement our receiver to get devices detected
    // SCAN LES APPAREILS AUX ALENTOURS
    private final BroadcastReceiver deviceFoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // Si on a trouvé un appareil...
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //... On le met dans la liste
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //... Avec son nom et son adresse
                listAdapter.add(device.getName() + "\n" + device.getAddress());
                listAdapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // Quand on a fini de scanner, y a marquer SCANNER sur le bouton
                Toast.makeText(AccueilActivity.this, "Paired with a Distrib'", Toast.LENGTH_SHORT).show();
                store_icon.setEnabled(true);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                // Quand on est en trzinde scanner, y a marqué SCANNING IN PROGRESS sur le bouton
            }
        }
    };

}
