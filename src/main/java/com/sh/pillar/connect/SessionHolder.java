package com.sh.pillar.connect;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
public class SessionHolder {
    private static ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            5, 10, 10, TimeUnit.MINUTES, new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("SessionHolder-POOL-#%d").build(), new ThreadPoolExecutor.AbortPolicy());

    private static ConcurrentHashMap<String, WebSocketSession> webSessionCache = new ConcurrentHashMap<>();

    public static int getSize() {
        return webSessionCache.size();
    }

    public static Set<String> getKeys() {
        return webSessionCache.keySet();
    }


    public static String getSessionId(WebSocketSession session) {
        String deviceId = (String) session.getAttributes().get("deviceId");
        return deviceId;
    }

    public static void add(WebSocketSession session, ConnectorType connector) {
        String deviceId = getSessionId(session);
        if (deviceId == null) {
            return ;
        }

        log.info("# ADD_SESSION deviceId: {}", deviceId);
        webSessionCache.put(getKey(session, connector), session);
    }

    public static void remove(WebSocketSession session, ConnectorType connector) {
        String deviceId = getSessionId(session);
        if (StringUtils.isEmpty(deviceId)) {
            return ;
        }

        log.info("# REMOVE_SESSION deviceId: {}", deviceId);
        webSessionCache.remove(getKey(session, connector));
    }

    public static boolean hasValidSession(ConnectorType connector, String deviceId) {
        return webSessionCache.containsKey(connector + deviceId);
    }

    public static void asyncSendTextMessage(ConnectorType connector, String deviceId, JSONObject message) {
        POOL.execute(() -> {
            sendTextMessage(connector, deviceId, message);
        });
    }

    public static boolean sendTextMessage(ConnectorType connector, String deviceId, JSONObject message) {
        WebSocketSession session = webSessionCache.get(connector + deviceId);
        if (session == null) {
            log.info("# SEND_MSG {} session is null", deviceId);
            return false;
        }

        try {
            log.info("# SEND_TEXT_MESSAGE. message:{}", message.toJSONString());
            session.sendMessage(new TextMessage(message.toJSONString()));
        } catch (Exception e) {
            log.error("# SEND_MSG ERROR. device:{} ", deviceId, e);
            return false;
        }

        return true;
    }

    public static boolean sendBinaryMessage(ConnectorType connector, String deviceId, byte[] data) {
        WebSocketSession session = webSessionCache.get(connector + deviceId);
        if (session == null) {
            log.info("# SEND_MSG {} session is null", deviceId);
            return false;
        }

        try {
            log.info("# SEND_BINARY_MESSAGE. deviceId:{}, size:{}", deviceId, data.length);
            session.sendMessage(new BinaryMessage(data));
        } catch (Exception e) {
            log.error("# SEND_MSG ERROR. device:{} ", deviceId, e);
            return false;
        }

        return true;
    }

    private static String getKey(WebSocketSession session, ConnectorType connector) {
        return connector.name() + getSessionId(session);
    }
}
