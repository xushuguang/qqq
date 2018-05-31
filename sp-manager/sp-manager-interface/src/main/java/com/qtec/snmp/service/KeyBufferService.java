package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.Keybuffer;

import java.util.List;

public interface KeyBufferService {
    /**
     * 根据本地网元名和对端网元id以及时间获取当前的keybuffer
     * @param neName
     * @return int
     */
    int getKeyBuffer(String neName,Long pairId,Long time);
    /**
     * 根据本地网元名和对端网元id获取当前一对TN的所有KeyBuffer
     * @param neName
     * @param pairId
     * @return
     */
    List<Keybuffer> getAllKeyBuffer(String neName, Long pairId);

    /**
     * 删除所有的KeyBuffer
     */
    void delALLKeyBuffer();

    /**
     * 把所有KeyBuffer放入缓存
     */
    void setAllKeyBufferToRedis();

    /**
     * 根据neName和pairId从缓存中取KeyBuffer
     * @param neName
     * @param pairId
     * @return
     */
    String getAllKeyBufferFromRedis(String neName, Long pairId);
}
