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

好处：
+ 接口规定的方法拥有更强的类型检查
+ 将dao层与实现分离，更灵活
