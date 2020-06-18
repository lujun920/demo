/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import so.dian.demo.dao.model.ModModule;
import so.dian.mofa3.template.dao.BaseDAO;


/**
 * 继承于BaseDAO，默认有五个方法实现
 * listRecord、getRecord、saveRecord、removeRecord、updateRecord
 *
 * @author MBG工具生成
 * @version $Id: ModModuleDAO.java, v 0.1 2019-05-08 10:37:20 Exp $
 */
@Mapper
public interface ModModuleDAO extends BaseDAO<ModModule> {
}