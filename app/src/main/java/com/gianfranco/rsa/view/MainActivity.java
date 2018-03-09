package com.gianfranco.rsa.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gianfranco.rsa.R;
import com.gianfranco.rsa.view.fragment.InputKeySizeFragment;
import com.gianfranco.rsa.view.fragment.InputPlainTextFragment;
import com.gianfranco.rsa.view.fragment.RSADetailsFragment;

public class MainActivity extends AppCompatActivity {

    private InputKeySizeFragment.OnKeySizeListener onKeySizeListener = new InputKeySizeFragment.OnKeySizeListener() {
        @Override
        public void onKeySizeSelected(int keySize) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, InputPlainTextFragment.newInstance(onMessageListener))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private InputPlainTextFragment.OnMessageListener onMessageListener = new InputPlainTextFragment.OnMessageListener() {
        @Override
        public void onMessageEntered(String message) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, RSADetailsFragment.newInstance(onClickHomeListener))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private RSADetailsFragment.OnClickHomeListener onClickHomeListener = new RSADetailsFragment.OnClickHomeListener() {
        @Override
        public void onClickHome() {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, InputKeySizeFragment.newInstance(onKeySizeListener))
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, InputKeySizeFragment.newInstance(onKeySizeListener))
                    .addToBackStack(null)
                    .commit();
        }
    }
}

