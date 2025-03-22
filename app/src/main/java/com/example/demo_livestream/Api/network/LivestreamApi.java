package com.example.demo_livestream.Api.network;

import com.viettel.mocha.app.ApplicationController;
import com.viettel.mocha.common.api.BaseApi;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.database.model.ReengAccount;
import com.viettel.mocha.helper.encrypt.EncryptUtil;
import com.viettel.mocha.helper.httprequest.HttpHelper;

public class LivestreamApi extends BaseApi {
    private String DOMAIN_LIVESTREAM = "https://mytelapi-live.ringme.vn";
    public static final String LIVESTREAM_LIST = "/LivestreamAPI/v2/my_livestream/all"; //"/LivestreamAPI/v1/my_livestream/all";
    public static final String LIVESTREAM_FOLLOW_CHANNEL = "/LivestreamAPI/v1/live_social/follow";
    public static final String LIVESTREAM_LIKE = "/LivestreamAPI/v1/live_social/like";
    public static final String LIVESTREAM_REPORT = "/LivestreamAPI/v1/report_livestream";
    public static final String LIVESTREAM_LIST_CHANNEL = "/LivestreamAPI/v1/channel/list/v2";
    public static final String LIVESTREAM_GET_DETAIL = "/LivestreamAPI/v2/my_livestream/details";
    public static final String LIVESTREAM_DELETE_COMMENT = "/LivestreamAPI/v1/social/comment/delete";
    public static final String LIVESTREAM_REACTION_COMMENT = "/LivestreamAPI/v1/live_social/react_comment";
    public static final String LIVESTREAM_LIST_REACTION_USER = "/LivestreamAPI/v1/live_social/list_reacts_user";
    public static final String LIVESTREAM_LIST_STAR_PACKAGE = "/LivestreamAPI/v1/gift/list-level-star";
    public static final String LIVESTREAM_LIST_DONATE = "/LivestreamAPI/v2/donate/list-package-gift";
    public static final String LIVESTREAM_SEND_STAR = "/LivestreamAPI/v2/donate";
    public static final String LIVESTREAM_STAR_WALLET = "/LivestreamAPI/v2/donate/get-current-star-user";
    public static final String LIVESTREAM_RECEIVE_STAR = "/LivestreamAPI/v1/donate/receive-star";
    public static final String LIVESTREAM_TOP_DONATE = "/LivestreamAPI/v1/donate/list-donate/top";
    public static final String LIVESTREAM_TOP_DONATE_LIVESTREAM = "/LivestreamAPI/v2/donate/get-top-donate-in-livestream";
    public static final String LIVESTREAM_RECHARGE_STAR = "/LivestreamAPI/v2/donate/buy-star";  ///LivestreamAPI/v1/donate/recharge-star
    public static final String LIVESTREAM_REGISTER_NOTIFY = "/LivestreamAPI/api/v1/notification";
    public static final String LIVESTREAM_GET_LIST_VOTE = "/LivestreamAPI/v1/vote/list-all";
    public static final String LIVESTREAM_APPLY_VOTE = "/LivestreamAPI/v1/vote/voted";
    public static final String LIVESTREAM_LIST_PACKAGE_START = "/LivestreamAPI/v1/buy-star/pack_mps/list";
    public static final String LIVESTREAM_LIST_BUY_START_OTP = "/LivestreamAPI/v1/buy-star/pack_mps/reqOTP";
    public static final String LIVESTREAM_LIST_BUY_START_VALIDATE = "/LivestreamAPI/v1/buy-star/pack_mps/inputOTP";
    public static final String LIVESTREAM_BLOCK_USER_COMMENT = "/LivestreamAPI/v1/report_comment/cms";
    public static final String LIVESTREAM_LIST_TOP = "/LivestreamAPI/v2/my_livestream/all";
    public static final String FOLLOW_CHANNEL = "/LivestreamAPI/v1/channel/{id}/follow";
    public static final String UNFOLLOW_CHANNEL = "/LivestreamAPI/v1/channel/{id}/unfollow";
    public static final String GET_VIDEO_IN_CHANNEL = "/LivestreamAPI/v2/video/{id}/channel/v2";
    public static final String LIST_LIVESTREAM = "/LivestreamAPI/v2/my_livestream/all";
    public static final String VIDEO_SEARCH = "/LivestreamAPI/v1/search/livestream";
    public static final String CHANNEL_SEARCH = "/LivestreamAPI/v1/search/channel";
    public static final String LIVESTREAM_REPORT_COMMENT = "/LivestreamAPI/v1/social/comment/report";
    public static final String LIVESTREAM_DELETE_REPLY = "/LivestreamAPI/v1/comment/reply/delete";
    public static final String LIVESTREAM_GET_LIST_COMMENT = "/LivestreamAPI/v1/social/list-comment";
    public static final String LIVESTREAM_POST_COMMENT = "/LivestreamAPI/v1/social/comment";
    public static final String LIVESTREAM_POST_LIKE = "/LivestreamAPI/v1/comment/like";
    public static final String LIVESTREAM_GET_LIST_REPLY = "/LivestreamAPI/v1/comment/list/reply";
    public static final String LIVESTREAM_POST_REPLY = "/LivestreamAPI/v1/comment/reply";
    public static final String LIKE_VIDEO = "/LivestreamAPI/v1/social/like";
    public static final String LIVESTREAM_DELETE_CMT_IN_LIVE="/LivestreamAPI/v1/report_comment/delete";
    public static final String HISTORY_DONATE="/LivestreamAPI/v2/donate/get-history-transaction";
    public static final String GET_TOP_DONATE_FILTER="/LivestreamAPI/v2/donate/get-top-donate-channel";



    private static LivestreamApi mInstance;

    public LivestreamApi() {
        super(ApplicationController.self());
    }

    public static LivestreamApi getInstance() {
        if (mInstance == null) mInstance = new LivestreamApi();
        return mInstance;
    }

    public void getListChannel(int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_CHANNEL)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .withCallBack(httpCallBack).execute();
    }

    public void getListLivestream(int featureId, int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_TOP)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("featureId", String.valueOf(featureId))
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .withCallBack(httpCallBack).execute();
    }

    public void getLivestreamDetail(String id, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_GET_DETAIL)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("id", id)
                .withCallBack(httpCallBack).execute();
    }

    public void reactionLive(String videoId, String streamerId, int reactId, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_LIKE)
                .putParameter("livestreamId", videoId)
                .putParameter("streamerId", streamerId)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("reactId", String.valueOf(reactId))
                .withCallBack(httpCallBack)
                .execute();
    }

    public void reactionComment(int type, String smsgId, String action, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_REACTION_COMMENT)
                .putParameter("type", String.valueOf(type))
                .putParameter("smsgId", smsgId)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("action", action)
                .withCallBack(httpCallBack).execute();
    }

    public void getListChannelLivestream(int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_CHANNEL)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .withCallBack(httpCallBack).execute();
    }


    public void followChannel(String channelId, HttpCallBack httpCallBack) {
        String time = System.currentTimeMillis() + "";
        String security = "";

        String stringUrl = FOLLOW_CHANNEL.replace("{id}", channelId);
        get(DOMAIN_LIVESTREAM, stringUrl)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("timestamp", time)
                .putParameter("security", security)
                .withCallBack(httpCallBack).execute();
    }

    public void unFollowChannel(String channelId, HttpCallBack httpCallBack) {
        String time = System.currentTimeMillis() + "";
        String security = "";

        String stringUrl = UNFOLLOW_CHANNEL.replace("{id}", channelId);
        get(DOMAIN_LIVESTREAM, stringUrl)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("timestamp", time)
                .putParameter("security", security)
                .withCallBack(httpCallBack).execute();
    }

    public void getChannelInfo(String id, HttpCallBack httpCallBack) {
        long timestamp = System.currentTimeMillis();
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        String security = HttpHelper.encryptDataV2(ApplicationController.self(), msisdn + timestamp + "RINGME" + timestamp, "RINGME");


        get(DOMAIN_LIVESTREAM, "/LivestreamAPI/v1/channel/" + id + "/info")
                .putHeader("Accept-language", "en")
                .putParameter("msisdn", msisdn)
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .withCallBack(httpCallBack)
                .execute();
    }

    public void getVideoByChannel(String id, String lastHashId, int page, int size, HttpCallBack httpCallBack) {
        long timestamp = System.currentTimeMillis();
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        String security = HttpHelper.encryptDataV2(ApplicationController.self(), msisdn + timestamp + "RINGME" + timestamp, "RINGME");
        String stringUrl = GET_VIDEO_IN_CHANNEL.replace("{id}", id);

        get(DOMAIN_LIVESTREAM, stringUrl)
                .putHeader("Accept-language", "en")
                .putParameter("msisdn", msisdn)
                .putParameter("lastHashId", lastHashId)
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .withCallBack(httpCallBack)
                .execute();
    }


    public void getListLiveStream(int featureId, int page, int size, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        get(DOMAIN_LIVESTREAM, LIST_LIVESTREAM)
                .putHeader("Accept-language", "en")
                .putParameter("userId", msisdn)
                .putParameter("featureId", String.valueOf(featureId))
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .withCallBack(httpCallBack)
                .execute();
    }


    public void registerNotifyLivestream(String livestreamId, long time, int type, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        String token = "RINGME";
        post(DOMAIN_LIVESTREAM, LIVESTREAM_REGISTER_NOTIFY)
                .putParameter("userId", msisdn)
                .putParameter("livestreamId", livestreamId)
                .putParameter("type", String.valueOf(type))
                .putParameter("timeNotification", String.valueOf(time))
                .putHeader("Token", token)
                .withCallBack(httpCallBack).execute();
    }


    public void searchVideo(int page, int size, String query, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        long timestamp = System.currentTimeMillis();
        String security = "";
        get(DOMAIN_LIVESTREAM, VIDEO_SEARCH)
                .putParameter("userId", msisdn)
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putParameter("q", query)
                .putParameter("lastHashId", "")
                .putHeader("Accept-language", "en")
                .withCallBack(httpCallBack).execute();
    }

    public void searchChannel(int page, int size, String query, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        long timestamp = System.currentTimeMillis();
        String security = "";
        get(DOMAIN_LIVESTREAM, CHANNEL_SEARCH)
                .putParameter("userId", msisdn)
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putParameter("q", query)
                .putHeader("Accept-language", "vi")
                .withCallBack(httpCallBack).execute();
    }

    public void getTopDonateLivestream(String streamerId, String livestreamId, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_TOP_DONATE_LIVESTREAM)
                .putParameter("channelId", streamerId)
                .putParameter("livestreamId", livestreamId)
                .withCallBack(httpCallBack).execute();
    }

    public void getListDonate(HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        get(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_DONATE)
                .withCallBack(httpCallBack)
                .execute();
    }

    public void getListPackageStart(HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        get(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_PACKAGE_START)
                .withCallBack(httpCallBack)
                .execute();
    }

    public void sendStar(String message, String giftId, String streamerId, String livestreamId, HttpCallBack httpCallBack) {
        long timestamp = System.currentTimeMillis();
        ReengAccount account = ApplicationController.self().getReengAccountBusiness().getCurrentAccount();
        String avatarUrl = ApplicationController.self().getAvatarBusiness().getAvatarUrl(account.getLastChangeAvatar(), ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin(), 70);
        String md5 = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin() + ":" + streamerId + ":" + livestreamId + ":" + giftId + ":" + "RINGME" + ":" + timestamp;
        String security = EncryptUtil.encryptMD5(md5);

        post(DOMAIN_LIVESTREAM, LIVESTREAM_SEND_STAR)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("name", account.getName())
                .putParameter("avatar", avatarUrl)
                .putParameter("channelId", streamerId)
                .putParameter("livestreamId", livestreamId)
                .putParameter("giftId", giftId)
                .putParameter("timestamp", "" + timestamp)
                .putParameter("tokenMd5", security)
                .putParameter("message", message)
                .withCallBack(httpCallBack).execute();
    }

    public void getUserStarNumber(HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_STAR_WALLET)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .withCallBack(httpCallBack).execute();

    }

    public void buyPackageStartOTP(String code, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        String md5 = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin() + ":" + code + ":" + ApplicationController.self().getReengAccountBusiness().getToken();
        String security = EncryptUtil.encryptMD5(md5);

//        BuyStarOtpRequest request = new BuyStarOtpRequest(msisdn, code, security);
        post(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_BUY_START_OTP)
//                .putBody(request)
//                .putHeader("Content-type","application/x-www-form-urlencoded")
                .putParameter("msisdn", msisdn)
                .putParameter("code", code)
                .putParameter("tokenMd5", security)
                .withCallBack(httpCallBack)
                .execute();
    }

    public void buyPackageStartValidate(String code, String otp, String requestId, HttpCallBack httpCallBack) {
        String msisdn = ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin();
        ReengAccount account = ApplicationController.self().getReengAccountBusiness().getCurrentAccount();
        String security = EncryptUtil.encryptMD5(ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin() + ":" + code + ":" + ApplicationController.self().getReengAccountBusiness().getToken());
        String avatarUrl = ApplicationController.self().getAvatarBusiness().getAvatarUrl(account.getLastChangeAvatar(), ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin(), 70);

//        BuyStarValidateRequest request = new BuyStarValidateRequest(msisdn, code, otp,requestId, security);
        post(DOMAIN_LIVESTREAM, LIVESTREAM_LIST_BUY_START_VALIDATE)
                .putParameter("msisdn", msisdn)
                .putParameter("code", code)
                .putParameter("requestIdCp", requestId)
                .putParameter("name", ApplicationController.self().getReengAccountBusiness().getUserName())
                .putParameter("avatar", avatarUrl)
                .putParameter("otp", otp)
                .putParameter("tokenMd5", security)
                .withCallBack(httpCallBack)
                .execute();
    }

    public void getListVoteLivestream(String livestreamId, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_GET_LIST_VOTE)
                .putParameter("liveStreamId", livestreamId)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putHeader("Content-Type", "application/json")
                .putHeader("Authorization", ApplicationController.self().getReengAccountBusiness().getToken())
                .withCallBack(httpCallBack).execute();
    }

    public void applyVote(String livestreamId, long surveyId, long voteId, int type, long voteNumber, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_APPLY_VOTE)
                .putParameter("liveStreamId", livestreamId)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("surveyId", String.valueOf(surveyId))
                .putParameter("voteId", String.valueOf(voteId))
                .putParameter("type", String.valueOf(type))
                .putParameter("numberVote", String.valueOf(voteNumber))
                .putHeader("Content-Type", "application/json")
                .putHeader("Authorization", ApplicationController.self().getReengAccountBusiness().getToken())
                .withCallBack(httpCallBack).execute();
    }

    public void deleteCommentLivestream(String id, String livestreamId, String streamerId, String avatar, String userName, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_DELETE_CMT_IN_LIVE)
                .putParameter("blockId", "3")
                .putParameter("commentId", id)
                .putParameter("livestreamId", livestreamId)
                .putParameter("streamerId", streamerId)
                .putParameter("userId", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("avatar", avatar)
                .putParameter("userName", userName)
                .withCallBack(httpCallBack).execute();
    }

    public void blockUserCommentLivestream(String id, String livestreamId, String streamerId, String avatar, String userName,String userId, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_BLOCK_USER_COMMENT)
                .putParameter("blockId", "5")
                .putParameter("commentId", id)
                .putParameter("livestreamId", livestreamId)
                .putParameter("streamerId", streamerId)
                .putParameter("userId",userId)
                .putParameter("avatar", avatar)
                .putParameter("userName", userName)
                .withCallBack(httpCallBack).execute();
    }

    public void postReportComment(String commentId, String content, String problem, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_REPORT_COMMENT)
                .putParameter("commentId", commentId)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("content", content)
                .putParameter("problem", problem)
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void postDeleteComment(String commentId, String contentId, HttpCallBack httpCallBack) {
        post(DOMAIN_LIVESTREAM, LIVESTREAM_DELETE_COMMENT)
                .putParameter("commentId", commentId)
                .putParameter("contentId", contentId)
                .putHeader("Accept-language", "VN")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void postDeleteReply(String id, String commentReplyId, String contentId, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_DELETE_REPLY)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("id_reply", id)
                .putParameter("id_comment", commentReplyId)
                .putParameter("contentId", contentId)
                .putHeader("Accept-language", "VN")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void getListComment(String contentId, int page, int size, String security, HttpCallBack httpCallBack) {
        long timestamp = System.currentTimeMillis();

        get(DOMAIN_LIVESTREAM, LIVESTREAM_GET_LIST_COMMENT)
                .putParameter("contentId", contentId)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void postCommentVideo(String content, String contentId, String userInfo, String videoInfo, String channelInfo, HttpCallBack httpCallBack) {
        long timestamp = System.currentTimeMillis();
        String security = HttpHelper
                .encryptDataV2(ApplicationController
                        .self(), ApplicationController
                        .self().getReengAccountBusiness().getPhoneNumberLogin()
                        + ""
                        + ApplicationController.self().getReengAccountBusiness().getToken()
                        + timestamp, ApplicationController.self().getReengAccountBusiness().getToken());

        post(DOMAIN_LIVESTREAM, LIVESTREAM_POST_COMMENT)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("timestamp", String.valueOf(timestamp))
                .putParameter("security", security)
                .putParameter("content", content)
                .putParameter("contentId", contentId)
                .putParameter("userInfo", userInfo)
                .putParameter("videoInfo", videoInfo)
                .putParameter("channelInfo", channelInfo)
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void postLikeCmt(String commentId, int level, int status, int old_status, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_POST_LIKE)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("commentId", commentId)
                .putParameter("level", String.valueOf(level))
                .putParameter("status", String.valueOf(status))
                .putParameter("old_status", String.valueOf(old_status))
                .putHeader("Accept-language", "VN")
                .putHeader("sec-api", "")
                .withCallBack(httpCallBack).execute();
    }

    public void getListReply(String commentId, int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_GET_LIST_REPLY)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("commentId", commentId)
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void postReply(String commentId, String content, String contentId, String userInfo, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, LIVESTREAM_POST_REPLY)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("commentId", commentId)
                .putParameter("content", content)
                .putParameter("contentId", contentId)
                .putParameter("userInfo", userInfo)
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void likeVideo(String videoId,String action, HttpCallBack httpCallBack) {
        String timestamp = System.currentTimeMillis() + "";
        post(DOMAIN_LIVESTREAM, LIKE_VIDEO)
                .putParameter("msisdn", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("action", action)
                .putParameter("videoId", videoId)
                .putParameter("timestamp", timestamp)
                .putParameter("security", "")
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void getHistoryDonate(int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, HISTORY_DONATE)
                .putParameter("user-id", ApplicationController.self().getReengAccountBusiness().getPhoneNumberLogin())
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putParameter("type", null)
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .withCallBack(httpCallBack).execute();
    }


    public void getTopDonateFilter(String channelId,String filter,int page, int size, HttpCallBack httpCallBack) {
        get(DOMAIN_LIVESTREAM, GET_TOP_DONATE_FILTER)
                .putParameter("channel-id", channelId)
                .putParameter("type", filter)
                .putParameter("page", String.valueOf(page))
                .putParameter("size", String.valueOf(size))
                .putHeader("Accept-language", "VN")
                .putHeader("mocha-api", "")
                .withCallBack(httpCallBack).execute();
    }


}
