package com.sh.pillar.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketSession;

/**
 * 解析websocket请求参数
 */
@Slf4j
public class WebsocketParamUtil {

    public static JSONObject parse(WebSocketSession session) {
        return getParams(session.getUri().getQuery());
    }

    public static JSONObject parse(ServerHttpRequest request) {
        return getParams(request.getURI().getQuery());
    }

    private static JSONObject getParams(String requestQuery) {
        JSONObject jsonParam = new JSONObject();

        if (ObjectUtils.isEmpty(requestQuery)) return jsonParam;

        String[] params = requestQuery.split("&");
        if (params.length > 0) {
            for (int paramLoop = 0; paramLoop < params.length; paramLoop++) {
                String keyValue[] = params[paramLoop].split("=");
                if (keyValue.length == 2) {
                    jsonParam.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return jsonParam;
    }


}
