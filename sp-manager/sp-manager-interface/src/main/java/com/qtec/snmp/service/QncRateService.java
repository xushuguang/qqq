package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.QncRate;

import java.util.List;

public interface QncRateService {
    String getAllQncRateFromRedis(String neName, Long pairId);

    List<QncRate> getAllQncRate(String neName, Long pairId);

    double getQncRate(String neName, Long pairId, Long time);
}
