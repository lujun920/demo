/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import so.dian.demo.dao.model.ModModule;
import so.dian.demo.service.ModModuleService;
import so.dian.mofa3.lang.domain.Result;
import so.dian.mofa3.lang.exception.BizProcessException;
import so.dian.mofa3.lang.exception.CheckParamException;
import so.dian.mofa3.lang.exception.CustomCodeException;
import so.dian.mofa3.lang.exception.ServiceIsNotAvailableException;
import so.dian.mofa3.lang.exception.ThirdPartyException;
import so.dian.mofa3.lang.util.JsonUtil;
import so.dian.mofa3.template.controller.ControllerCallback;

import java.io.IOException;

/**
 *
 * TODO
 * @author ${baizhang}
 * @version $Id: DemoController.java, v 0.1 2018-04-16 下午9:00 Exp $
 */
@Slf4j
@RestController
public class DemoController extends BaseController{

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Result createPay() {
        return template.execute(new ControllerCallback<String>() {
            // 参数检查
            @Override
            public void checkParam() {
                if(1==1){
                    //throw new CheckParamException("交易订单号不能为空");
                    //throw new BizProcessException("交易规则不符合");
                    throw new ServiceIsNotAvailableException("服务不可用");
                    //throw new ThirdPartyException("支付宝接口调用异常");
                    //throw new IOException("io exception");
                    //SystemException

                    //throw new CustomCodeException("3005","支付宝接口调用异常");
                }
                //System.out.println("checkParam");
                //log.info("logback");
            }

            // 参数构建
            @Override
            public void buildContext() {
                System.out.println("buildContext");
            }

            // 幂等处理
            @Override
            public void checkConcurrent(){

            }

            // 执行
            @Override
            public String execute() {


                return "aabbccddeeffgg";
            }
        });
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result test(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
        if (id==1) {
            throw new CheckParamException("交易订单号不能为空");
            //throw new BizProcessException("交易规则不符合");
            //throw new ServiceIsNotAvailableException("服务不可用");
            //throw new ThirdPartyException("支付宝接口调用异常");
            //throw new IOException("io exception");
            //SystemException

            //throw new CustomCodeException("3005","支付宝接口调用异常");
        }
        if(id==2){
            throw new BizProcessException("交易规则不符合");
        }
        if (id == 3) {
            throw new ServiceIsNotAvailableException("服务不可用");
        }

        if (id == 4) {
            throw new ThirdPartyException("支付宝接口调用异常");
        }

        if (id == 5) {
            try {
                throw new IOException("io exception");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(id== 6){
            throw new CustomCodeException("3008", "CustomCodeException");
        }

        if(id== 7){
            int a = 2 / 0;
        }



        return new Result();
    }

    @RequestMapping(value = "/adc/{str}")
    public @ResponseBody
    String list(@PathVariable String str) {

//        List<ModModule> list =  modModuleService.listRecord(new ModModule());

        Page<ModModule> page = PageHelper.offsetPage(0, 3).doSelectPage(() -> modModuleService.listRecord(new ModModule()));

        return JsonUtil.beanToJson(page.getResult());
    }

    @Autowired
    private ModModuleService modModuleService;
}