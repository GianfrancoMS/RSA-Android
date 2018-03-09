package com.gianfranco.rsa.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gianfranco.rsa.R;

public class InputPlainTextFragment extends Fragment {

    private EditText editTextMessage;
    private Snackbar snackbar;
    private Button buttonNext;

    private OnMessageListener listener;

    public static InputPlainTextFragment newInstance(OnMessageListener listener) {
        InputPlainTextFragment fragment = new InputPlainTextFragment();
        fragment.setMessageListener(listener);
        return fragment;
    }

    @FunctionalInterface
    public interface OnMessageListener {
        void onMessageEntered(String message);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_plain_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextMessage = view.findViewById(R.id.et_message);
        buttonNext = view.findViewById(R.id.btn_details);
        buttonNext.setOnClickListener(v -> listener.onMessageEntered(editTextMessage.getText().toString()));
    }

    @Override
    public void onDestroyView() {
        editTextMessage = null;
        buttonNext = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void setMessageListener(OnMessageListener listener) {
        this.listener = listener;
    }
}
