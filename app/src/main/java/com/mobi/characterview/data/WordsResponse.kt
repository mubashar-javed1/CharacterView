package com.mobi.characterview.data

import com.google.gson.annotations.SerializedName

class WordsResponse {

    @SerializedName("RelatedTopics")
    var list: List<Topic>? = null
}