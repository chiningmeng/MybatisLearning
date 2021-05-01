import bean.Employee;
import dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class MyBatisTest {

/*
* 测试增删改
* 1、mybatis 允许增删改直接定义一下返回类型
*           Integer Long Boolean
* */
    @Test
    public void test() throws IOException {
        //1.获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            //3.获取接口的实现类对象
            //会为接口自动创建一个代理对象，代理对象取执行CURD
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

//            Employee employee = new Employee(null,"jerry","@jerry@163.com","0");
//            mapper.addEmployee(employee);
            //mapper.updateEmployee(employee);
            //boolean update = mapper.deleteEmployee(19);
//            System.out.println(employee);
//            Employee employee = mapper.getEmpByIdAndLastName(1,"tom");
//            System.out.println(employee);
            Map<String,Object> map = new HashMap<>();
            map.put("id",1);
            map.put("lastName","tom");
            map.put("tableName","tbl_employee");
            Employee employee = mapper.getEmpByMap(map);
            System.out.println(employee);
            //手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }

    }
}
