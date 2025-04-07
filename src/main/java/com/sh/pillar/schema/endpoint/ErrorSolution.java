package com.sh.pillar.schema.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.sh.pillar.schema.cont.ErrorOption;

import java.io.Serializable;

public class ErrorSolution implements Serializable {
    private String id;
    private String taskId;
    private ErrorOption option;
    private Integer code;
    private JSONObject message;

}
