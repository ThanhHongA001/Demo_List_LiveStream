package com.viettel.mocha.rmlivestream.socket.stomp.pathmatcher;


import com.viettel.mocha.rmlivestream.socket.stomp.dto.StompHeader;
import com.viettel.mocha.rmlivestream.socket.stomp.dto.StompMessage;

public class SimplePathMatcher implements PathMatcher {

    @Override
    public boolean matches(String path, StompMessage msg) {
        String dest = msg.findHeader(StompHeader.DESTINATION);
        if (dest == null) return false;
        else return path.equals(dest);
    }
}
