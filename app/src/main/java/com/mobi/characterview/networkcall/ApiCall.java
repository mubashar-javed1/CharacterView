package com.mobi.characterview.networkcall;

import com.mobi.characterview.BuildConfig;
import com.mobi.characterview.data.WordsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCall {
    @GET(BuildConfig.loadCharactersUrl)
    Observable<WordsResponse> getWords();
}