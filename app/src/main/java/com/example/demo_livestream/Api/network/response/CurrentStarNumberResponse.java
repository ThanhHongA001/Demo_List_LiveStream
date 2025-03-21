package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentStarNumberResponse implements Serializable {
    @SerializedName("data")
    private CurrentStarNumber data;

    public CurrentStarNumber getData() {
        return data;
    }

    public class CurrentStarNumber{
        @SerializedName("id")
        private long id;
        @SerializedName("channelId")
        private long channelId;
        @SerializedName("userId")
        private String userId;
        @SerializedName("totalStar")
        private long totalStar;

        public long getId() {
            return id;
        }

        public long getChannelId() {
            return channelId;
        }

        public String getUserId() {
            return userId;
        }

        public long getTotalStar() {
            return totalStar;
        }
    }
}
