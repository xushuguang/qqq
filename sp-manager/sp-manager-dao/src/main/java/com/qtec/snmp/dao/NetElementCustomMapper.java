package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.NetElement;

import java.util.List;

public interface NetElementCustomMapper {
    List<NetElement> selectBelongGroup();

    List<NetElement> selectChildren(String belongGroup);
}