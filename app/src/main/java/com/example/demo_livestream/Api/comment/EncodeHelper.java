package com.example.demo_livestream.Api.comment;

import com.viettel.mocha.app.ApplicationController;
import com.viettel.mocha.helper.httprequest.HttpHelper;

public class EncodeHelper {
    public static String getSecurity(String stringToEncode, long timestamp){
        return HttpHelper
                .encryptDataV2(ApplicationController
                        .self(),ApplicationController
                        .self().getReengAccountBusiness().getPhoneNumberLogin()
                        + stringToEncode
                        + ApplicationController.self().getReengAccountBusiness().getToken()
                        + timestamp, ApplicationController.self().getReengAccountBusiness().getToken());
    }
}
