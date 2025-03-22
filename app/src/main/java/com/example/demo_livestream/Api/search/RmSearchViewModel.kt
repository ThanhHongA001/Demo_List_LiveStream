package com.viettel.mocha.rmlivestream.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.util.Log

class RmSearchViewModel: ViewModel() {
    var keySearch = MutableLiveData<String>()

    fun updateData(newData: String) {
        keySearch.postValue(newData)
    }
}