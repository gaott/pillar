package com.sh.pillar.schema.plan;

import com.sh.pillar.schema.cont.BaseState;

import java.io.Serializable;

public class Mission implements Serializable {
    private String id;
    private String name;
    private String groupId;
    private BaseState state;
    private Integer priority;

}
