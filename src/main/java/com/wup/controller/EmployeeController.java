package com.wup.controller;

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
    /**
     * 进行登录以及登录校验
     * @param employee 前端传入的json
     * @return 返回给前端的json标准信息
     */
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
            Long id = byUsernameAndPassword.getId();
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
    /**
     * 增加员工
     * @param employee 前端传入的json
     * @return 返回给前端的json标准信息
     */
    @PostMapping
    public Result<String> add(@RequestBody Employee employee) {
        employeeService.save(employee);
        return Result.success("新增员工成功");
    }
    /**
     * 员工分页查询
     * @param pageNum 页码
     * @param pageSize 每页展示的数量
     * @return 返回给前端的json标准信息
     */
    @GetMapping("/page")
    public Result<Object> page(@RequestParam("page") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize,
                               String name) {
        PageBean<Employee> pageBean = employeeService.list(pageNum, pageSize, name);
        // log.info(JSONObject.toJSONString(pageBean));
        // 直接返回pageBean对象即可
        return Result.success(pageBean);
    }
    /**
     * 用户状态修改以及信息修改
     * @param employee 前端传入的json
     * @return 返回给前端的json标准信息
     */
    @PutMapping
    public Result updateAndModifyStatus(@RequestBody Employee employee) {
        employeeService.update(employee);
        return Result.success(null);
    }

    /**
     * 返回id对应的员工信息
     * @param id url中用户的id
     * @return 返回给前端的json标准信息
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return Result.success(employee);
    }



}
