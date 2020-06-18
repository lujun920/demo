/*
 * Dian.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package so.dian.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 测试基类
 *
 * @author ${guanzhong}
 * @version $Id: BaseController.java, v 0.1 2017年11月17日 下午9:03 Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class BaseTest {
    /**
     * 线程池服务
     */
    static ExecutorService arynExecutorService = new ThreadPoolExecutor(10, 50, 0L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());


    @Autowired
    public WebApplicationContext context;
    public MockMvc               mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

}