# MybatisLearning
利用SqlSession 的 selctOne进行简易查询

1. 根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
                有数据源等一些运行环境信息
2. sql映射文件：配置每一个sql,以及sql的封装规则
3. 将sql映射注册在全局配置文件中
4. 写代码：
	1. 根据全局配置文件得到SqlSessionFactory
	2. 使用SqlSession工厂，获取SqlSession对象使用他来增删查改， 一个SqlSession代表和数据库的一次会话，用完要关闭
	3. 使用sql的唯一标识来告诉mybatis执行哪个sql,sql都保存在sql映射文件中的
                    
                    
建表脚本如下：
~~~mysql
CREATE TABLE tbl_employee(
	id INT(11) PRIMARY KEY AUTO_INCREMENT,
	last_name VARCHAR(255),
	gender CHAR(1),
	email VARCHAR(255)
)
~~~
