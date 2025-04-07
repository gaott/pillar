package com.sh.pillar.connect;

import com.alibaba.fastjson.JSONObject;
import com.sh.pillar.utils.WebsocketParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@Slf4j
public class CommonHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("beforeHandshake >>>>>> {} {}", attributes, request.getURI().getQuery());
        JSONObject params = WebsocketParamUtil.parse(request);
        attributes.put("deviceId", params.getString("deviceId"));
        attributes.put("userId", params.getString("userId"));
        attributes.put("connectTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("afterHandshake >>>>>>");
    }
}
