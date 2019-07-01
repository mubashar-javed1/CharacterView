package com.mobi.characterview.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobi.characterview.data.Topic;

public class DataSharingViewModel extends ViewModel {

    private MutableLiveData<Topic> sharedTopic = new MutableLiveData<>();

    public DataSharingViewModel() {
        // empty constructor
    }

    public void postTopic (Topic topic) {
        sharedTopic.setValue(topic);
    }

    public LiveData<Topic> getSharedTopic() {
        return sharedTopic;
    }
}
