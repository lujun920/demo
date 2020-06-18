/*
 * Dian.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package so.dian.demo.preload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import so.dian.demo.domain.Running;
import so.dian.mofa3.cache.service.CacheService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 负载检测服务状态接口
 * nginx调用/xxx/preload检测服务状态，如果服务出现异常，调用/xxx/running修改服务状态，
 *
 * @author ${baizhang}
 * @version $Id: PreloadRestful.java, v 0.1 2017-12-07 下午5:34 Exp $
 */
@Slf4j
@RestController
public class PreloadRestful {
    /**
     * nacos api 地址
     */
    private static final String NACOS_URI = "/nacos/v1/ns/instance";

    @Autowired
    private CacheService cacheService;

//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;


    @RequestMapping(value = "/demo/preload")
    public @ResponseBody
    Object preload(HttpServletResponse response) throws IOException {
        if (Running.running) {
            return "OK";
        }
        response.sendError(404);
        return null;
    }

    @RequestMapping("/demo/running")
    public @ResponseBody
    void running(@RequestParam Integer running) throws IOException {
        Running.running = running == 1;
        // 服务上线下线
//        onOrOffline(Running.running);
    }

    /**
     * 注册中心服务剔除
     * 运维配置脚本中默认3秒给服务平滑下线，一般任务已经足够继续完成
     * <p>
     * 该方式只能保持队列消费任务停止，如果出现线程阻塞时间过长，任务会被强制结束，出现数据丢失
     * （超过运维脚本配置，将会被kill -9强制结束）
     *
     * @return OK
     * @throws IOException InterruptedException
     */
    @RequestMapping("/demo/shutdown")
    public @ResponseBody
    String shutdown() throws IOException, InterruptedException {
        log.info("beatles server offline....");
        // task任务终止
        Running.running = false;
        // 注册中心剔除服务
//        onOrOffline(Running.running);
        // sleep 3秒响应，给未完成任务处理时间
        TimeUnit.SECONDS.sleep(3);
        return "OK";
    }


    /**
     * 服务上线下线
     * spring cloud 的特殊机制关系，无法使用注销实例的方式做服务下线，所以使用修改服务实例的方式实现服务上线下线
     * nacos open api：https://nacos.io/zh-cn/docs/open-API.html
     *
     * @param enable
     */
//    private void onOrOffline(final boolean enable) {
//        Map<String, Object> paramMap = new HashMap<>(9);
//        paramMap.put("serviceName", nacosDiscoveryProperties.getService());
//        paramMap.put("clusterName", nacosDiscoveryProperties.getClusterName());
//        paramMap.put("ip", nacosDiscoveryProperties.getIp());
//        paramMap.put("port", nacosDiscoveryProperties.getPort());
//        paramMap.put("enable", enable);
//        paramMap.put("namespaceId", nacosDiscoveryProperties.getNamespace());
//        String url = "http://" + nacosDiscoveryProperties.getServerAddr() + NACOS_URI;
//        Header[] headers = HttpHeader.custom().contentType(HttpConstants.APP_FORM_URLENCODED)
//                .acceptCharset(MofaConstants.UTF_8).build();
//        HttpBuilder httpBuilder = HttpClientPoolSingle.getInstance();
//        HttpConfig cfg = HttpConfig.custom().headers(headers).method(HttpMethods.PUT)
//                .client(httpBuilder.build()).url(url).map(paramMap);
//        try {
//            String result = HttpClientUtil.send(cfg);
//            log.info("onOrOffline：{}", result);
//        } catch (ThirdPartyException e) {
//            e.printStackTrace();
//        }
//        log.info("NACOS instance online: {}", enable);
//    }

    @RequestMapping(value = "/sysenv", produces = "text/html; charset=UTF-8")
    public @ResponseBody
    Object env() throws IOException {
        InetAddress inetAddr = InetAddress.getLocalHost();
        String canonical = inetAddr.getCanonicalHostName();
        String cacheKey = "SYSTEM_ENV_" + canonical;

        String json = cacheService.get(cacheKey);
        if (null != json) {
            return json;
        }

        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        StringBuilder sb = new StringBuilder();
        sb.append("======================里面包含很多高开销的方法，避免频繁调用该接口=========================</br>");
        OperatingSystemMXBean osm = ManagementFactory.getOperatingSystemMXBean();
        sb.append("服务器信息：</br>");
        sb.append("系统架构：").append(osm.getArch()).append("</br>");
        sb.append("CPU核心数：").append(osm.getAvailableProcessors()).append("</br>");
        sb.append("操作系统类型：").append(osm.getName()).append("</br>");
        sb.append("操作系统版本：").append(osm.getVersion()).append("</br>");
        sb.append("=====================================================================================</br>");
        sb.append("JVM初始内存：").append(usage.getInit() / 1024 / 1024).append("M").append("</br>");
        sb.append("JVM最大内存：").append(usage.getMax() / 1024 / 1024).append("M").append("</br>");
        sb.append("程序当前使用JVM内存：").append(usage.getUsed() / 1024 / 1024).append("M").append("</br>");
        sb.append("=====================================================================================</br>");
        sb.append("JAVA运行时内存信息：</br>");
        sb.append("内存总容量：").append(Runtime.getRuntime().totalMemory() / 1024 / 1024).append("M</br>");
        sb.append("最大内存容量：").append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append("M</br>");
        sb.append("空闲内存容量：").append(Runtime.getRuntime().freeMemory() / 1024 / 1024).append("M</br>");
        sb.append("=====================================================================================</br>");
        sb.append("内存使用情况(内存可能不是所有信息的总和)：</br>");
        sb.append("Heap Memory Usage: ").append(memorymbean.getHeapMemoryUsage()).append("</br>");
        sb.append("Non-Heap Memory Usage: ").append(memorymbean.getNonHeapMemoryUsage()).append("</br>");
        sb.append("=====================================================================================</br>");
        sb.append("JVM启动参数配置：</br>");
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        sb.append(inputArguments).append("</br>");

        sb.append("=====================================================================================</br>");

        ThreadMXBean tm = ManagementFactory.getThreadMXBean();
        sb.append("线程信息：</br>");
        sb.append("当前活跃线程数（包含守护线程和非守护线程）：").append(tm.getThreadCount()).append("</br>");
        sb.append("当前活跃的守护线程数：").append(tm.getDaemonThreadCount()).append("</br>");
        sb.append("系统峰值活跃线程数：").append(tm.getPeakThreadCount()).append("</br>");
        sb.append("系统启动以来创建的线程总数：").append(tm.getTotalStartedThreadCount()).append("</br>");
        sb.append("死锁线程：</br>");
        if (null != tm.findDeadlockedThreads()) {
            for (long tid : tm.findDeadlockedThreads()) {
                sb.append(tid).append(",");
            }
        }

        sb.append("</br>处于死锁线程的周期：</br>");
        if (null != tm.findMonitorDeadlockedThreads()) {
            for (long ft : tm.findMonitorDeadlockedThreads()) {
                sb.append(ft).append(",");
            }
        }

        sb.append("</br>=====================================================================================</br>");
        sb.append("内存池使用情况：</br>");
        List<MemoryPoolMXBean> mpmList = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mpm : mpmList) {
            for (String name : mpm.getMemoryManagerNames()) {
                sb.append(name).append("</br>");
            }
            sb.append("Usage：").append(mpm.getUsage()).append("</br>");
            sb.append("-------------------------------------------------------------------------------------</br>");
        }
        sb.append("=====================================================================================</br>");
        sb.append("GC：</br>");
        List<GarbageCollectorMXBean> gcmList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcm : gcmList) {
            for (String name : gcm.getMemoryPoolNames()) {
                sb.append(name).append("</br>");
            }
            sb.append(gcm.getName()).append("</br>");
            sb.append("GC次数：" + gcm.getCollectionCount()).append("GC累计耗时：").append(gcm.getCollectionTime()).append("ms</br>");
            sb.append("-------------------------------------------------------------------------------------</br>");
        }
        cacheService.set(cacheKey, sb.toString(), 10);
        return sb.toString();
    }
}