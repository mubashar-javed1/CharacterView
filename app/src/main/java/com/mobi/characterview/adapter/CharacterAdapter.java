package com.mobi.characterview.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.characterview.R;
import com.mobi.characterview.data.Topic;
import com.mobi.characterview.ui.masterdetailactivity.ItemDetailActivity;
import com.mobi.characterview.ui.masterdetailactivity.MasterDetailActivity;
import com.mobi.characterview.viewmodel.DataSharingViewModel;

import java.util.ArrayList;
import java.util.List;

import android.widget.Filter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ItemViewHolder> implements Filterable {

    private final MasterDetailActivity mParentActivity;
    private final List<Topic> mValues;
    private final List<Topic> originaList;
    private final boolean mTwoPane;
    private DataSharingViewModel viewModel;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mTwoPane) {
                viewModel.postTopic((Topic) view.getTag());
            } else {
                Intent intent = new Intent(mParentActivity, ItemDetailActivity.class);
                intent.putExtra("Topic", (Topic) view.getTag());
                mParentActivity.startActivity(intent);
            }
        }
    };

    public CharacterAdapter(MasterDetailActivity parent,
                            List<Topic> items,
                            boolean twoPane) {
        mValues = items;
        originaList = new ArrayList<>(items);
        mParentActivity = parent;
        mTwoPane = twoPane;
        this.viewModel = ViewModelProviders.of(mParentActivity).get(DataSharingViewModel.class);
        if (mTwoPane && !mValues.isEmpty()) {
            viewModel.postTopic(mValues.get(0));
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String[] info = mValues.get(position).getText().split("- ");
        holder.tvTitle.setText(info[0]);
        holder.tvContent.setText(info[1]);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Topic> tempList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                tempList.addAll(originaList);
            } else {
                for (Topic topic : originaList) {
                    if (topic.getText().toLowerCase().contains(charSequence)) {
                        tempList.add(topic);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mValues.clear();
            mValues.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}