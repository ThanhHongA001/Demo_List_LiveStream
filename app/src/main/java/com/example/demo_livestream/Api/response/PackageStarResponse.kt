package com.viettel.mocha.rmlivestream.network.response

import com.google.gson.annotations.SerializedName
import com.viettel.mocha.rmlivestream.model.PackageStar
import java.io.Serializable

class PackageStarResponse : Serializable {
    @SerializedName("data")
    var data = ArrayList<PackageStar>()
}