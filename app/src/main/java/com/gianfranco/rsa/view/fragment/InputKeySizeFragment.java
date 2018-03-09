package com.gianfranco.rsa.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gianfranco.rsa.R;

public class InputKeySizeFragment extends Fragment {

    private Spinner keySizeSpinner;
    private Button buttonNext;

    private OnKeySizeListener listener;

    public static InputKeySizeFragment newInstance(OnKeySizeListener listener) {
        InputKeySizeFragment fragment = new InputKeySizeFragment();
        fragment.setOnKeySizeListener(listener);
        return fragment;
    }

    @FunctionalInterface
    public interface OnKeySizeListener {
        void onKeySizeSelected(int keySize);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_key_size, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keySizeSpinner = view.findViewById(R.id.spinner_key_size);
        buttonNext = view.findViewById(R.id.btn_next);

        Integer[] bits = new Integer[]{64, 128, 256, 512, 1024, 2048};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, bits);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        keySizeSpinner.setAdapter(adapter);

        buttonNext.setOnClickListener(v -> {
            int keySize = (Integer) keySizeSpinner.getSelectedItem();
            listener.onKeySizeSelected(keySize);
        });
    }

    @Override
    public void onDestroyView() {
        keySizeSpinner = null;
        buttonNext = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void setOnKeySizeListener(OnKeySizeListener listener) {
        this.listener = listener;
    }

}
