package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.EchartsVo;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 15:13
 * Version:V1.0
 */
public interface NetElementService {
    int saveNetElement(NetElement netElement);

    List<EchartsVo> listNetElemetVo();

    List<NetElement> listNetElemet();
}
