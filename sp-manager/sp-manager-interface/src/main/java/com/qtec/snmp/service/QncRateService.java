package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.QncRate;

import java.util.List;

public interface QncRateService {

    List<QncRate> getAllQncRate(String neName, Long pairId);

    double getQncRate(String neName, Long pairId, Long time);

    void deleteAllQncRate();

    void setAllQncRateToRedis();

    String getAllQncRateFromRedis(String neName, Long pairId);

}
