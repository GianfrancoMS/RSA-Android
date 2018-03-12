package com.gianfranco.rsa.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gianfranco.rsa.R;
import com.gianfranco.rsa.view.fragment.InputKeySizeFragment;
import com.gianfranco.rsa.view.fragment.InputPlainTextFragment;
import com.gianfranco.rsa.view.fragment.RSADetailsFragment;

public class MainActivity extends AppCompatActivity implements InputKeySizeFragment.OnKeySizeListener,
        InputPlainTextFragment.OnMessageListener, RSADetailsFragment.OnClickHomeListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new InputKeySizeFragment(), InputKeySizeFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onKeySizeSelected(int keySize) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, new InputPlainTextFragment(), InputPlainTextFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageEntered(String message) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, new RSADetailsFragment(), RSADetailsFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClickHome() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
                .replace(R.id.main_container, new InputKeySizeFragment(), InputKeySizeFragment.TAG)
                .addToBackStack(null)
                .commit();
    }
}

