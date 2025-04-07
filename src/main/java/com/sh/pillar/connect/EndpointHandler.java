package com.sh.pillar.connect;

import com.alibaba.fastjson.JSONObject;
import com.sh.pillar.service.EndpointReportService;
import com.sh.pillar.service.PillarApplicationContextHolder;
import com.sh.pillar.utils.WebsocketParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
public class EndpointHandler extends AbstractWebSocketHandler implements ApplicationContextAware {

    private EndpointReportService endpointReportService;

    public EndpointHandler() {
        this.endpointReportService = (EndpointReportService) PillarApplicationContextHolder.getBean("endpointReportService");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("# EndpointHandler Context ...");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        JSONObject param = WebsocketParamUtil.parse(session);
        SessionHolder.add(session, ConnectorType.ENDPOINT);
        log.info("# client_connected. params:{}", param);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("# handleTextMessage. message:{}", message.getPayload());
        endpointReportService.handle((String) session.getAttributes().get("deviceId"), message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("# handleTransportError.", exception);
        SessionHolder.remove(session, ConnectorType.ENDPOINT);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.error("afterConnectionClosed. code:{}", status.getCode());
        SessionHolder.remove(session, ConnectorType.ENDPOINT);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("handleBinaryMessage. message length:{}", message.getPayloadLength());
    }
}
