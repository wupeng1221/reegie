package com.wup.controller;

import com.alibaba.fastjson.JSONObject;
import com.wup.common.PageBean;
import com.wup.common.Result;
import com.wup.entity.Employee;
import com.wup.service.EmployeeService;
import com.wup.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    // 登录用户
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private HttpServletRequest request;
    @PostMapping("/login")
    public Result<Object> login(@RequestBody Employee employee) {
        Employee byUsername = employeeService.findByUsername(employee);
        Employee byUsernameAndPassword = null;
        if (byUsername != null) {
            byUsernameAndPassword = employeeService.findByUsernameAndPassword(employee);
        }
        // 判断用户名是否存在，不存在直接返回用户不存在
        if (byUsername == null) {
            return Result.error("用户名不存在");
        } else if (byUsernameAndPassword == null){
            // 用户名正确，密码错误的情况
            return Result.error("密码错误");
        } else if (byUsernameAndPassword.getStatus() != 1) {
            return Result.error("该用户已被禁止登录");
        } else {
            Integer id = byUsernameAndPassword.getId();
            String name = byUsernameAndPassword.getName();
            String username = byUsernameAndPassword.getUsername();
            Map<String, Object> claims = new HashMap<>();
            claims.put("name", name);
            claims.put("username", username);
            String jwt = JwtUtils.generateJwt(claims);
            request.getSession().setAttribute("id", id);
            request.getSession().setAttribute("jwt", jwt);
            log.info("用户-{}-登录", name);
            return Result.success(byUsernameAndPassword);
        }
    }
    @PostMapping("/logout")
    public Result<String> logout() {
        request.getSession().removeAttribute("jwt");
        return Result.success("退出成功");
    }

    @PostMapping
    public Result<String> add(@RequestBody Employee employee) {
        employeeService.save(employee);
        return Result.success("新增员工成功");
    }
    @GetMapping("/page")
    public Result<Object> page(@Param("page") Integer page,
                               @Param("pageSize") Integer pageSize,
                               String name) {
        PageBean<Employee> pageBean = employeeService.list(page, pageSize, name);
        // log.info(JSONObject.toJSONString(pageBean));
        // 直接返回pageBean对象即可
        return Result.success(pageBean);
    }

}
