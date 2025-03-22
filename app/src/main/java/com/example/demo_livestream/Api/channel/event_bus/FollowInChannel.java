package com.example.demo_livestream.Api.channel.event_bus;

public class FollowInChannel {
    private Boolean isFollow;

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public FollowInChannel(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}
