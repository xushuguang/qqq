package com.idqqtec.nms.service.impl;

import com.idqqtec.nms.common.jedis.JedisClientPool;
import com.idqqtec.nms.common.utils.JsonUtil;
import com.idqqtec.nms.dao.KeybufferMapper;
import com.idqqtec.nms.dao.NetElementMapper;
import com.idqqtec.nms.pojo.po.Keybuffer;
import com.idqqtec.nms.pojo.po.KeybufferExample;
import com.idqqtec.nms.pojo.po.NetElementExample;
import com.idqqtec.nms.service.KeyBufferService;
import com.idqqtec.nms.pojo.dto.KeyBufferDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class KeyBufferServiceImpl implements KeyBufferService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private KeybufferMapper keybufferDao;
    @Autowired
    private JedisClientPool jedisClientPool;
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    /**
     * 根据当前TN网元名，对端TN以及时间获取Keybuffer
     * @param neName
     * @param pairId
     * @param time
     * @return int
     */
    @Override
    public int getKeyBuffer(String neName,Long pairId,Long time) {
        int result = 0;
        try {
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            String localTNIP = netElementDao.selectByExample(netElementExample).get(0).getNeIp();
            String pairTNIP = netElementDao.selectByPrimaryKey(pairId).getNeIp();
            //时间往前推一秒
            String time1 = dateToString.format(time-1000);
            KeybufferExample keybufferExample = new KeybufferExample();
            keybufferExample.createCriteria().andTnIpEqualTo(localTNIP).andPairTnIpEqualTo(pairTNIP).andTimeEqualTo(time1);
            List<Keybuffer> keybuffers = keybufferDao.selectByExample(keybufferExample);
            if (keybuffers!=null&&keybuffers.size()>0){
                result = Integer.parseInt(keybuffers.get(0).getKeybuffer());
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 定时删除KeyRate和KeyBuffer,每天00点删除
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void delALLKeyBuffer(){
        keybufferDao.deleteByExample(new KeybufferExample());
    }

    /**
     * 根据当前的TN网元名和对端的TNid获取这一对TN的所有Keybuffer
     * @param neName
     * @param pairId
     * @return list
     */
    @Override
    public List<Keybuffer> getAllKeyBuffer(String neName, Long pairId) {
        List<Keybuffer> keybuffers = null;
        try{
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            String localTNIP = netElementDao.selectByExample(netElementExample).get(0).getNeIp();
            String pairTNIP = netElementDao.selectByPrimaryKey(pairId).getNeIp();
            KeybufferExample keybufferExample = new KeybufferExample();
            keybufferExample.createCriteria().andTnIpEqualTo(localTNIP).andPairTnIpEqualTo(pairTNIP);
            keybuffers = keybufferDao.selectByExample(keybufferExample);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keybuffers;
    }

    /**
     * 定时把所有KeyBuffer放入缓存
     */
    @Override
    @Scheduled(fixedRate = 1000*60*5)
    public void setAllKeyBufferToRedis() {
        try {
            //先去重查询所有的TN和对端TN
            List<KeyBufferDto> keyBufferDtos = keybufferDao.distinctTNandPairTN();
            //循环遍历
            if (keyBufferDtos!=null&&keyBufferDtos.size()>0){
                for (KeyBufferDto keyBufferDto : keyBufferDtos){
                    //设置key
                    NetElementExample netElementExample1 = new NetElementExample();
                    netElementExample1.createCriteria().andNeIpEqualTo(keyBufferDto.getTnIp());
                    Long tnId = netElementDao.selectByExample(netElementExample1).get(0).getId();
                    NetElementExample netElementExample2 = new NetElementExample();
                    netElementExample2.createCriteria().andNeIpEqualTo(keyBufferDto.getPairTnIp());
                    Long pairTnId = netElementDao.selectByExample(netElementExample2).get(0).getId();
                    String key = Long.toString(tnId)+":"+Long.toString(pairTnId);
                    //根据tn_ip和pair_tn_ip查询KeyBuffer
                    KeybufferExample keybufferExample = new KeybufferExample();
                    keybufferExample.createCriteria().andTnIpEqualTo(keyBufferDto.getTnIp()).andPairTnIpEqualTo(keyBufferDto.getPairTnIp());
                    List<Keybuffer> list = keybufferDao.selectByExample(keybufferExample);
                    String value = JsonUtil.objectToJson(list);
                    //存到Redis缓存中
                    jedisClientPool.set(key,value);
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 从Redis缓存中获取当前一对TN的所有KeyBuffer
     * @param neName
     * @param pairId
     * @return String
     */
    @Override
    public String getAllKeyBufferFromRedis(String neName, Long pairId){
        String s = null;
        try {
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            Long tnId = netElementDao.selectByExample(netElementExample).get(0).getId();
            String key = Long.toString(tnId)+":"+Long.toString(pairId);
            s = jedisClientPool.get(key);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return s;
    }
}
