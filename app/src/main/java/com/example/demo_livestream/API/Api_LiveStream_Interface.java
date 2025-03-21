package com.example.demo_livestream.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Body;

public interface Api_LiveStream_Interface {

    // 1. Lấy danh sách kênh
    @GET("/LivestreamAPI/v1/channel/list/v2")
    Call<Object> getListChannel(
            @Query("page") int page,
            @Query("size") int size
    );

    // 2. Lấy danh sách livestream
    @GET("/LivestreamAPI/v2/my_livestream/all")
    Call<Object> getListLivestream(
            @Query("featureId") int featureId,
            @Query("page") int page,
            @Query("size") int size
    );

    // 3. Lấy chi tiết livestream
    @GET("/LivestreamAPI/v2/my_livestream/details")
    Call<Object> getLivestreamDetail(
            @Query("id") String id);

    // 4. Thả reaction livestream
    @POST("/LivestreamAPI/v1/live_social/like")
    Call<Object> reactionLive(
            @Body Object requestBody);

    // 5. Thả reaction comment
    @POST("/LivestreamAPI/v1/live_social/react_comment")
    Call<Object> reactionComment(
            @Query("type") int type,
            @Query("smsgId") String smsgId,
            @Query("action") String action
    );

    // 6. Theo dõi kênh
    @POST("/LivestreamAPI/v1/channel/{id}/follow")
    Call<Object> followChannel(
            @Path("id") String channelId);

    // 7. Bỏ theo dõi kênh
    @POST("/LivestreamAPI/v1/channel/{id}/unfollow")
    Call<Object> unfollowChannel(
            @Path("id") String channelId);

    // 8. Lấy thông tin kênh
    @GET("/LivestreamAPI/v2/video/{id}/channel/v2")
    Call<Object> getChannelInfo(
            @Path("id") String id);

    // 9. Lấy video theo kênh
    @GET("/LivestreamAPI/v2/video/{id}/channel/v2")
    Call<Object> getVideoByChannel(
            @Path("id") String id,
            @Query("lastHashId") String lastHashId,
            @Query("page") int page,
            @Query("size") int size
    );

    // 10. Tìm kiếm video
    @GET("/LivestreamAPI/v1/search/livestream")
    Call<Object> searchVideo(
            @Query("page") int page,
            @Query("size") int size,
            @Query("q") String query
    );

    // 11. Tìm kiếm kênh
    @GET("/LivestreamAPI/v1/search/channel")
    Call<Object> searchChannel(
            @Query("page") int page,
            @Query("size") int size,
            @Query("q") String query
    );

    // 12. Lấy danh sách donate
    @GET("/LivestreamAPI/v2/donate/list-package-gift")
    Call<Object> getListDonate();

    // 13. Gửi quà (sao) trong livestream
    @POST("/LivestreamAPI/v2/donate")
    Call<Object> sendStar(
            @Body Object donateRequest);

    // 14. Xóa bình luận livestream
    @DELETE("/LivestreamAPI/v1/report_comment/delete")
    Call<Object> deleteCommentLivestream(
            @Query("id") String id,
            @Query("livestreamId") String livestreamId,
            @Query("streamerId") String streamerId,
            @Query("avatar") String avatar,
            @Query("userName") String userName
    );

    // 15. Bình luận video
    @POST("/LivestreamAPI/v1/social/comment")
    Call<Object> postCommentVideo(
            @Body Object commentRequest);

    // 16. Thích video
    @POST("/LivestreamAPI/v1/social/like")
    Call<Object> likeVideo(
            @Query("videoId") String videoId,
            @Query("action") String action
    );

    // 17. Lấy danh sách donate top
    @GET("/LivestreamAPI/v1/donate/list-donate/top")
    Call<Object> getTopDonate();
}
