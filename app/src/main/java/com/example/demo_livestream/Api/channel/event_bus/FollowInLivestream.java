package com.example.demo_livestream.Api.channel.event_bus;

public class FollowInLivestream {
    private Boolean isFollow;

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public FollowInLivestream(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}
