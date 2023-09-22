package com.wup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wup.common.PageBean;
import com.wup.entity.Employee;
import com.wup.mapper.EmployeeMapper;
import com.wup.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;


/**
* @author brainwu
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-09-20 22:52:21
*/
@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Employee findByUsername(Employee employee) {
        return employeeMapper.findByUsername(employee);
    }

    @Override
    public Employee findByUsernameAndPassword(Employee employee) {
        // 对前端传来的密码明文进行MD5加密，再在数据库中查找
        employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
        return employeeMapper.findByUsernameAndPassword(employee);
    }

    @Override
    public void save(Employee employee) {
        // 密码进行md5加密
        // 用户的初始化密码 123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 补全数据
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Integer) httpServletRequest.getSession().getAttribute("id"));
        employee.setUpdateUser((Integer) httpServletRequest.getSession().getAttribute("id"));

        employeeMapper.insert(employee);
    }

    @Override
    public PageBean<Employee> list(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        List<Employee> employeeList = employeeMapper.getAllByName(name);
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return new PageBean<>(pageInfo.getTotal(), pageInfo.getList());
    }
}




