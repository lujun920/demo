package so.dian.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 程序启动入口
 *
 * @author ${baizhang}
 * @date 17/11/20 上午10:16
 */
@EnableAsync
@SpringCloudApplication
@EnableEurekaClient
@EnableTransactionManagement
public class App {
    public static void main( String[] args ) {
        //SpringApplication.run(App.class, args);

//        ApplicationContext ctx = SpringApplication.run(App.class, args);
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        System.out.println("所有beanNames个数：" + beanNames.length);
//        System.out.println("====================================");
//        for (String bn:beanNames) {
//            System.out.println(bn);
//        }
//        System.out.println("====================================");

        SpringApplication app = new SpringApplication(App.class);
//        app.addListeners(
//                new TaskListener()
//        );
        app.run(args);
    }

    /**
     * 添加supportedMediaTypes，可解决 'Content-Type' cannot contain wildcard type '*' 的问题
     *
     * @return HttpMessageConverters
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 时间类型格式化
        objectMapper.setDateFormat(smt);
        // 属性为NULL不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 处理json中缺少映射字段异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        List<MediaType> supportedMediaTypes = new ArrayList<>(25);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return mappingJackson2HttpMessageConverter;
    }

    // 线程池初始化，spring管理线程池
    @Bean(name = "cmqAsyncPool")
    public TaskExecutor workExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("ASYNC-POOL-");
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(500);
        threadPoolTaskExecutor.afterPropertiesSet();
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }
}
