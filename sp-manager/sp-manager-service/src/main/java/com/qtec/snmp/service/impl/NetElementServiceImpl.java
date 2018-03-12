package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.utils.GetStateUtil;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 15:16
 * Version:V1.0
 */
@Service
public class NetElementServiceImpl implements NetElementService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private NERelationMapper neRelationDao;

    @Override
    public int saveNetElement(NetElement netElement) {
        int insert = 0;
        try {
            //先根据ip查询设备是否已经存在
            NetElementExample example = new NetElementExample();
            example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
            List<NetElement> netElements = netElementDao.selectByExample(example);
            if (netElements != null && netElements.size()>0) {
                //设备已存在，不可以添加
            }else {
                //设备不存在，可以添加
               //给设备添加状态
                netElement.setState(GetStateUtil.getState(netElement.getNeIp()));
                //添加设备
                insert = netElementDao.insert(netElement);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return insert;
    }

    @Override
    public List<EchartsVo> statisticsNetElemet() {
        List<EchartsVo> list = null;
        try {
            list = new ArrayList<>();
            List<String> types = netElementDao.selectType();
            for (String type : types){
                NetElementExample example = new NetElementExample();
                example.createCriteria().andTypeEqualTo(type);
                int value = netElementDao.selectByExample(example).size();
                EchartsVo vo = new EchartsVo();
                vo.setName(type);
                vo.setValue(value);
                list.add(vo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<NodeVo> listNodeVo() {
        List<NodeVo> list = null;
        try {
            list = new ArrayList<>();
            //先查询管理层设备QTN,QNC
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andTypeNotEqualTo("QKD");
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            //封装进nodeVo
            for (NetElement netElement : netElements){
                NodeVo nodeVo = new NodeVo();
                nodeVo.setName(netElement.getNeName());
                nodeVo.setLabel(netElement.getNeName());
                nodeVo.setCategory(2);
                nodeVo.setSymbolSize(20);
                nodeVo.setFlag(true);
                nodeVo.setIgnore(true);
                list.add(nodeVo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<LinkVo> listLinkVo() {
        List<LinkVo> list = null;
        try {
            list = new ArrayList<LinkVo>();
            //先查询ne_relation表中的数据
            List<NERelation> neRelations = neRelationDao.selectByExample(new NERelationExample());
            for (NERelation neRelation : neRelations){
                //查询source
                NERelationExample neRelationExample1 = new NERelationExample();
                neRelationExample1.createCriteria().andNeidEqualTo(neRelation.getNeid());
                List<NERelation> neRelations1 = neRelationDao.selectByExample(neRelationExample1);
                //获取节点id
                Long parentId1 = neRelations1.get(0).getParentId();
                //根据节点id查询到节点名
                NetElementExample netElementExample1 = new NetElementExample();
                netElementExample1.createCriteria().andIdEqualTo(parentId1);
                List<NetElement> netElements1 = netElementDao.selectByExample(netElementExample1);
                String source = netElements1.get(0).getNeName();
                //查询target
                //如果存在pairingId
                if(neRelation.getPairingId()!=null){
                    NERelationExample neRelationExample2 = new NERelationExample();
                    neRelationExample2.createCriteria().andNeidEqualTo(neRelation.getPairingId());
                    List<NERelation> neRelations2 = neRelationDao.selectByExample(neRelationExample2);
                    //获取节点id
                    Long parentId2 = neRelations2.get(0).getParentId();
                    //根据节点id查询到节点名
                    NetElementExample netElementExample2 = new NetElementExample();
                    netElementExample2.createCriteria().andIdEqualTo(parentId2);
                    List<NetElement> netElements2 = netElementDao.selectByExample(netElementExample2);
                    String target = netElements2.get(0).getNeName();
                    //封装进linkVo对象
                    LinkVo linkVo = new LinkVo();
                    linkVo.setSource(source);
                    linkVo.setTarget(target);
                    //存入list
                    list.add(linkVo);
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<NetElement> getNEDetails(String neName) {
        List<NetElement> list = null;
        try {
            list = new ArrayList<>();
            //先根据neName查询到parentId
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            Long parentId = netElements.get(0).getId();
            //再根据parentId查询到neid
            NERelationExample neRelationExample = new NERelationExample();
            neRelationExample.createCriteria().andParentIdEqualTo(parentId);
            List<NERelation> neRelations = neRelationDao.selectByExample(neRelationExample);
            for (NERelation neRelation : neRelations){
                Long neid = neRelation.getNeid();
                //根据neid查询netElement
                NetElement netElement = netElementDao.selectByPrimaryKey(neid);
                list.add(netElement);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
