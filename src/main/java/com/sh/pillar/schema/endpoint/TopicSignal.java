package com.sh.pillar.schema.endpoint;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class TopicSignal implements Serializable {
    private String id;
    private String name;
    private JSONObject message;
    private Long epTimestamp;
}
