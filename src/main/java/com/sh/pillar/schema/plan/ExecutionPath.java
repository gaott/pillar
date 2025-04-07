package com.sh.pillar.schema.plan;

import com.sh.pillar.schema.cont.ExecutionMode;

import java.io.Serializable;
import java.util.List;

public class ExecutionPath implements Serializable {
    private String id;
    private String decompositionId  ;
    private List<String> taskIds;
    private ExecutionMode mode;
}
