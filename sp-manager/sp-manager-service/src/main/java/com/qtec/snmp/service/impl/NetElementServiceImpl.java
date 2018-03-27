package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.PropertyGrid;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.common.utils.GetStateUtil;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.NERelation;
import com.qtec.snmp.pojo.po.NERelationExample;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
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
    public List<NetElement> getNEChildren(String neName) {
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

    @Override
    public Result<PropertyGrid> getNEDetails(Long id) {
        Result<PropertyGrid> result = null;
        try {
            //先根据id查到网元对端pairingid和parentid和distance
            NERelationExample neRelationExample = new NERelationExample();
            neRelationExample.createCriteria().andNeidEqualTo(id);
            NERelation neRelation = neRelationDao.selectByExample(neRelationExample).get(0);
            //新建一个list用于保存
            List<PropertyGrid> list = new ArrayList();
            //根据id查到网元信息
            NetElement netElement = netElementDao.selectByPrimaryKey(id);
            //封装
            PropertyGrid propertyGrid1 = new PropertyGrid();
            propertyGrid1.setName("name");
            propertyGrid1.setValue(netElement.getNeName());
            propertyGrid1.setGroup("NE");
            list.add(propertyGrid1);
            PropertyGrid propertyGrid2 = new PropertyGrid();
            propertyGrid2.setName("IP");
            propertyGrid2.setValue(netElement.getNeIp());
            propertyGrid2.setGroup("NE");
            list.add(propertyGrid2);
            PropertyGrid propertyGrid3 = new PropertyGrid();
            propertyGrid3.setName("type");
            propertyGrid3.setValue(netElement.getType());
            propertyGrid3.setGroup("NE");
            list.add(propertyGrid3);
            //根据pairingID查询到对端网元信息
            NetElement PairingNetElement = netElementDao.selectByPrimaryKey(neRelation.getPairingId());
            //封装
            PropertyGrid propertyGrid4 = new PropertyGrid();
            propertyGrid4.setName("name");
            propertyGrid4.setValue(PairingNetElement.getNeName());
            propertyGrid4.setGroup("Pairing");
            list.add(propertyGrid4);
            PropertyGrid propertyGrid5 = new PropertyGrid();
            propertyGrid5.setName("IP");
            propertyGrid5.setValue(PairingNetElement.getNeIp());
            propertyGrid5.setGroup("Pairing");
            list.add(propertyGrid5);
            PropertyGrid propertyGrid6 = new PropertyGrid();
            propertyGrid6.setName("type");
            propertyGrid6.setValue(PairingNetElement.getType());
            propertyGrid6.setGroup("Pairing");
            list.add(propertyGrid6);
            //根据parentID查询到父网元信息
            NetElement ParentNetElement = netElementDao.selectByPrimaryKey(neRelation.getParentId());
            //封装
            PropertyGrid propertyGrid7 = new PropertyGrid();
            propertyGrid7.setName("name");
            propertyGrid7.setValue(ParentNetElement.getNeName());
            propertyGrid7.setGroup("Parent");
            list.add(propertyGrid7);
            PropertyGrid propertyGrid8 = new PropertyGrid();
            propertyGrid8.setName("IP");
            propertyGrid8.setValue(ParentNetElement.getNeIp());
            propertyGrid8.setGroup("Parent");
            list.add(propertyGrid8);
            PropertyGrid propertyGrid9 = new PropertyGrid();
            propertyGrid9.setName("type");
            propertyGrid9.setValue(ParentNetElement.getType());
            propertyGrid9.setGroup("Parent");
            list.add(propertyGrid9);
            //封装distance
            PropertyGrid propertyGrid10 = new PropertyGrid();
            propertyGrid10.setName("distance(/km)");
            propertyGrid10.setValue(neRelation.getDistance().toString());
            list.add(propertyGrid10);
            //封装进result
            result = new Result<>();
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result<NetElement> listNetElement() {
        Result<NetElement> result = null;
        try {
            result = new Result<>();
            NetElementExample example = new NetElementExample();
            List<NetElement> list = netElementDao.selectByExample(example);
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
