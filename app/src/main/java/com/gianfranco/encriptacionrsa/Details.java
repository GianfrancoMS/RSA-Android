package com.gianfranco.encriptacionrsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

@SuppressWarnings("FieldCanBeLocal")

public class Details extends AppCompatActivity {

    private TextView pValue;
    private TextView qValue;
    private TextView nValue;
    private TextView zValue;
    private TextView dValue;
    private TextView eValue;
    private TextView encryptedValue;
    private TextView desencryptedValue;
    private RSA rsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle=getIntent().getExtras();
        try{
             rsa=new RSA(bundle.getInt("Size Bits"), bundle.getString("Plain Text"));
        }catch(Exception e){
            Log.i("RSA","Error");
        }

        pValue=(TextView) findViewById(R.id.p_value);
        qValue=(TextView) findViewById(R.id.q_value);
        nValue=(TextView) findViewById(R.id.n_value);
        zValue=(TextView) findViewById(R.id.z_value);
        dValue=(TextView) findViewById(R.id.d_value);
        eValue=(TextView) findViewById(R.id.e_value);
        encryptedValue=(TextView) findViewById(R.id.encrypted_value);
        desencryptedValue=(TextView) findViewById(R.id.desencrypted_value);

        pValue.setText(rsa.getP());
        qValue.setText(rsa.getQ());
        nValue.setText(rsa.getN());
        zValue.setText(rsa.getZ());
        dValue.setText(rsa.getD());
        eValue.setText(rsa.getE());
        encryptedValue.setText(rsa.getEncryptedText());
        desencryptedValue.setText(rsa.getDesencryptedText());

        Button button=(Button)findViewById(R.id.btn_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Details.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
