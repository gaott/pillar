package com.sh.pillar.schema.plan;

import com.sh.pillar.schema.cont.BaseState;
import com.sh.pillar.schema.cont.ExecutionMode;

import java.io.Serializable;

public class Decomposition implements Serializable {
    private String id;
    private String missionId;
    private String deviceId;
    private String name;
    private BaseState state;
    private Integer priority;
    private ExecutionMode executionMode;

}
