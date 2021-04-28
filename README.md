# MybatisLearning

~~~xml
<!--
        1.mybatis可以使用properties来引入外部properties配置文件的内容
        resource 引入类路径下的资源
        url 引入网络路径或磁盘路径下的资源
        （了解即可，一般都交给spring处理）
    -->
    <properties resource="dbconfig.properties"></properties>
    <!--
        2.settings包含很多重要的设置项
        setting:用来设置每一个设置项
            name : 设置项名
            value : 设置取值
    -->
    <settings>
        <!--是否开启驼峰命名自动映射,默认为false-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <!--
        3.typeAliases : 别名处理器，可以为我们的Java类型起别名
            注：别名不区分大小写
          但一般还是写全类名
    -->
    <typeAliases>
        <!--1、typeAlias 为某个Java类型起别名
                type: 指定要起别名的类型全类名; 在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名
        -->
        <!--<typeAlias type="bean.Employee" alias="employee"/>-->

        <!--2、也可以用 package 指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean
            name: 指定包名（为 当前包以及下面所有的后代包的每一个类起一个默认别名（原类名小写）
        -->
        <package name="bean"/>
        <!--3、批量起别名的情况下，使用@Alias注解为某个类型指定新的别名-->
    </typeAliases>
    <!--4.environments 为mybatis配置多种环境,defailt指定使用某个环境，可以快速切换
            environment 配置一个具体的环境信息，必须有两个标签； id代表当前环境的唯一标识
            transactionManager : 事务管理器
                type : 事物管理器类型
            dataSource : 数据源
                type: 数据源类型; UNPOOLED\POOLED\JNDI
    -->
    <!--<environments default="test">
        <environment id="test">
            <transactionManager type=""></transactionManager>
            <dataSource type=""></dataSource>
        </environment>
        <environment id="development">
            <transactionManager type=""></transactionManager>
            <dataSource type=""></dataSource>
        </environment>
    </environments>-->
    <!--5.databaseIdProvider 数据库厂商标识-->
    <!--6.mappers 映射器，将sql映射注册到全局配置中-->
<!--    <mappers>

            mapper : 注册一个sql映射
                resource : 引用类路径下的sql映射文件
                url : 引用网络路径或磁盘路径下的sql文件
                class : 引用（注册）接口
                    1、有sql文件，映射文件名必须和接口同名，并且放在与接口同一目录下
                    2、没有sql文件，所有的sql都是利用注释写在接口上
        <mapper url=""></mapper>
        <mapper resource=""></mapper>
        <mapper class=""></mapper>
    </mappers>-->
~~~
