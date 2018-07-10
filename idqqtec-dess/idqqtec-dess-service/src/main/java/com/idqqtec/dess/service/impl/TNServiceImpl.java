package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.action.TNService;
import com.idqqtec.dess.dao.TNRelationMapper;
import com.idqqtec.dess.dao.TrustNodeMapper;
import com.idqqtec.dess.pojo.po.TNRelation;
import com.idqqtec.dess.pojo.po.TNRelationExample;
import com.idqqtec.dess.pojo.po.TrustNode;
import com.idqqtec.dess.pojo.po.TrustNodeExample;
import com.idqqtec.nms.common.vo.LinkVo;
import com.idqqtec.nms.common.vo.NodeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TNServiceImpl implements TNService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TrustNodeMapper trustNodeDao;
    @Autowired
    private TNRelationMapper tnRelationDao;
    @Override
    public Map<String, List> getTNTopology() {
        Map<String, List> map = null;
        try{
            map = new HashMap<>();
            //先查询node
            List<TrustNode> trustNodes = trustNodeDao.selectByExample(new TrustNodeExample());
            List<NodeVo> nodeVos = new ArrayList<>();
            if (trustNodes!=null&&trustNodes.size()>0){
                for (TrustNode trustNode : trustNodes){
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setName(trustNode.getName());
                    nodeVo.setCategory(2);
                    nodeVo.setSymbol("circle");
                    nodeVo.setSymbolSize(25);
                    nodeVos.add(nodeVo);
                }
            }
            //再查询link
            List<TNRelation> tnRelations = tnRelationDao.selectByExample(new TNRelationExample());
            List<LinkVo> linkVos = new ArrayList<>();
            if (tnRelations!=null&&tnRelations.size()>0){
                for (TNRelation tnRelation : tnRelations){
                    LinkVo linkVo = new LinkVo();
                    String source = trustNodeDao.selectByPrimaryKey(tnRelation.getLocalId()).getName();
                    String target = trustNodeDao.selectByPrimaryKey(tnRelation.getPairId()).getName();
                    linkVo.setSource(source);
                    linkVo.setTarget(target);
                    linkVos.add(linkVo);
                }
            }
            //放进map
            map.put("node",nodeVos);
            map.put("link",linkVos);

        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public TrustNode getTNForName(String tnName) {
        TrustNode trustNode = null;
        try{
            TrustNodeExample trustNodeExample = new TrustNodeExample();
            trustNodeExample.createCriteria().andNameEqualTo(tnName);
            List<TrustNode> trustNodes = trustNodeDao.selectByExample(trustNodeExample);
            if (trustNodes!=null&&trustNodes.size()>0){
                trustNode = trustNodes.get(0);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return trustNode;
    }
}
