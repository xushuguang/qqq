package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.PropertyGrid;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.NERelation;
import com.qtec.snmp.pojo.po.NERelationExample;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.pojo.vo.*;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * NetElementService实现类
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

    /**
     *保存网元设备
     * @param netElement
     * @return int
     */
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
                insert = 0;
            }else {
                //设备不存在，可以添加
               //给设备添加状态
                netElement.setState(0);
                //添加设备
                insert = netElementDao.insert(netElement);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return insert;
    }

    /**
     * 首页获取网元设备类型和数目信息
     * @return list
     */
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

    /**
     * 拓扑图获取node
     * @return list
     */
    @Override
    public List<NodeVo> listNodeVo() {
        List<NodeVo> list = null;
        try {
            list = new ArrayList<>();
            //先查询管理层设备QTN,QNC
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andTypeNotEqualTo("QKD");
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            for (NetElement netElement : netElements){
                //添加颜色
                ItemStyle itemStyle = new ItemStyle();
                Normal normal = new Normal();
               if (netElement.getState()==2){
                   //设备状态为2，则是绿色
                   normal.setColor("green");
               }else if (netElement.getState()==1){
                   //设备状态为1，则是黄色
                   normal.setColor("yellow");
               }else {
                   //设备状态为0，则是红色
                   normal.setColor("red");
               }
                itemStyle.setNormal(normal);
                //封装进nodeVo
                NodeVo nodeVo = new NodeVo();
                nodeVo.setName(netElement.getNeName());
                nodeVo.setCategory(2);
                nodeVo.setSymbol("circle");
                nodeVo.setSymbolSize(20);
                nodeVo.setItemStyle(itemStyle);
                list.add(nodeVo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 拓扑图获取link
     * @return
     */
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

    /**
     * 根据网元名获取该网元设备底下的所有网元
     * @param neName
     * @return list
     */
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

    /**
     * 根据网元id获取当前网元设备的详细信息
     * @param id
     * @return result
     */
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
            propertyGrid1.setName("名称");
            propertyGrid1.setValue(netElement.getNeName());
            propertyGrid1.setGroup("本地网元详情");
            list.add(propertyGrid1);
            PropertyGrid propertyGrid2 = new PropertyGrid();
            propertyGrid2.setName("IP地址");
            propertyGrid2.setValue(netElement.getNeIp());
            propertyGrid2.setGroup("本地网元详情");
            list.add(propertyGrid2);
            PropertyGrid propertyGrid3 = new PropertyGrid();
            propertyGrid3.setName("类型");
            propertyGrid3.setValue(netElement.getType());
            propertyGrid3.setGroup("本地网元详情");
            list.add(propertyGrid3);
            //根据pairingID查询到对端网元信息
            NetElement PairingNetElement = netElementDao.selectByPrimaryKey(neRelation.getPairingId());
            //封装
            PropertyGrid propertyGrid4 = new PropertyGrid();
            propertyGrid4.setName("名称");
            propertyGrid4.setValue(PairingNetElement.getNeName());
            propertyGrid4.setGroup("对端网元详情");
            list.add(propertyGrid4);
            PropertyGrid propertyGrid5 = new PropertyGrid();
            propertyGrid5.setName("IP地址");
            propertyGrid5.setValue(PairingNetElement.getNeIp());
            propertyGrid5.setGroup("对端网元详情");
            list.add(propertyGrid5);
            PropertyGrid propertyGrid6 = new PropertyGrid();
            propertyGrid6.setName("类型");
            propertyGrid6.setValue(PairingNetElement.getType());
            propertyGrid6.setGroup("对端网元详情");
            list.add(propertyGrid6);
            //封装distance
            PropertyGrid propertyGrid7 = new PropertyGrid();
            propertyGrid7.setName("距离(/km)");
            propertyGrid7.setValue(neRelation.getDistance().toString());
            list.add(propertyGrid7);
            //封装进result
            result = new Result<>();
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取所有网元设备
     * @return result
     */
    @Override
    public Result<NetElement> listNetElement() {
        Result<NetElement> result = null;
        try {
            result = new Result<>();
            List<NetElement> list = netElementDao.selectByExample(new NetElementExample());
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int removeNetElements(List<Long> ids) {
        int result = 0;
        try {
            //先根据ids删除网元
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andIdIn(ids);
            result = netElementDao.deleteByExample(netElementExample);
            //再根据ids删除网元关系表中的数据\
            NERelationExample neRelationExample = new NERelationExample();
            neRelationExample.createCriteria().andNeidIn(ids);
            neRelationDao.deleteByExample(neRelationExample);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
