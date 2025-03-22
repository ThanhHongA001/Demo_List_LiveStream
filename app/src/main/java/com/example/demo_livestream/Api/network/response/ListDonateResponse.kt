package com.viettel.mocha.rmlivestream.network.response

import com.google.gson.annotations.SerializedName
import com.viettel.mocha.rmlivestream.model.Donate
import java.io.Serializable
import java.util.ArrayList

class ListDonateResponse: Serializable {
    @SerializedName("data")
    var data = ArrayList<Donate>()
}