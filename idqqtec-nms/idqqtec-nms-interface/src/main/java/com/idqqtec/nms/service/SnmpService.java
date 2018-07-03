package com.idqqtec.nms.service;

import com.idqqtec.nms.pojo.dto.NodeDto;

/**
 * SnmpService接口
 * User: james.xu
 * Date: 2018/3/26
 * Time: 15:59
 * Version:V1.0
 */
public interface SnmpService {
    /**
     * 获取网元之间关系
     */
    void setNeRelation();

    /**
     * 更新当前node下的网元关系
     * @param nodeDto
     */
    void updateNERelationForNode(NodeDto nodeDto);
}
