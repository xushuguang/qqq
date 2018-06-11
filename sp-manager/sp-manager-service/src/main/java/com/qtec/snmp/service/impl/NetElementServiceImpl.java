package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.PropertyGrid;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.KeybufferMapper;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.*;
import com.qtec.snmp.service.GetStateService;
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
    @Autowired
    private GetStateService getStateService;
    @Autowired
    private KeybufferMapper keyBufferDao;

    /**
     * 验证IP和Name是否存在
     * @param netElement
     * @return int
     */
    public int verifyNEByNameAndIp(NetElement netElement){
        int i = 0;
        try {
            //先根据ip查询设备是否已经存在
            NetElementExample example = new NetElementExample();
            example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
            List<NetElement> netElements = netElementDao.selectByExample(example);
            if (netElements.isEmpty()||netElements.size()==0) {
                //再根据名字查询设备是否已经存在
                NetElementExample example1 = new NetElementExample();
                example1.createCriteria().andNeNameEqualTo(netElement.getNeName());
                List<NetElement> netElementList = netElementDao.selectByExample(example1);
                if (netElementList.isEmpty() || netElementList.size() == 0) {
                   i = 0;
                }else {
                    i = -2;
                }
            }else {
                i = -1;
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    /**
     *保存网元设备
     * @param netElement
     * @return int
     */
    @Override
    public int saveNetElement(NetElement netElement) {
        int insert = 0;
        try {
            insert = verifyNEByNameAndIp(netElement);
            if (insert==0){
                //获取设备状态
                int state = getStateService.getStateForNetElement(netElement);
                netElement.setState(state);
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
            if (types!=null&&types.size()>0){
                for (String type : types){
                    NetElementExample example = new NetElementExample();
                    example.createCriteria().andTypeEqualTo(type);
                    int value = netElementDao.selectByExample(example).size();
                    EchartsVo vo = new EchartsVo();
                    vo.setName(type);
                    vo.setValue(value);
                    list.add(vo);
                }
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
                nodeVo.setSymbolSize(25);
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
            //先查询ne_relation表中的数据,有QKD的TN
            NERelationExample neRelationExample = new NERelationExample();
            neRelationExample.createCriteria().andLinkTypeEqualTo("1");
            List<NERelation> neRelations = neRelationDao.selectByExample(neRelationExample);
            if (neRelations!=null&&neRelations.size()>0){
                for (NERelation neRelation : neRelations){
                    //查询source
                    NERelationExample neRelationExample1 = new NERelationExample();
                    neRelationExample1.createCriteria().andNeidEqualTo(neRelation.getNeid());
                    List<NERelation> neRelations1 = neRelationDao.selectByExample(neRelationExample1);
                    if (neRelations1!=null&&neRelations1.size()>0){
                        //获取节点id
                        Long parentId1 = neRelations1.get(0).getParentId();
                        //根据节点id查询到节点名
                        String source = netElementDao.selectByPrimaryKey(parentId1).getNeName();
                        //查询target
                        //如果存在pairingId
                        if(neRelation.getPairingId()!=null){
                            NERelationExample neRelationExample2 = new NERelationExample();
                            neRelationExample2.createCriteria().andNeidEqualTo(neRelation.getPairingId());
                            List<NERelation> neRelations2 = neRelationDao.selectByExample(neRelationExample2);
                            if (neRelations2!=null&&neRelations2.size()>0){
                                //获取节点id
                                Long parentId2 = neRelations2.get(0).getParentId();
                                //根据节点id查询到节点名
                                String target = netElementDao.selectByPrimaryKey(parentId2).getNeName();
                                //封装进linkVo对象
                                Normal normal = new Normal();
                                normal.setColor("black");
                                normal.setType("solid");
                                normal.setWidth(1);
                                ItemStyle itemStyle = new ItemStyle();
                                itemStyle.setNormal(normal);
                                LinkVo linkVo = new LinkVo();
                                linkVo.setSource(source);
                                linkVo.setTarget(target);
                                linkVo.setLineStyle(itemStyle);
                                //存入list
                                list.add(linkVo);
                            }
                        }
                    }
                }
            }
            NERelationExample neRelationExample1 = new NERelationExample();
            neRelationExample1.createCriteria().andLinkTypeEqualTo("2");
            List<NERelation> neRelations1 = neRelationDao.selectByExample(neRelationExample1);
            if (neRelations1!=null&&neRelations1.size()>0){
                for (NERelation neRelation : neRelations1){
                    String source = netElementDao.selectByPrimaryKey(neRelation.getNeid()).getNeName();
                    if (neRelation.getPairingId()!=null){
                        String target = netElementDao.selectByPrimaryKey(neRelation.getPairingId()).getNeName();
                        Normal normal = new Normal();
                        normal.setColor("lime");
                        normal.setType("dotted");
                        normal.setWidth(2);
                        ItemStyle itemStyle = new ItemStyle();
                        itemStyle.setNormal(normal);
                        LinkVo linkVo = new LinkVo();
                        linkVo.setSource(source);
                        linkVo.setTarget(target);
                        linkVo.setLineStyle(itemStyle);
                        //存入list
                        list.add(linkVo);
                    }
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
            if (netElements!=null&&netElements.size()>0){
                Long parentId = netElements.get(0).getId();
                //再根据parentId查询到neid
                NERelationExample neRelationExample = new NERelationExample();
                neRelationExample.createCriteria().andParentIdEqualTo(parentId);
                List<NERelation> neRelations = neRelationDao.selectByExample(neRelationExample);
                if (neRelations!=null&&neRelations.size()>0){
                    for (NERelation neRelation : neRelations){
                        Long neid = neRelation.getNeid();
                        //根据neid查询netElement
                        NetElement netElement = netElementDao.selectByPrimaryKey(neid);
                        list.add(netElement);
                    }
                }
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
            propertyGrid7.setGroup("距离");
            list.add(propertyGrid7);
            //封装进result距离
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

    /**
     * 根据网元id删除网元，可以多条删除
     * @param ids
     * @return int
     */
    @Override
    public int removeNetElements(List<Long> ids) {
        int result = 0;
        try {
            //先根据ids删除网元
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andIdIn(ids);
            result = netElementDao.deleteByExample(netElementExample);
            //再根据ids删除网元关系表中的数据
            NERelationExample neRelationExample = new NERelationExample();
            neRelationExample.createCriteria().andNeidIn(ids);
            neRelationDao.deleteByExample(neRelationExample);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据网元id获取该网元的信息
     * @param neId
     * @return NetElement
     */
    @Override
    public NetElement getNetElementById(Long neId) {
        NetElement netElement = null;
        try {
          netElement = netElementDao.selectByPrimaryKey(neId);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return netElement;
    }

    /**
     * 更新网元设备
     * @param netElement
     * @return int
     */
    @Override
    public int updateNetElement(NetElement netElement) {
        int i = 0;
        try {
            i = verifyNEByNameAndIp(netElement);
            if (i==0){
                NetElementExample example = new NetElementExample();
                example.createCriteria().andIdEqualTo(netElement.getId());
                i = netElementDao.updateByExampleSelective(netElement, example);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 根据当前网元名获取当前TN与其它TN之间的关系
     * @param neName
     * @return
     */
    @Override
    public List<NetElement> getTNRelation(String neName) {
        List<NetElement> list = null;
        try {
            //先根据neName查询到TNIP
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            if (netElements!=null&&netElements.size()>0){
                list = new ArrayList<>();
                String tnIp = netElements.get(0).getNeIp();
                //再根据id查询到所有对端的tnIp
                List<String> strings = keyBufferDao.distinctPairTNIP(tnIp);
                if (strings!=null&&strings.size()>0){
                    for (String pairTnIp : strings){
                        NetElementExample netElementExample1 = new NetElementExample();
                        netElementExample1.createCriteria().andNeIpEqualTo(pairTnIp);
                        List<NetElement> netElements1 = netElementDao.selectByExample(netElementExample1);
                        if (netElements1!=null&&netElements1.size()>0){
                            list.add(netElements1.get(0));
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据当前TN名和对端TNid查询它们的信息
     * @param neName
     * @param pairId
     * @return
     */
    @Override
    public Result<PropertyGrid> getTNDetails(String neName, Long pairId) {
        Result<PropertyGrid> result = null;
        try {
            //先根据neName查询到TN信息
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            NetElement localTN = netElementDao.selectByExample(netElementExample).get(0);
            //再根据pairId查询到TN信息
            NetElement pairTN = netElementDao.selectByPrimaryKey(pairId);
            //新建一个list用于保存
            List<PropertyGrid> list = new ArrayList();
            PropertyGrid propertyGrid1 = new PropertyGrid();
            propertyGrid1.setName("名称");
            propertyGrid1.setValue(localTN.getNeName());
            propertyGrid1.setGroup("本地TN详情");
            list.add(propertyGrid1);
            PropertyGrid propertyGrid2 = new PropertyGrid();
            propertyGrid2.setName("IP地址");
            propertyGrid2.setValue(localTN.getNeIp());
            propertyGrid2.setGroup("本地TN详情");
            list.add(propertyGrid2);
            PropertyGrid propertyGrid3 = new PropertyGrid();
            propertyGrid3.setName("类型");
            propertyGrid3.setValue(localTN.getType());
            propertyGrid3.setGroup("本地TN详情");
            list.add(propertyGrid3);
            PropertyGrid propertyGrid4 = new PropertyGrid();
            propertyGrid4.setName("名称");
            propertyGrid4.setValue(pairTN.getNeName());
            propertyGrid4.setGroup("对端TN详情");
            list.add(propertyGrid4);
            PropertyGrid propertyGrid5 = new PropertyGrid();
            propertyGrid5.setName("IP地址");
            propertyGrid5.setValue(pairTN.getNeIp());
            propertyGrid5.setGroup("对端TN详情");
            list.add(propertyGrid5);
            PropertyGrid propertyGrid6 = new PropertyGrid();
            propertyGrid6.setName("类型");
            propertyGrid6.setValue(pairTN.getType());
            propertyGrid6.setGroup("对端TN详情");
            list.add(propertyGrid6);
            //封装进result距离
            result = new Result<>();
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
