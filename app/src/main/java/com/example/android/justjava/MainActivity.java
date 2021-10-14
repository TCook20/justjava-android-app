package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    EditText editName;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit method early
            return;
        } else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit method early
            return;
        } else {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        //User Name
        editName = (EditText) findViewById(R.id.name);
        String name = editName.getText().toString();

        //Whipped Cream Topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox_whippedcream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Chocolate Topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " +  name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param addWhippedCream is whether the user wants whipped cream topping
     * @param addChocolate    is whether the user wants chocolate topping
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * This method is called when the order button is clicked.
     */
    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param name            is the name of the user
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberCoffees);
    }
}

