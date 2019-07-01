package com.mobi.characterview.ui.masterdetailactivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.characterview.MyApplication;
import com.mobi.characterview.R;
import com.mobi.characterview.adapter.CharacterAdapter;
import com.mobi.characterview.data.WordsResponse;
import com.mobi.characterview.networkcall.ApiResponse;
import com.mobi.characterview.ui.BaseActivity;
import com.mobi.characterview.viewmodel.MainViewModel;
import com.mobi.characterview.viewmodel.ViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class MasterDetailActivity extends BaseActivity{

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_character)
    RecyclerView rvCharacter;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;


    private boolean mTwoPane;
    private MainViewModel viewModel;
    private CharacterAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        setToolbar(toolbar);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            Fragment fragment = new ItemDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }

        ((MyApplication) getApplication()).getAppComponent().injectMasterActivity(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getResponseLiveData().observe(this, this::consumeResponse);

        handleIntent(getIntent());

        if (savedInstanceState == null) {
            viewModel.getWords();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        Observable.create((ObservableOnSubscribe<String>)
                emitter -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                emitter.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                emitter.onNext(newText);
                return true;
            }
        })).map(text -> text.toLowerCase().trim())
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.contains("-"))
                .subscribe(text -> adapter.getFilter().filter(text));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    private void consumeResponse(ApiResponse response) {
        switch (response.status) {
            case LOADING:
                showProgressBar();
                break;

            case SUCCESS:
                assert response.data != null;
                updateRecyclerView(response.data);
                break;

            case ERROR:
                if (response.error != null) {
                    if (!checkNetworkAvailability()) {
                        showStatusText(getString(R.string.network_error));
                        return;
                    }
                    showStatusText(response.error.getMessage());
                }
                break;

            default:

        }
    }

    public void updateRecyclerView(WordsResponse data) {
        if (data != null && !data.getList().isEmpty()) {
            showMainView();
            adapter = new CharacterAdapter(this, data.getList(), mTwoPane);
            rvCharacter.setAdapter(adapter);


            if (!checkNetworkAvailability()) {
                showStatusText(getString(R.string.cache));
                frameLayout.setVisibility(View.VISIBLE);
            }
        } else {
            showStatusText(getString(R.string.no_result_found));
        }
    }


    protected void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
    }

    protected void showStatusText(String text) {
        progressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(text);
    }

    protected void showMainView() {
        progressBar.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }
}