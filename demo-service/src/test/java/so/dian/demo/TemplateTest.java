/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo;

import so.dian.mofa3.template.controller.AbstractControllerTemplate;
import so.dian.mofa3.template.controller.ControllerCallback;
import so.dian.mofa3.template.controller.ControllerTemplate;

/**
 * TODO
 *
 * @author baizhang
 * @version: TemplateTest.java, v 0.1 2019-04-16 20:56 Exp $
 */
public class TemplateTest {
    public void template() {
        ControllerTemplate controllerTemplate = new AbstractControllerTemplate<>();
        controllerTemplate.execute(new ControllerCallback<String>() {
            @Override
            public void checkParam() {

            }

            @Override
            public void buildContext() {

            }

            @Override
            public String execute() {
                return null;
            }
        });
    }

    public static void main(String[] args) {
        TemplateTest templateTest= new TemplateTest();
        templateTest.template();
    }
}