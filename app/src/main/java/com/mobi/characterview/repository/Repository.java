package com.mobi.characterview.repository;

import com.mobi.characterview.data.WordsResponse;
import com.mobi.characterview.networkcall.ApiCall;

import io.reactivex.Observable;

public class Repository {
    private ApiCall apiCall;

    public Repository(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    public Observable<WordsResponse> getWords() {
        return apiCall.getWords();
    }
}