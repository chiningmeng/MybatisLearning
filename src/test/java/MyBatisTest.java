import bean.Employee;
import dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;

public class MyBatisTest {


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
            Employee employee = mapper.getEmpById(1);
            //System.out.println(mapper.getClass()); class com.sun.proxy.$Proxy6 代理对象
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }
}
