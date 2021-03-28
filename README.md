# MybatisLearning
1. 获取sqlSessionFactory对象
2. 获取sqlSession对象
3. 获取接口的实现类对象，会为接口自动创建一个代理对象，代理对象取执行CURD

~~~xml
 <!--namespace:
    映射文件中的namespace是用于绑定Dao接口的，即面向接口编程。
    当你的namespace绑定接口后，你可以不用写接口实现类，
    mybatis会通过该绑定自动帮你找到对应要执行的SQL语句
    -->
    <!--id:  接口中的方法与映射文件中的SQL语句的ID一一对应
        resultType: 返回类型
        #{id}  : 从传递过来的参数中取id值


        public Employee getEmpById(Integer id);
    -->
<select id="getEmpById" resultType="bean.Employee">
    select id,last_name lastname,email,gender from tbl_employee where id = #{id}
</select>
~~~
1. 接口式编程：
     + 原来      Dao ===》 DaoImpl
 
     + mybatis:  Mapper  ===》 xxMapper.xml
2. SqlSession代表和数据库的一次会话：用完必须关闭
3. SqlSession和connection一样都是非线程安全==》每次使用都因该去获取新的对象
4. mapper接口没实现类，但mybatis会为这个接口生成一个代理对象
5. 两个重要的配置文件：
    + mybatis的全局配置文件：包含数据库连接池信息，事物管理器信息等系统运行环境信息
    + sql映射文件：保存了每一个sql语句的映射信息
~~~java
//将接口和xml进行绑定
 EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
~~~
 
好处：
+ 接口规定的方法拥有更强的类型检查
+ 将dao层与实现分离，更灵活
