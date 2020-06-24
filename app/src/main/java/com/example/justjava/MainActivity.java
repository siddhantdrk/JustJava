package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int qi=0;
    public void increment(View view){
        if(qi<100) {
            qi++;
            displayQuantity(qi);
        }
        else{
            Toast.makeText(this, getString(R.string.stopincrement), Toast.LENGTH_SHORT).show();
        }
    }
    public void decrement(View view){
        if(qi>0){
            qi--;
            displayQuantity(qi);
        }
        else{
            Toast.makeText(this, getString(R.string.stopdecrement), Toast.LENGTH_SHORT).show();
        }
    }

    public void submitOrder(View view) {
        CheckBox whippedCream= findViewById(R.id.checkBox2);
        CheckBox Chocolate= findViewById(R.id.checkBox3);
        EditText Name= findViewById(R.id.editText);
        boolean hasWhippedCream=whippedCream.isChecked();
        boolean hasChocolate= Chocolate.isChecked();
        String userName=Name.getText().toString();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSumary(price,hasWhippedCream,hasChocolate,userName);
        String subject=getString(R.string.subject)+userName;

         // below setting up an implicit intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String s=""+number;
        quantityTextView.setText(s);
    }


    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int perprice=5;
        if(hasWhippedCream){
            perprice+=1;
        }
        if(hasChocolate){
            perprice+=2;
        }
        return(qi * perprice);
    }
    private String createOrderSumary(int price,boolean hasWhippedCream,boolean hasChocolate, String username){
        String summary= getString(R.string.username)+username;
               summary+="\n"+getString(R.string.whip)+ hasWhippedCream;
               summary+="\n"+getString(R.string.chocochoco)+ hasChocolate;
               summary+= "\n"+getString(R.string.qty)+ qi;
               summary+= "\n"+getString(R.string.total)+ price;
                    NumberFormat.getCurrencyInstance().format(price);
               summary+= "\n"+getString(R.string.ty);
        return(summary);
    }
}
