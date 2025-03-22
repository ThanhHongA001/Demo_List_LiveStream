package com.viettel.mocha.rmlivestream;

public class RMConstants {
    public static final String KeyScreen="Screen";
    public static final String KeyId="Id";


    public static final String RM_LIVESTREAM_FRG="RMLivestreamDetailFragment";
    public static final String RM_CHANNEL_FRG="RmChannelDetailFragment";

    public static final class Screen{
        public static final String HOME ="HOME";
        public static final String CHANNEL_DETAIL ="CHANNEL_DETAIL";
        public static final String LIVESTREAM_DETAIL ="LIVESTREAM_DETAIL";

    }
    public static final class ChatFunction{
        public int functionType;
        public boolean isChosen = false;
        public static final int TYPE_LIKE = 0;
        public static final int TYPE_LIKED = 1;
        public static final int TYPE_DONATE = 2;
        public static final int TYPE_HEART = 3;
        public static final int TYPE_HAHA = 4;
        public static final int TYPE_WOW = 5;
        public static final int TYPE_SAD = 6;
        public static final int TYPE_ANGRY = 7;

        public ChatFunction(int typeDonate) {
            this.functionType = typeDonate;
        }

        public void setFunctionType(int i) {
            this.functionType = i;
        }

        public void setChosen(boolean chosen) {
            isChosen = chosen;
        }

        public boolean isChosen() {
            return isChosen;
        }

        public int getFunctionType() {
            return functionType;
        }
    }
    public static class WebSocket {
        //        public static final String domain = "http://livews.tls.tl:8080/LiveEvent/live/event/websocket";
        public static final String domain = "https://mytelwss-live.ringme.vn/LiveEvent/live/event/websocket";
        //        public static final String domain = " https://livechat.ringme.vn:8443/LiveEvent/live/event/websocket";
        public static final String connectorUser = "user";
        public static final String connectorToken = "token";
        public static final String connectorUsername = "username";
        public static final String connectorContentType = "Content-type";
        public static final String connectorContentTypeValue = "application/json";

        public static final String pathSubscribeCMS = "/user/watching";
        public static final String pathSubscribeChannel = "/watching/";
        public static final String pathSendMessage = "/chat/message/";

        public static final String type = "type";
        public static final String typeMessage = "chat";
        public static final String typeOldMessage = "old_chat";
        public static final String typeViewNumber = "seen_number";
        public static final String typeGift = "donate";
        public static final String typeBlock = "block";
        public static final String typeLike = "like";
        public static final String typeNotify = "notify_live";
        public static final String typeReactComment = "react";
        public static final String typeDropStar = "drop_star";
        public static final String typeWait = "wait";
        public static final String reloadSurvey = "reload-survey";
        public static final String deleteChat="delete-chat";
        //block type
        public static final String pinMessage = "7";
        public static final String violation = "8";
        public static final String delete = "9";
        public static final String banMessage300s = "10";
        public static final String hideStream = "11";
        public static final String banChat = "12";

        public static final String typeStream = "START";
    }
}
