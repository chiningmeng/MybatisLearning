package dao;

import bean.Department;
import bean.Employee;

import java.util.List;

public interface DepartmentMapper {
    public Department getDeptById(Integer id);

    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);


}
