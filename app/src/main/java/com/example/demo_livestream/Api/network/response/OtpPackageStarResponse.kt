package com.viettel.mocha.rmlivestream.network.response

import com.google.gson.annotations.SerializedName

class OtpPackageStarResponse {
    @SerializedName("requestIdCp")
    var requestIdCp = ""
    @SerializedName("code")
    var code = 0
    @SerializedName("message")
    var message = ""
}