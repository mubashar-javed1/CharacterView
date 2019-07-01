package com.mobi.characterview.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    protected void setUpHome() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, message.length() > 30 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    protected boolean isValid(EditText editText) {
        editText.setError(null);
        if (getString(editText).isEmpty()) {
            editText.setError("Field Required");
            return false;
        }
        return true;
    }

    protected boolean isValid(EditText... editTexts) {
        boolean valid = true;
        for (EditText editText : editTexts) {
            if (isValid(editText)) {
                valid = false;
            }
        }
        return valid;
    }

    protected String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    protected boolean checkNetworkAvailability() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}