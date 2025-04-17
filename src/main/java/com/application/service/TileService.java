package com.application.service;

import com.application.entity.DemoUser;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

public interface TileService {

    Object getTerrainType(String type,Integer z, Integer x, Integer y);
    Object getImageType(String layer,String type,String grid,Integer z, Integer x, Integer y);
}
