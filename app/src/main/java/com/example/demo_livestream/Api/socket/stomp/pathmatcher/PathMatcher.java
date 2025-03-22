package com.viettel.mocha.rmlivestream.socket.stomp.pathmatcher;


import com.viettel.mocha.rmlivestream.socket.stomp.dto.StompMessage;

public interface PathMatcher {

    boolean matches(String path, StompMessage msg);
}
