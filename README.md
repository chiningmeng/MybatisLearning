# MybatisLearning

## MyBatis 参数处理
1. 单个参数： 不会特殊处理，
~~~
#{参数名} 取出参数值
~~~ 
2. 多个参数：做特殊处理
~~~
如果操作
    方法：
        public Employee getEmpByIdAndLastName(Integer id,String lastNeme);
    取值：#{id}, #{lastname}
异常
org.apache.ibatis.binding.BindingException:
    Parameter 'id' not found. 
    Available parameters are [arg1, arg0, param1, param2]
~~~

~~~
多个参数会被封装成一个map，
key:param1,param2...,或参数的索引也可以
value: 传入的值
#{}就是从map中获得指定的key值
eg: select * from tbl_employee where id=#{param1} and last_name=#{param2}
~~~
或者明确指定封装参数时的Key
~~~
在mapper中的形参前使用注解@Param("")
key:使用@Param注解指定的值
value: 传入的值
#{指定的key} 取出相应的参数值
eg: public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastNeme);
~~~

3. POJO
如果多个参数正好是业务逻辑的数据类型，就可以直接传入pojo
~~~
#{属性名} 取出传入的pojo的属性值
~~~
4. Map
如果多个参数不是业务模型中的数据，没有对应的pojo，若不经常使用为了方便也可以传入map
~~~
#{key} 取出map中对应的值
~~~
5. TO
如果多个参数不是业务模型中的数据，若经常使用，则可以写一个TO(Transfer Object)数据传输对象
eg:分页查询
~~~
Page{
   int index;
   int size;
} 
~~~

特别注意：
如果Collection(List/Set)类型或数组，也会特殊处理
也是把传入的List或数组封装在map中
Key Collection
eg:
~~~
public Employee getEmpById(List<Integer> ids);
取值： 取出第一个id 的值 #{list[0]}
~~~

==========================参数值的获取===================================
~~~
区别：
select * from tbl_employee where id=${id} and last_name=#{lastName}
Preparing: select * from tbl_employee where id=1 and last_name=?
#{}  是以预编译的形式，将参数 设置到sql语句中； PrepareStatement；防止sql注入
${}  取出的值直接拼装在sql语句中，会有安全问题
大多数情况下，应该使用#{}
但分表,比如按照年份分表拆分
原生jdbc不支持占位符的地方就可以使用${} 拼装出2016_salary这种形式
 select * from ${year}_salary where ...
排序
 select * from tbl_employee order by ${f_name} ${order}
 
 
#{} 更多用法：
            规定参数的一些规则:
                javaType、jdbcType、mode(存储过程)、numericScale、
                resultMap、typeHandler、jdbcTypeName、expression
            jdbcType通常需要在某种特定的条件下被设置:
                在数据为null时，有些数据库可能不能识别mybatis对null的默认处理，比如Oracle

~~~