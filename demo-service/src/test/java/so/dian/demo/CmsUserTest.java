/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import so.dian.demo.dao.model.ModModule;
import so.dian.demo.service.ModModuleService;

/**
 *
 * TODO
 * @author ${baizhang}
 * @version $Id: CmsUserTest.java, v 0.1 2018-06-27 下午7:10 Exp $
 */
public class CmsUserTest extends BaseTest {
    @Autowired
    private ModModuleService service;

    @Test
//    @Transactional
    public void save(){
        ModModule modModule = new ModModule();
        modModule.setModuleName("ABC_AA33333");
        modModule.setModuleCode("AAAA");
        modModule.setMaxVersion(1);
        modModule.setMinVersion(1);
        service.saveRecord(modModule);
    }


    @Test
    public void get() {
        ModModule modModule = new ModModule();

        System.out.println(service.listRecord(modModule));
    }

}