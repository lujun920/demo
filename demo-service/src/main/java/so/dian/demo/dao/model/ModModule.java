/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo.dao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import so.dian.mofa3.template.model.BaseModel;

/**
 * TODO
 *
 * @author MBG工具生成
 * @version $Id: ModModule.java, v 0.1 2019-05-08 10:37:20 Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModModule extends BaseModel<ModModule> {
    /** serialVersionUID */
    private static final long serialVersionUID = 171949864073377574L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 组件名称
     */
    private String moduleName;

    /**
     * 组件code
     */
    private String moduleCode;

    /**
     * 组件描述
     */
    private String moduleDesc;

    /**
     * 组件最低支持版本
     */
    private Integer minVersion;

    /**
     * 组件最高支持版本
     */
    private Integer maxVersion;
}