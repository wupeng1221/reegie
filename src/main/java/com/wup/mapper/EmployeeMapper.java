package com.wup.mapper;


import com.wup.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author brainwu
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-09-20 22:52:21
* @Entity com.wup.entity.Employee
*/
@Mapper
public interface EmployeeMapper {
    Employee findByUsername(Employee employee);
    Employee findByUsernameAndPassword(Employee employee);
    Employee findById(Integer id);
    void insert(Employee employee);
    List<Employee> getAllByName(String name);
    void update(Employee employee);

}




