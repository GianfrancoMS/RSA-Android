package com.gianfranco.rsa.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.gianfranco.rsa.model.RSA;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RSAViewModel extends ViewModel {

    private int keySize;
    private String message;

    public Completable setKeySize(int keySize) {
        return Completable.fromAction(() -> {

            int BITS = 8;

            if (keySize <= BITS - 1)
                throw new IllegalArgumentException("Key size is too short");

            if (keySize % 8 != 0)
                throw new IllegalArgumentException("Key size is not multiple of 8");

            this.keySize = keySize;
        });
    }

    public Completable setMessage(String message) {
        return Completable.fromAction(() -> {

            if (message == null)
                throw new NullPointerException("Message is null");

            if (isMessageEmpty(message))
                throw new IllegalArgumentException("Message is empty");

            if (isMessageTrimEmpty(message))
                throw new IllegalArgumentException("Message contains only whitespaces");

            this.message = message;
        });
    }

    public int getMaxSizeMessage() {
        return keySize / 8;
    }

    public Single<RSA> computeRSA() {
        return Single.fromCallable(() -> new RSA.Builder(keySize, message)
                .encodeMessage()
                .decodeMessage()
                .build());
    }

    private boolean isMessageEmpty(String message) {
        return message.length() == 0;
    }

    private boolean isMessageTrimEmpty(String message) {
        return message.trim().length() == 0;
    }
}
