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
    public final static String TAG = InputPlainTextFragment.class.getCanonicalName();

    private EditText editTextMessage;
    private Snackbar snackbar;
    private Button buttonNext;

    private OnMessageListener listener;

    @FunctionalInterface
    public interface OnMessageListener {
        void onMessageEntered(String message);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_plain_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextMessage = view.findViewById(R.id.et_message);
        buttonNext = view.findViewById(R.id.btn_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof OnMessageListener) {
            listener = (OnMessageListener) getActivity();
            buttonNext.setOnClickListener(view -> listener.onMessageEntered(editTextMessage.getText().toString()));
        }
    }

    @Override
    public void onDestroyView() {
        editTextMessage = null;
        buttonNext = null;
        listener = null;
        super.onDestroyView();
    }
}
