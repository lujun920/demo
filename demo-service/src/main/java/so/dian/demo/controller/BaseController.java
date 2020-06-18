/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import so.dian.mofa3.cache.service.SerialNumService;
import so.dian.mofa3.template.controller.ControllerTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 控制器基类
 *
 * @author ${baizhang}
 * @version $Id: BaseController.java, v 0.1 2018-04-16 下午2:47 Exp $
 */
@SuppressWarnings("unused")
@RestController
public abstract class BaseController {

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 操作模板
     */
    @Resource
    @Qualifier("AbstractControllerTemplate")
    public ControllerTemplate template;

    @Autowired
    public SerialNumService serialNumService;

    @Autowired
    public RedissonClient redissonClient;

}