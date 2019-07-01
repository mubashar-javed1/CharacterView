package com.mobi.characterview.networkcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobi.characterview.data.WordsResponse;
import com.mobi.characterview.enums.Status;

import static com.mobi.characterview.enums.Status.ERROR;
import static com.mobi.characterview.enums.Status.LOADING;
import static com.mobi.characterview.enums.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final WordsResponse data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable WordsResponse data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull WordsResponse data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse responseError(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }
}