package com.idqqtec.nms.service;

import com.idqqtec.nms.pojo.po.QncRate;

import java.util.List;

public interface QncRateService {

    List<QncRate> getAllQncRate(String neName, Long pairId);

    double getQncRate(String neName, Long pairId, Long time);

    void deleteAllQncRate();

    void setAllQncRateToRedis();

    String getAllQncRateFromRedis(String neName, Long pairId);

}
