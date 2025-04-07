package com.sh.pillar.controller;

import com.alibaba.fastjson.JSONObject;
import com.sh.pillar.connect.ConnectorType;
import com.sh.pillar.connect.SessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class EndpointController {

    @RequestMapping(value = "/ep/test", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject debug(HttpServletRequest request, @RequestBody JSONObject msg) {
        boolean isPushed = SessionHolder.sendTextMessage(ConnectorType.ENDPOINT, msg.getString("deviceId"), msg);
        msg.put("isPushed", isPushed);
        return msg;
    }
}
