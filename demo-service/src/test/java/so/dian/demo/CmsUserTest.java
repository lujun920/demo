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
        for(int i = 0; i< 10; i++){
            ModModule modModule = new ModModule();
            modModule.setModuleName("ABC_00000"+i);
            modModule.setModuleCode("AAAA_"+i);
            modModule.setMaxVersion(1);
            modModule.setMinVersion(1);
            service.saveRecord(modModule);
        }

    }


    @Test
    public void get() {
        ModModule modModule = new ModModule();

        System.out.println(service.listRecord(modModule));

        modModule.setId(33L);
        System.out.println(service.getRecord(modModule));
    }

}