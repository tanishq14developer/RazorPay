package com.example.razorpay;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    Button paybtn;
    TextView paytxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());

        paytxt = findViewById(R.id.paytext);
        paybtn = findViewById(R.id.paybtn);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makepayement();
            }
        });
    }
    public void makepayement()

    {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_EMLOQ9wjLvvSHj");
        checkout.setImage(R.drawable.symbols);

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "Tanishq Chawda");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","7987402011");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        paytxt.setText("Successful payment id :" +s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        paytxt.setText("Failed and cause is :" +s);

    }
}