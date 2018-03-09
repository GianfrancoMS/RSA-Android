package com.gianfranco.rsa.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gianfranco.rsa.R;

public class RSADetailsFragment extends Fragment {

    private TextView pValue;
    private TextView qValue;
    private TextView nValue;
    private TextView zValue;
    private TextView dValue;
    private TextView eValue;
    private TextView encryptedValue;
    private TextView decryptedValue;
    private Button buttonHome;

    private OnClickHomeListener listener;

    public static RSADetailsFragment newInstance(OnClickHomeListener listener) {
        RSADetailsFragment fragment = new RSADetailsFragment();
        fragment.setOnClickHomeListener(listener);
        return fragment;
    }

    @FunctionalInterface
    public interface OnClickHomeListener {
        void onClickHome();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rsa_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pValue = view.findViewById(R.id.tv_pValue);
        qValue = view.findViewById(R.id.tv_qValue);
        nValue = view.findViewById(R.id.tv_nValue);
        zValue = view.findViewById(R.id.tv_zValue);
        dValue = view.findViewById(R.id.tv_dValue);
        eValue = view.findViewById(R.id.tv_eValue);
        encryptedValue = view.findViewById(R.id.tv_encrypted_value);
        decryptedValue = view.findViewById(R.id.tv_decrypted_value);
        buttonHome = view.findViewById(R.id.btn_home);

        buttonHome.setOnClickListener(v -> listener.onClickHome());
    }

    @Override
    public void onDestroyView() {
        pValue = null;
        qValue = null;
        nValue = null;
        zValue = null;
        dValue = null;
        eValue = null;
        encryptedValue = null;
        decryptedValue = null;
        buttonHome = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        listener = null;
        super.onDestroy();
    }

    private void setOnClickHomeListener(OnClickHomeListener listener) {
        this.listener = listener;
    }
}

