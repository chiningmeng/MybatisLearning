package dao;

import bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EmployeeMapper {
    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastNeme);
    public Employee getEmpByMap(Map<String,Object> map);

    public void addEmployee(Employee employee);
    public void updateEmployee(Employee employee);
    public boolean deleteEmployee(Integer id);
}
