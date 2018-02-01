package com.gianfranco.rsa.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gianfranco.rsa.R;

public class InputPlainText extends AppCompatActivity {

    private int size_bits;
    private EditText editText;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_plain_text);

        Bundle bundle= getIntent().getExtras();

        size_bits=bundle.getInt("SIZE_BITS");
        int size_bytes=size_bits/8;     //To avoid the theoretical limit of RSA encryption

        editText=(EditText) findViewById(R.id.edit_text_plain_text);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size_bytes)});

        Button button=(Button) findViewById(R.id.btn_next2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString()))
                {
                    snackbar = Snackbar.make(v,"Ingrese un mensaje para encriptar",Snackbar.LENGTH_LONG)
                            .setAction("Entendido", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });
                    snackbar.setActionTextColor(ContextCompat.getColor(InputPlainText.this,R.color.colorSecondaryText));
                    TextView textView= (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
                    textView.setTextColor(ContextCompat.getColor(InputPlainText.this,R.color.colorPrimaryText));
                    View view=snackbar.getView();
                    view.setBackgroundColor(ContextCompat.getColor(InputPlainText.this,R.color.colorPrimary));
                    snackbar.show();
                }

                else if (editText.getText().toString().trim().length()==0)
                {
                    snackbar= Snackbar.make(v,"Ingrese un mensaje valido",Snackbar.LENGTH_LONG)
                            .setAction("Entendido", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });
                    snackbar.setActionTextColor(ContextCompat.getColor(InputPlainText.this,R.color.colorSecondaryText));
                    TextView textView= (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
                    textView.setTextColor(ContextCompat.getColor(InputPlainText.this,R.color.colorPrimaryText));
                    View view=snackbar.getView();
                    view.setBackgroundColor(ContextCompat.getColor(InputPlainText.this,R.color.colorPrimary));
                    snackbar.show();
                }

                else{
                    Intent intent=new Intent(InputPlainText.this,Details.class);
                    String tempString=editText.getText().toString();
                    intent.putExtra("Plain Text", tempString);
                    intent.putExtra("Size Bits", size_bits);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
