package com.wup.service;

import com.wup.common.PageBean;
import com.wup.entity.Employee;

/**
* @author brainwu
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-09-20 22:52:21
*/
public interface EmployeeService {
    Employee findByUsername(Employee employee);
    Employee findByUsernameAndPassword(Employee employee);
    Employee findById(Integer id);
    void save(Employee employee);
    PageBean<Employee> list(Integer page, Integer pageSize, String name);
    void update(Employee employee);
}
