package com.application.controller;

import com.application.service.TileService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

/**
 * TileController
 * @Author: chaobo
 * 2024-11-05 16:03
 */
@RestController
@CrossOrigin(origins = "*")
//@Api(value = "TileController", tags = { "TileController" })
@Slf4j
public class TileController {
    @Autowired
    private TileService tileService;
    @Value("${terrain.directory}")
    private String terrainDirectory;

    @Autowired
    private ResourceLoader resourceLoader;
    /**
     * 获取地形terrain瓦片
     * @param
     * @return
     */
    //@ApiOperation("获取地形terrain瓦片")
    //@UserLoginToken
    @ResponseBody
    @GetMapping(value="/terrain/{type}/{z}/{x}/{y}.terrain",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Object getTerrainType(@PathVariable String type, @PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        log.debug("invoke getTerrainType ,type:"+type+",z:"+z+",x:"+x+",y:"+y);
        Object tileRes = null;
        try{
            tileRes = tileService.getTerrainType(type,z,x,y);
        }catch (Exception e){

        }

        return tileRes;
    }

    /**
     * 获取image瓦片
     * @param
     * @return
     */
    //@ApiOperation("获取image瓦片")
    //@UserLoginToken
    @ResponseBody
    @GetMapping(value="/image/{layer}/{type}/{grid}/{z}/{x}/{y}.{format}",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Object getImageType(@PathVariable String layer,@PathVariable String type,@PathVariable String grid, @PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        log.debug("invoke getImageType, layer:"+layer+",type:"+type+",grid:"+grid+",z:"+z+",x:"+x+",y:"+y);
        //System.out.printf("before - %s%n", Thread.currentThread()) ;
        Object tileRes = null;
        try{
            tileRes = tileService.getImageType(layer,type,grid,z,x,y);
        }catch (Exception e){

        }
        //System.out.printf("end - %s%n", Thread.currentThread()) ;
        return tileRes;
    }


    /**
     * 获取layer.json
     * @param
     * @return
     */
    //@ApiOperation("获取layer.json")
    //@UserLoginToken
    @ResponseBody
    @GetMapping(value="/terrain/{type}/layer.json",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object getLayerJson(@PathVariable String type){
        Resource resource = resourceLoader.getResource("file:"+terrainDirectory+type+"-layer.json");
        String line = "";
        try{
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }


        return line;
    }
}
