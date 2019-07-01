package com.mobi.characterview.ui.masterdetailactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobi.characterview.R;
import com.mobi.characterview.viewmodel.DataSharingViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ItemDetailFragment extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_detail)
    TextView tvDetail;


    private DataSharingViewModel viewModel;


    private Unbinder unbinder;

    public ItemDetailFragment() {
        // empty constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DataSharingViewModel.class);
        viewModel.getSharedTopic().observe(this, topic -> {
            String[] data = topic.getText().split("- ");
            tvTitle.setText(data[0]);
            tvContent.setText(data[1]);

            tvDetail.setText(topic.getResult());

            Glide.with(Objects.requireNonNull(getActivity()))
                    .setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_photo))
                    .load(topic.getIcon().getUrl()).into(ivIcon);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
