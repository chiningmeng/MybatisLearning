import bean.Department;
import bean.Employee;
import dao.DepartmentMapper;
import dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
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
            //EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

//            Employee employee = mapper.getEmpById(1);
//            System.out.println(employee);

//            Employee empAndDept = mapper.getEmpAndDept(1);
//            System.out.println(empAndDept);
//            System.out.println(empAndDept.getDept());

//            Employee employee = mapper.getEmpByIdStep(1);
//            System.out.println(employee);
            //System.out.println(employee.getDept());

            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
           /* Department department = mapper.getDeptByIdPlus(1);
            System.out.println(department);
            System.out.println(department.getEmps());*/
            Department department = mapper.getDeptByIdStep(1);
            System.out.println(department);
           // System.out.println(department.getEmps());
            //手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }

    }
}
