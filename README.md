# 开发设计约定
## 目的
- 统一项目结构，减少开发沟通成本，降低项目上手难度 

## 约定内容
### 可执行范围
- 核心思想：面向接口编程
- 尽可能覆盖流程：`评审设计阶段`、`开发阶段`、`优化重构`、`CR阶段`

### 约定项
- 基本约定，准守`《阿里巴巴java开发手册》`
- 服务接口（service Interface）、应用API（controller）设计原则，参照`设计模式六大原则`
   - 六大原则在实际使用中会有很多相互矛盾的地方，我们在实际执行中，在保证主要思想不变的情况下，做一些变通约定
   - 单一职责原则（SRP）：一个接口明确职责，只做一件事情。如果因业务需要出现职责扩散，扩散范围在3个以内（建议最多2个，复杂情况允许3个），超过3个必须重构，避免无止境的扩散导致不可控的情况发生。
   - 开闭原则（OCP）：业务迭代，系统升级，必然会出现原来的接口不满足使用的情况，使用开闭原则，新增接口使用，有条件可以将接口使用者也替换成新接口，并考虑是否可以将接口标记为`@Deprecated`，标记为`@Deprecated`必须有新的实现，并且在注释上说明新接口的使用方法
   - 接口隔离原则（ISP）：尽量细化接口（参照单一原则，但也不可过细出现程序复杂度提高），类似于乐高，使用简单的几个组件可以完成一个接口模块，后续迭代中，如果认为该模块可以做为组件使用的话，遵循该职责斟酌是否能使用。原则，提高内聚，使用最少的方法去完成尽可能多的事情。
   - 迪米特法则（LKP）：调用者使用尽可能少的请求参数，得到自己想要的结果。`不同的业务请求避免使用同一个request或者response对象进行封装`, 因为共用会导致出现臃肿的request和response对象，不利于后期的扩展和维护，如果以后迭代出现不满足业务需要的接口，参照`开闭原则`来处理。
   - 里氏代换原则（LSP）：里氏代换原则在应用接口上不做要求（原因是约定不允许在request和response允许做任何业务相关的操作，仅允许用来做数据传输的载体），但是在`服务接口（service Interface）`需要遵守。
   - 依赖倒置原则（DIP）：该原则针对`服务接口（service Interface）`的设计。
   
- 默认项目生成代码不要直接修改，不满足需要直接新增dao方法
   - 项目默认可以使用代码生成工具生成基础代码结构，默认实现基础的CRUD方法，不满足需要新增dao方法和对象的mapper sql

- mapper sql，避免复杂的sql逻辑，设计阶段在业务逻辑设计中处理，复杂的sql可读性和效率都极差，出现性能问题优化困难。

- 清楚VO、BO、POJO（DO、DTO）等领域定义，`避免在对象中有涉及业务逻辑相关的操作`，对象可读性转换除外（将int值转为可读性高的文字）

- 明确各层职责，避免对象直接透传的情况（请求Request止步于Controller层，响应查询model止步于Service层，目前对象拷贝待完善，所以，能不偷懒就不偷懒）
   - 这条的执行成本实际上是很高，主要是做解耦，总感觉出现了很多不必要的参数拷贝（相似的对象），实际它们职责是完全不一样的，是对象领域划分的关键，能有效的界定业务边界。

- Controller接口对请求参数做必须的参数有效性校验，业务使用参数必须非空校验，遵守模板使用

- 数据表设计，至少应该包含`id（主键自增）`、`deleted`、`create_time`、`update_time`字段

- 对自己接口的服务能力有一定了解，能快速评估接口QPS是否满足接下来的业务流量。
   - 程序优化，短时间内无法实现，从长远来说必须考虑
   - 扩展服务机器，应对活动间突发流量，治标不治本
   - wrk 接口压测：https://github.com/wg/wrk

## 工程配套
### 代码生成
- 代码生成使用定制mybatis官方生成，保留基本的增、删（逻辑删除）、改、查方法，生成后可直接注入Service使用，减少基础方法开发量
- 删除操作为逻辑删除，deleted值为true
- 单条查询，单条查询生成增加limit 2，目的是避免大量数据全表扫描导致OOM，直接异常抛出方便排查定位问题。
- 多条查询，对于不确定数据增长量的查询，必须使用分页
- 界面化代码生成工程：git@git.dian.so:powergreen/mybatis-generator-gui.git，导入直接运行main即可

### mofa3
- 配套模块化引入使用，模板，代码生成依赖该工程
- 支持项
   - 缓存，redisson，使用自动装配，符合`cache.redis`自动启用（目前支持单机、主从模式，阿里云集群模式域名配置也可以使用）
   - 高效分布式id生成（阿里云1G redis TPS：1w1+），使用lua+redis实现，注入`SerialNumService`可直接使用，支持短位生成和长位生成
   - http client，使用okhttp3重构
   - 货币类支持，常用金额相关计算、转换
   - 时间处理`DateBuild`、`DateUtil`
   - JSON处理，使用jackson，使用中尽量统一转换三方jar，避免一些反序列化问题
   - 随机码生成`RandomCodeUtil`
   - xml处理`XmlUtil`
   - sql监控，执行异常、慢sql报警
   - 整合`pagehelper`分页支持
   - 统一异常处理
   - 代码性能埋点跟踪（手动埋点，不支持异步方法）
   - mofa3 工程：git@git.dian.so:powergreen/mofa3.git
   
项目结构，各个目录说明：
```tree
.
├── README.md  readme文件，项目说明
├── dian-facade  提供给接口使用方调用的client定义
│   ├── pom.xml  facade jar依赖，最少依赖原则，尽量避免使用三方jar，可能造成使用方jar冲突，如果无法避免，注意pom scope
│   └── src
│       ├── main
│       │   └── java
│       │       └── so
│       │           └── dian
│       │               └── demo
│       │                   ├── api 对外API定义
│       │                   │   ├── fallback  熔断处理
│       │                   │   │   └── package-info.java
│       │                   │   └── package-info.java
│       │                   ├── commons  公共内容
│       │                   │   ├── constants  常量包
│       │                   │   │   └── package-info.java
│       │                   │   └── enums  枚举，接口入参可以使用枚举，响应返回值，禁止使用返回枚举
│       │                   │       └── package-info.java
│       │                   ├── request  请求Request对象定义，接口设计注意遵守迪米特原则，建议对象组合不超过两层
│       │                   │   └── DemoRequest.java
│       │                   └── response  响应response对象定义，接口设计注意遵守迪米特原则，建议对象组合不超过三层
│       │                       └── package-info.java
│       └── test  test包，忽略
│           └── java
│               └── so
│                   └── dian
│                       └── demo
├── dian-service
│   ├── pom.xml  工程实际使用jar依赖，继承于父级依赖，不需要jar版本号，需要则在父级pom引入后再使用
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── so
│       │   │       └── dian
│       │   │           └── demo
│       │   │               ├── App.java  工程main入口类
│       │   │               ├── commons  公共内容
│       │   │               │   ├── constants  常量包
│       │   │               │   │   └── BizConstant.java
│       │   │               │   ├── enums  枚举包
│       │   │               │   │   └── package-info.java
│       │   │               │   └── utils  常用工具类，mofa3 utils已经包含很多常用工具类
│       │   │               │       └── package-info.java
│       │   │               ├── component  spring 组件包
│       │   │               │   ├── annotation  自定义注解定义
│       │   │               │   │   └── package-info.java
│       │   │               │   ├── config  应用配置，初始化配置、数据库、redis等
│       │   │               │   │   ├── HikariConfiguration.java
│       │   │               │   │   └── package-info.java
│       │   │               │   ├── filter  过滤器定义
│       │   │               │   │   └── package-info.java
│       │   │               │   ├── handler  handler定义
│       │   │               │   │   └── package-info.java
│       │   │               │   ├── interceptor  拦截器定义
│       │   │               │   │   └── package-info.java
│       │   │               │   └── listener  监听定义
│       │   │               │       └── package-info.java
│       │   │               ├── controller  Controller
│       │   │               │   └── BaseController.java
│       │   │               ├── dao  dao定义
│       │   │               │   ├── ModModuleDAO.java
│       │   │               │   └── model  对象model定义
│       │   │               │       └── ModModule.java
│       │   │               ├── domain  领域对象定义，设计多个子域继续分包
│       │   │               │   └── Running.java
│       │   │               ├── preload  运维接口定义
│       │   │               │   └── PreloadRestful.java
│       │   │               ├── remote  接口调用，禁止直接从Service调用外部api，在该包下处理好对象返回后使用（包括异常等）
│       │   │               │   └── package-info.java
│       │   │               ├── service  应用Service
│       │   │               │   ├── ModModuleService.java
│       │   │               │   └── impl 应用Service实现
│       │   │               │       └── ModModuleServiceImpl.java
│       │   │               └── task  任务
│       │   │                   ├── PoolTask.java
│       │   │                   └── TaskListener.java
│       │   └── resources
│       │       ├── application-local.yml  对应环境配置文件
│       │       ├── bootstrap.yml  应用引导主配置
│       │       ├── logback-spring.xml  logback日志配置
│       │       ├── mapper  mybatis Mapper目录
│       │       │   └── ModModuleDAO.xml
│       │       └── script  应用使用脚本目录：sql脚本，lua脚本等
│       │           └── table.sql
│       └── test  测试目录，可忽略
│           ├── java
│           │   └── so
│           │       └── dian
│           │           └── demo
│           │               ├── AnnotationTest.java
│           │               ├── BaseTest.java
│           │               ├── LuaQpsTest.java
│           │               ├── MainTest.java
│           │               ├── TestStringEncryptor.java
│           │               ├── lua
│           │               │   └── 123.lua
│           │               └── redisson
│           │                   └── RedissonTest.java
│           └── resources
├── enjoy.MD
└── pom.xml  父级pom，只包含jar的引用依赖，不直接引入使用


```

[设计模式六大原则](https://blog.csdn.net/qq_28055429/article/details/51507170)
