参考文档：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Result_Maps

resultMap:自定义结果集映射规则
~~~
<!--resultMap:自定义结果集映射规则-->
    <!--自定义某个JavaBean的封装规则
            id 唯一id方便引用
            type 自定义规则的Java类型
    -->
    <resultMap id="MyEmp" type="bean.Employee">
        <!--指定主键列的封装规则
         id定义主键会有底层优化
                column 指定哪一列
                property 指定对应的JavaBean属性
        -->
        <id column="id" property="id"/>

        <!--定义普通列封装规则-->
        <result column="last_name" property="lastName"/>
        <!--其他不指定的列会自动封装：但最好都写上-->
        <result column="email" property="email"/>
        <result column="gender" property="gender"/>
    </resultMap>

    <select id="getEmpById" resultMap="MyEmp">
        select * from tbl_employee where id=#{id}
    </select>
~~~
resultMap关联查询 级联属性封装结果集
#### 场景一： 查询Employee的同时查询员工对应的部门
~~~
<!--场景一：
            查询Employee的同时查询员工对应的部门
            Employee==Department
            一个员工有与之对应的部门信息
            id last_name gender d_id did dept_name
    -->
~~~
~~~

    <!--联合查询：级联属性封装结果集-->
    <resultMap id="MyEmp" type="bean.Employee">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="gender" property="gender"/>
        <result column="did" property="dept.id"/><!--dept是employee的属性之一，把部门表中查询的数据封装到employee的dept中-->
        <result column="dept_name" property="dept.departmentName"/>
    </resultMap>
~~~

association定义关联对象封装规则
~~~
  <resultMap id="MyEmp2" type="bean.Employee">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="gender" property="gender"/>
        <!--association可以指定联合的JavaBean对象
                property 指定哪个属性是联合的对象
                javaType 指定这个属性的对象类型（不能省略！）
        -->
        <association property="dept" javaType="bean.Department">
            <id column="did" property="id"/>
            <result column="dept_name" property="departmentName"/>
        </association>
    </resultMap>
~~~

association进行分步查询
~~~
 <!--使用association进行分步查询
            1.先按照员工id查询员工信息
            2.根据查询员工信息中的d_id查询部门信息
            3.把部门信息设置到员工信息中
    -->
    <resultMap id="MyEmpByStep" type="bean.Employee">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="gender" property="gender"/>
        <!--association 定义关联对象的封装规则
                select 表明当前属性是调用select指定的方法查出的结果
                column 指定将哪一列的值传给这个方法
            流程：使用select指定的方法（传入column指定的这列参数的值）查出的对象，并封装给property指定的属性
        -->
        <association property="dept"
                     select="dao.DepartmentMapper.getDeptById"
                     column="d_id">
        </association>
    </resultMap>
    <!--public Employee getEmpByIdStep(Integer id);-->
    <select id="getEmpByIdStep" resultMap="MyEmpByStep">
        select * from tbl_employee where id=#{id}
    </select>
~~~

关联查询 分布查询&延迟加载
~~~
  <!--可以使用延迟加载
        Employee ==》Dept
            每次查询Employee对象时，都将一起查询出来
            部门信息在使用时再去查询
                即在分步查询的基础上加上两个配置
                    在mybatis配置文件中settings中加入
                        lazyLoadingEnabled：延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 fetchType 属性来覆盖该项的开关状态。
                        aggressiveLazyLoading：开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 lazyLoadTriggerMethods)。
    -->
~~~
~~~
<!--要显式地指定每个需要更改的配置值，即使是默认的。防止版本更新带来的问题-->
<!--mybatis的配置文件中settings中加上一下两个设置-->
        <!--延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 fetchType 属性来覆盖该项的开关状态。-->
        <setting name="lazyLoadingEnabled" value="true"/>
        
        <!--开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 lazyLoadTriggerMethods)。-->
        <setting name="aggressiveLazyLoading" value="false"/>
~~~
#### 场景二：查询部门时将部门对应的所以员工信息也查询出来
(在DepartmentMapper中)
1.使用collection定义关联集合类型的属性的封装规则
~~~
 <!--
    public class Department {
        private Integer id;
        private String departmentName;
        private List<Employee> emps;

    did dept_name || eid last_name email gender
    -->
    <resultMap id="MyDept" type="bean.Department">
        <id column="did" property="id"/>
        <result column="dept_name" property="departmentName"/>
        <!--collection 定义关联集合类型的属性的封装规则-->
        <collection property="emps" ofType="bean.Employee">
            <!--定义这个集合中元素的封装规则-->
            <id column="eid" property="id"/>
            <result column="last_name" property="lastName"/>
            <result column="email" property="email"/>
            <result column="gender" property="gender"/>
        </collection>
    </resultMap>
    <!--public Department getDeptByIdPlus(Integer id);-->
    <select id="getDeptByIdPlus" resultMap="MyDept">
        SELECT d.id did,d.dept_name dept_name,
               e.id eid,e.last_name last_name,e.email email,e.gender gender
        FROM tbl_dept d
                 LEFT JOIN tbl_employee e
                           ON d.id=e.d_id
        WHERE d.id=#{id};
    </select>
~~~
2. 使用collection分布查询&延迟加载（和法一结果一样）
~~~
DepartmentMapper.xml中：

    <resultMap id="MyDeptStep" type="bean.Department">
        <id column="id" property="id"/>
        <id column="dept_name" property="departmentName"/>
        <collection property="emps"
                    select="dao.EmployeeMapper.getEmpsByDeptId"
                    column="id"></collection>
    </resultMap>
    <!--public Department getDeptByIdStep(Integer id);-->
    <select id="getDeptByIdStep" resultMap="MyDeptStep">
        select id,dept_name from tbl_dept where id=#{id}
    </select>

EmployeeMapper.xml中：
<!--被DepartmentMapper中的resultMap MyDeptStep的collection调用-->
    <!--public List<Employee> getEmpsByDeptId(Integer id);-->
    <select id="getEmpsByDeptId" resultType="bean.Employee">
        select * from tbl_employee where d_id=#{deptId}
    </select>
~~~
扩展(这里懒加载不知道为什么没有生效)
~~~
 <!--
    多列的值传过去；
        将多列的值封装map传递
        column=”{key1=column1,key2=column2}"

        例如：<collection property="emps"
                    select="dao.EmployeeMapper.getEmpsByDeptId"
                    column="{deptId=id}"></collection>
    fetchType="lazy" 表示使用延迟加载
                    -lazy 延迟
                    -eager 立即
    -->
~~~
鉴别器discrimination（但目前个人认为不如写在Java代码里）
~~~
 <!--鉴别器 mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为
            eg:
                封装Employee：
                    如果查出女生，则查出部门信息，否则不查询
                    如果男生，把last_name这一列的值赋给email
    -->
    <resultMap id="MyEmpDis" type="bean.Employee">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="email" property="email"/>
        <result column="gender" property="gender"/>

        <!--
            column 指定判定的列名
            javaType 列值对应的Java类型
        -->
        <discriminator javaType="string" column="gender">
            <!--女生-->
            <case value="0" resultType="bean.Employee">
                <association property="dept"
                             select="dao.DepartmentMapper.getDeptById"
                             column="d_id">
                </association>
            </case>
            <!--男-->
            <case value="1" resultType="bean.Employee">
                <id column="id" property="id"/>
                <result column="last_name" property="lastName"/>
                <result column="last_name" property="email"/>
                <result column="gender" property="gender"/>
            </case>
        </discriminator>
    </resultMap>
~~~