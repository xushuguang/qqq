package com.idqqtec.dess.action;

import com.idqqtec.dess.pojo.po.TrustNode;

import java.util.List;
import java.util.Map;

public interface TNService {
    Map<String,List> getTNTopology();

    TrustNode getTNForName(String tnName);
}
