package com.example.steven.icecream;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView priceView;
    TextView fudgeAmount;
    SeekBar fudgeBar;
    Spinner flavorSpinner;
    Spinner sizeSpinner;
    CheckBox peanutsBox;
    CheckBox almondsBox;
    CheckBox oreosBox;
    CheckBox gummiesBox;
    CheckBox strawberriesBox;
    CheckBox browniesBox;
    CheckBox marshmallowsBox;
    CheckBox mandmsBox;
    private static final Map<String, Double> finalPrices = addPrices();
    double subTotal;

    ArrayList<OrderItem> orderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        orderHistory = new ArrayList<OrderItem>();
        priceView = (TextView) findViewById(R.id.priceView);
        fudgeAmount = (TextView) findViewById(R.id.fudgeAmount);
        fudgeBar = (SeekBar) findViewById(R.id.fudgeBar);
        sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
        flavorSpinner = (Spinner) findViewById(R.id.flavorSpinner);
        peanutsBox = (CheckBox) findViewById(R.id.peanutsBox);
        almondsBox = (CheckBox) findViewById(R.id.almondsBox);
        oreosBox = (CheckBox) findViewById(R.id.oreosBox);
        gummiesBox = (CheckBox) findViewById(R.id.gummiesBox);
        strawberriesBox = (CheckBox) findViewById(R.id.strawberriesBox);
        browniesBox = (CheckBox) findViewById(R.id.browniesBox);
        marshmallowsBox = (CheckBox) findViewById(R.id.marshmallowsBox);
        mandmsBox = (CheckBox) findViewById(R.id.mandmsBox);
        subTotal = 0.0;
        fudgeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fudgeAmount.setText(Integer.toString(progress) + " oz.");
                processCheckBoxChange(seekBar.getRootView());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //this is purposely left blank
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //this is purposely left blank
            }
        });
        sizeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "You selected: " + Integer.toString(position),Toast.LENGTH_SHORT).show();
                processCheckBoxChange(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private static Map<String, Double> addPrices() {
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("peanuts", 0.15);
        prices.put("m&ms", 0.25);
        prices.put("almonds", 0.15);
        prices.put("brownies", 0.20);
        prices.put("strawberries", 0.20);
        prices.put("oreos", 0.20);
        prices.put("gummy bears", 0.20);
        prices.put("marshmallows", 0.15);
        prices.put("1oz", 0.15);
        prices.put("2oz", 0.25);
        prices.put("3oz", 0.30);
        prices.put("single", 2.99);
        prices.put("double", 3.99);
        prices.put("triple", 4.99);
        return prices;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_about) {
            //Toast.makeText(this, "'About' selected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra("developer", "Steven Turmel");
            startActivity(intent);
            return true; //if we handled the action
        } else if (id == R.id.action_menu_order_history) {
            //Toast.makeText(this, "'Order History' selected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, OrderHistoryActivity.class);
            //pass the orders over
            intent.putExtra("DataKey", orderHistory);
            startActivity(intent);
            return true; //if we handled the action
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void processReset(View view) {
        Log.d("DEBUG", "Reset Pressed");
        peanutsBox.setChecked(false);
        almondsBox.setChecked(false);
        oreosBox.setChecked(false);
        gummiesBox.setChecked(false);
        marshmallowsBox.setChecked(false);
        strawberriesBox.setChecked(false);
        browniesBox.setChecked(false);
        mandmsBox.setChecked(false);
        updatePrice();
    }

    public void processOrder(View view) {
        Toast.makeText(this, "Your order is on the way!", Toast.LENGTH_LONG).show();
        updatePrice();
        orderHistory.add(new OrderItem(getToppings(), subTotal, fudgeBar.getProgress(),
                sizeSpinner.getSelectedItem().toString(), flavorSpinner.getSelectedItem().toString()));
    }

    private String[] getToppings() {
        ArrayList<String> temp = new ArrayList<>();
        if (peanutsBox.isChecked()){
            temp.add("Peanuts");
        } if (almondsBox.isChecked()){
            temp.add("Almonds");
        } if (strawberriesBox.isChecked()) {
            temp.add("Strawberries");
        } if (gummiesBox.isChecked()) {
            temp.add("Gummy Bears");
        } if (mandmsBox.isChecked()){
            temp.add("M&Ms");
        } if (browniesBox.isChecked()){
            temp.add("Brownies");
        } if (oreosBox.isChecked()){
            temp.add("Oreos");
        } if (marshmallowsBox.isChecked()){
            temp.add("Marshmallows");
        }
        return temp.toArray(new String[temp.size()]);
    }

    public void processTheWorks(View view) {
        Log.d("DEBUG", "'The Works' Pressed.");
        peanutsBox.setChecked(true);
        almondsBox.setChecked(true);
        oreosBox.setChecked(true);
        gummiesBox.setChecked(true);
        marshmallowsBox.setChecked(true);
        strawberriesBox.setChecked(true);
        browniesBox.setChecked(true);
        mandmsBox.setChecked(true);
        updatePrice();
    }

    public void updatePrice() {
        Double price = 0.0;
        if (peanutsBox.isChecked()){
            price += finalPrices.get("peanuts");
        } if (almondsBox.isChecked()){
            price += finalPrices.get("almonds");
        } if (oreosBox.isChecked()){
            price += finalPrices.get("oreos");
        } if (gummiesBox.isChecked()){
            price += finalPrices.get("gummy bears");
        } if (marshmallowsBox.isChecked()){
            price += finalPrices.get("marshmallows");
        } if (strawberriesBox.isChecked()){
            price += finalPrices.get("strawberries");
        } if (browniesBox.isChecked()){
            price += finalPrices.get("brownies");
        } if (mandmsBox.isChecked()){
            price += finalPrices.get("m&ms");
        } if (fudgeBar.getProgress() == 1) {
            price += finalPrices.get("1oz");
        } if (fudgeBar.getProgress() == 2) {
            price += finalPrices.get("2oz");
        } if (fudgeBar.getProgress() == 3) {
            price += finalPrices.get("3oz");
        }
        price += finalPrices.get(sizeSpinner.getSelectedItem().toString().toLowerCase());
        priceView.setText("$" + String.format("%.2f",price));
        subTotal = price;
    }

    public void processCheckBoxChange(View view) {
        updatePrice();
        if (view.getId() == R.id.sizeSpinner) {
            // = sizeSpinner.getSelectedItem();
        }
    }
}
