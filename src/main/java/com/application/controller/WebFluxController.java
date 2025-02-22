package com.application.controller;

import com.application.service.TileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * @author
 * @create 2024/10/19 下午 08:05
 * @Description
 **/
@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")

public class WebFluxController {
    @Autowired
    private TileService tileService;

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello, Mono!");
    }
    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "World", "From", "WebFlux", "Controller", "in", "Spring Boot 3!");
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
    public Mono<Object> getImageType(@PathVariable String layer, @PathVariable String type, @PathVariable String grid, @PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        log.debug("invoke getImageType, layer:"+layer+",type:"+type+",grid:"+grid+",z:"+z+",x:"+x+",y:"+y);
        //System.out.printf("before - %s%n", Thread.currentThread()) ;
        log.debug("before - " + Thread.currentThread());
        Mono<Object> tileRes = null;
        try{
            tileRes = tileService.getImageTypeMono(layer,type,grid,z,x,y);
        }catch (Exception e){

        }
        //System.out.printf("end - %s%n", Thread.currentThread()) ;
        log.debug("end - " + Thread.currentThread());
        return tileRes;
    }
}
