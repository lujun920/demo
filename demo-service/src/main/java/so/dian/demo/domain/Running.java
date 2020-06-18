/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo.domain;

/**
 * 任务控制器
 * 服务下线，停止任务（依赖于运维脚本）
 *
 * @author ${baizhang}
 * @version $Id: Running.java, v 0.1 2018-08-29 下午5:23 Exp $
 */
public class Running {
    public static volatile boolean running = true;
}