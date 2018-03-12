package com.gianfranco.rsa.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gianfranco.rsa.R;
import com.gianfranco.rsa.view.SnackbarUtil;
import com.gianfranco.rsa.viewmodel.RSAViewModel;

public class InputPlainTextFragment extends Fragment {
    public final static String TAG = InputPlainTextFragment.class.getCanonicalName();

    private EditText editTextMessage;
    private Button buttonNext;

    private OnMessageListener listener;

    private RSAViewModel rsaViewModel;

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

        rsaViewModel = ViewModelProviders.of(getActivity()).get(RSAViewModel.class);

        editTextMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(rsaViewModel.getMaxSizeMessage())});

        if (getActivity() instanceof OnMessageListener) {
            listener = (OnMessageListener) getActivity();
            buttonNext.setOnClickListener(view -> {

                String message = editTextMessage.getText().toString();

                rsaViewModel.setMessage(message).subscribe(() -> listener.onMessageEntered(message),
                        throwable -> SnackbarUtil.showSnackbar(view, throwable.getMessage(), getString(R.string.snackbar_action)));
            });
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
