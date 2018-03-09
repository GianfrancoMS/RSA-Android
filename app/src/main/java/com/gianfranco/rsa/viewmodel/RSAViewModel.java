package com.gianfranco.rsa.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.gianfranco.rsa.model.RSA;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RSAViewModel extends ViewModel {

    private int keySize;
    private String message;

    private final MutableLiveData<RSA> rsaMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProgressStatus> progressStatusMutableLiveData = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public enum ProgressStatus {
        LOADING, ERROR, COMPLETED
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LiveData<RSA> getRSA() {
        return rsaMutableLiveData;
    }

    public LiveData<ProgressStatus> getProgressStatus() {
        return progressStatusMutableLiveData;
    }

    public void computeRSA() {
        compositeDisposable.add(Single.fromCallable(() -> new RSA.Builder(keySize, message)
                .encodeMessage()
                .decodeMessage()
                .build())
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(disposable -> progressStatusMutableLiveData.postValue(ProgressStatus.LOADING))
                .subscribe(rsa -> {
                            rsaMutableLiveData.postValue(rsa);
                            progressStatusMutableLiveData.postValue(ProgressStatus.COMPLETED);
                        }, throwable -> progressStatusMutableLiveData.postValue(ProgressStatus.ERROR)
                ));
    }

}
