package com.gianfranco.rsa.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.gianfranco.rsa.R;
import com.gianfranco.rsa.model.RSA;
import com.gianfranco.rsa.view.SnackbarUtil;
import com.gianfranco.rsa.viewmodel.RSAViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RSADetailsFragment extends Fragment {
    public final static String TAG = RSADetailsFragment.class.getCanonicalName();

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

    private final CompositeDisposable disposable = new CompositeDisposable();

    private RSAViewModel rsaViewModel;

    @FunctionalInterface
    public interface OnClickHomeListener {
        void onClickHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rsa_details, container, false);
        hideKeyboard(view);
        return view;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rsaViewModel = ViewModelProviders.of(getActivity()).get(RSAViewModel.class);

        disposable.add(rsaViewModel.computeRSA()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fillValues,
                        throwable -> SnackbarUtil.showSnackbar(getView(), getString(R.string.snackbar_error), getString(R.string.snackbar_action)))
        );

        if (getActivity() instanceof OnClickHomeListener) {
            listener = (OnClickHomeListener) getActivity();
            buttonHome.setOnClickListener(v -> listener.onClickHome());
        }
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
        listener = null;
        disposable.clear();
        super.onDestroyView();
    }

    private void fillValues(RSA rsa) {
        pValue.setText(rsa.pToString());
        qValue.setText(rsa.qToString());
        nValue.setText(rsa.nToString());
        zValue.setText(rsa.zToString());
        dValue.setText(rsa.dToString());
        eValue.setText(rsa.eToString());
        encryptedValue.setText(rsa.encryptedText());
        decryptedValue.setText(rsa.decryptedText());
    }

    private void hideKeyboard(View view) {
        InputMethodManager input = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}

