package com.ymc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ymc.reggie.common.R;
import com.ymc.reggie.entity.User;
import com.ymc.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(@RequestBody User user, HttpSession session){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getPhone() != null,User::getPhone,user.getPhone());
        User one = userService.getOne(queryWrapper);
        if (one != null){
            session.setAttribute("user",one.getId());
            return R.success(one);
        }
        else {
            userService.save(user);
            User one1 = userService.getOne(queryWrapper);
            session.setAttribute("user",one1.getId());
            return R.success(one1);
        }
    }

    @PostMapping ("/loginout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("user");

        return R.success("退出登录成功");
    }
}
