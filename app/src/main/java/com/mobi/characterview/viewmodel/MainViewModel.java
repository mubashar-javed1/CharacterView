package com.mobi.characterview.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobi.characterview.data.Topic;
import com.mobi.characterview.networkcall.ApiResponse;
import com.mobi.characterview.repository.Repository;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Topic>> copyData = new MutableLiveData<>();

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getWords() {
        disposables.add(repository.getWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.responseError(throwable))
                ));
    }

    public void performSearch (String search) {
        if (search.isEmpty()) {
            copyData.setValue(Objects.requireNonNull(Objects.requireNonNull(responseLiveData.getValue()).data).getList());
            return;
        }

        // implement here search function
    }

    public MutableLiveData< List<Topic>> getSearchedDate() {
        return copyData;
    }

    public MutableLiveData<ApiResponse> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}