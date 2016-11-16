package com.gianfranco.encriptacionrsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinnerBits = (Spinner) findViewById(R.id.spinner_bits);
        Integer [] bits = new Integer[] {64,128,256,512,1024,2048};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,bits);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerBits.setAdapter(adapter);

        Button button=(Button) findViewById(R.id.btn_next1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,InputPlainText.class);
                intent.putExtra("SIZE_BITS",(Integer) spinnerBits.getSelectedItem());
                startActivity(intent);
                finish();
            }
        });

    }
}

