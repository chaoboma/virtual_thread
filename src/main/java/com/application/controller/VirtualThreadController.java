package com.application.controller;


import com.application.common.Result;
import com.application.service.VirtualThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
@Slf4j
public class VirtualThreadController {
    @Autowired
    private VirtualThreadService virtualThreadService;
    /**
     * query
     * @param
     * @return
     */

    @GetMapping(value = "/virtualThread")
    public Result<Object> virtualThread() throws InterruptedException {
        log.debug("start");
        virtualThreadService.testVirtualThreadTask();
        //virtualThreadService.testNormalThreadTask();
        log.debug("end");
        return Result.success();

    }
    @GetMapping("/index")
    public Result<Object> index() throws InterruptedException {
        System.out.printf("before - %s%n", Thread.currentThread()) ;
        //TimeUnit.MILLISECONDS.sleep(100) ;
        Thread.sleep(100);
        System.out.printf("after - %s%n", Thread.currentThread()) ;
        return Result.success("task - default...");
    }
    @GetMapping(value = "/test")
    public Result<Object> test() throws InterruptedException {
        log.debug("start");
        //virtualThreadService.testVirtualThreadTask();
        //virtualThreadService.testNormalThreadTask();
        log.debug("end");
        return Result.success();

    }
}
