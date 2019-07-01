package com.mobi.characterview.ui.masterdetailactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.mobi.characterview.R;
import com.mobi.characterview.data.Topic;
import com.mobi.characterview.ui.BaseActivity;
import com.mobi.characterview.viewmodel.DataSharingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DataSharingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setUpHome();

        this.viewModel = ViewModelProviders.of(this).get(DataSharingViewModel.class);

        if (savedInstanceState == null) {
            ItemDetailFragment fragment = new ItemDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
            Topic topic = getIntent().getParcelableExtra("Topic");
            viewModel.postTopic(topic);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MasterDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}