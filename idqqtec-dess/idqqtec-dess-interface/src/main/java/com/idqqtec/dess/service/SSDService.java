package com.idqqtec.dess.service;

import com.idqqtec.dess.pojo.vo.BaseVo;

import java.util.List;

public interface SSDService {

    List<BaseVo> getSSDInformation(String tnIP);
}
