import bean.Employee;
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
    public  void test() throws IOException {
        /*1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
                有数据源等一些运行环境信息
          2.sql映射文件：配置每一个sql,以及sql的封装规则
          3.将sql映射注册在全局配置文件中
          4.写代码：
                    1)根据全局配置文件得到SqlSessionFactory
                    2)使用SqlSession工厂，获取SqlSession对象使用他来增删查改
                        一个SqlSession代表和数据库的一次会话，用完要关闭
                    3)使用sql的唯一标识来告诉mybatis执行哪个sql,sql都保存在sql映射文件中的
                */

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取sqlSession实例，能直接执行已经映射的SQL语句
        SqlSession openSession = sqlSessionFactory.openSession();
        //sql的唯一标识：statement – Unique identifier matching the statement to use.
        //执行sql要用的参数：parameter – A parameter object to pass to the statement.
        try {
            Employee employee = openSession.selectOne("EmployeeMapper.selectEmp",1);
            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }
}
