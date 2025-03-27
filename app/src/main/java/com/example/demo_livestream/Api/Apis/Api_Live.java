package com.example.demo_livestream.Api.Apis;

public class Api_Live {
    public static final String Domain_LiveStream = "https://mytelapi-live.ringme.vn";

    // 🟢 API Livestream
    public static final String LiveStream_List = "/LivestreamAPI/v2/my_livestream/all";
    public static final String LiveStream_List_Top = "/LivestreamAPI/v2/my_livestream/top";
    public static final String LiveStream_Get_Detail = "/LivestreamAPI/v2/my_livestream/details";
    public static final String LiveStream_Report = "/LivestreamAPI/v1/report_livestream";

    // 🟢 API Kênh (Channel)
    public static final String LiveStream_List_Channel = "/LivestreamAPI/v1/channel/list/v2";
    public static final String Follow_Channel = "/LivestreamAPI/v1/channel/{id}/follow";
    public static final String Unfollow_Channel = "/LivestreamAPI/v1/channel/{id}/unfollow";

    // 🟢 API Tìm kiếm
    public static final String Video_Search = "/LivestreamAPI/v1/search/livestream";
    public static final String Channel_Search = "/LivestreamAPI/v1/search/channel";

    // 🟢 API Bình luận (Comment)
    public static final String LiveStream_Get_List_Comment = "/LivestreamAPI/v1/social/list-comment";
    public static final String LiveStream_Post_Comment = "/LivestreamAPI/v1/social/comment";
    public static final String LiveStream_Post_Reply = "/LivestreamAPI/v1/comment/reply";
    public static final String LiveStream_Post_Like = "/LivestreamAPI/v1/comment/like";
    public static final String LiveStream_Get_List_Reply = "/LivestreamAPI/v1/comment/list/reply";
    public static final String LiveStream_Delete_Comment = "/LivestreamAPI/v1/social/comment/delete";
    public static final String LiveStream_Delete_Reply = "/LivestreamAPI/v1/comment/reply/delete";
    public static final String LiveStream_Report_Comment = "/LivestreamAPI/v1/social/comment/report";
    public static final String LiveStream_Reaction_Comment = "/LivestreamAPI/v1/live_social/react_comment";
    public static final String LiveStream_List_Reaction_User = "/LivestreamAPI/v1/live_social/list_reacts_user";    //Không Dùng
    public static final String LiveStream_Block_User_Comment = "/LivestreamAPI/v1/report_comment/cms";
    public static final String LiveStream_Delete_Cmt_In_Live = "/LivestreamAPI/v1/report_comment/delete";

    // 🟢 API Theo dõi & Tương tác
    public static final String LiveStream_Follow_Channel = "/LivestreamAPI/v1/live_social/follow";                  //Không Dùng
    public static final String LiveStream_Like = "/LivestreamAPI/v1/live_social/like";
    public static final String Like_Video = "/LivestreamAPI/v1/social/like";

    // 🟢 API Donate & Quà tặng
    public static final String LiveStream_List_Donate = "/LivestreamAPI/v2/donate/list-package-gift";
    public static final String LiveStream_Send_Star = "/LivestreamAPI/v2/donate";
    public static final String LiveStream_Star_Wallet = "/LivestreamAPI/v2/donate/get-current-star-user";
    public static final String LiveStream_Receive_Star = "/LivestreamAPI/v1/donate/receive-star";                   //Không Dùng
    public static final String LiveStream_Top_Donate = "/LivestreamAPI/v1/donate/list-donate/top";
    public static final String LiveStream_Top_Donate_LiveStream = "/LivestreamAPI/v2/donate/get-top-donate-in-livestream";
    public static final String LiveStream_Recharge_Star = "/LivestreamAPI/v2/donate/buy-star";                      //Không Dùng
    public static final String History_Donate = "/LivestreamAPI/v2/donate/get-history-transaction";
    public static final String Get_Top_Donate_Filter = "/LivestreamAPI/v2/donate/get-top-donate-channel";

    // 🟢 API Gói Star
    public static final String LiveStream_List_Star_Package = "/LivestreamAPI/v1/gift/list-level-star";             //Không Dùng
    public static final String LiveStream_List_Package_StarT = "/LivestreamAPI/v1/buy-star/pack_mps/list";
    public static final String LiveStream_List_Buy_Star_Otp = "/LivestreamAPI/v1/buy-star/pack_mps/reqOTP";//Không Dùng
    public static final String LiveStream_List_Buy_Star_Validate = "/LivestreamAPI/v1/buy-star/pack_mps/inputOTP";//Không Dùng

    // 🟢 API Vote
    public static final String LiveStream_Get_List_Vote = "/LivestreamAPI/v1/vote/list-all";
    public static final String LiveStream_Apply_Vote = "/LivestreamAPI/v1/vote/voted";

    // 🟢 API Video
    public static final String Get_Video_In_Channel = "/LivestreamAPI/v2/video/{id}/channel/v2";

    // 🟢 API Thông báo
    public static final String LiveStream_Register_Notify = "/LivestreamAPI/api/v1/notification";
    public static final String LiveStream_Get_List_Notification = "/LivestreamAPI/api/v1/notification/list";//Không Dùng
    public static final String LiveStream_Mark_Read_Notification = "/LivestreamAPI/api/v1/notification/mark-read";//Không Dùng
    public static final String LiveStream_Delete_Notification = "/LivestreamAPI/api/v1/notification/delete";//Không Dùng

    // 🟢 API Giao dịch
    public static final String LiveStream_Get_Transaction_History = "/LivestreamAPI/v1/transaction/history";//Không Dùng
    public static final String LiveStream_Check_Transaction_Status = "/LivestreamAPI/v1/transaction/status/{id}";//Không Dùng
    public static final String LiveStream_Refund_Transaction = "/LivestreamAPI/v1/transaction/refund/{id}";//Không Dùng

    // 🟢 API Tích hợp Xã hội
    public static final String LiveStream_Share_Live = "/LivestreamAPI/v1/social/share/livestream";//Không Dùng
    public static final String LiveStream_Share_Channel = "/LivestreamAPI/v1/social/share/channel";//Không Dùng
    public static final String LiveStream_Get_List_Followers = "/LivestreamAPI/v1/social/followers";//Không Dùng
    public static final String LiveStream_Get_List_Following = "/LivestreamAPI/v1/social/following";//Không Dùng

}
