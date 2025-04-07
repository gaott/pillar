package com.sh.pillar.schema.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.sh.pillar.schema.cont.BaseState;
import java.io.Serializable;
import java.util.List;


public class AtomicTask implements Serializable {
    private String id;
    private String name;
    private Integer priority;
    private BaseState state;
    private JSONObject params;
    private List<TopicSignal> dependSignals;
    private List<ErrorSolution> errorSolutions;
}



