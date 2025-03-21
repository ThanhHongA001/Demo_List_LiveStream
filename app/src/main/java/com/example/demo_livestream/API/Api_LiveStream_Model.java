package com.example.demo_livestream.API;

public class Api_LiveStream_Model {

    // Model cho danh sách kênh
    public static class Channel {
        public String id;
        public String name;
        public String avatar;
    }

    // Model cho danh sách livestream
    public static class LiveStream {
        public String id;
        public String title;
        public String thumbnail;
        public String streamerId;
    }

    // Model cho bình luận
    public static class Comment {
        public String id;
        public String content;
        public String userId;
        public String userName;
    }

    // Model cho donate
    public static class Donate {
        public String id;
        public String userId;
        public int amount;
    }

    // Model danh sách phản hồi bình luận
    public static class Reply {
        public String id;
        public String commentId;
        public String userId;
        public String content;
    }

    // Model phản hồi API chung
    public static class ApiResponse<T> {
        public boolean success;
        public String message;
        public T data;
    }
}
