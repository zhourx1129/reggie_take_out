package com.zhourx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zhourx.reggie.common.R;
import com.zhourx.reggie.entity.User;
import com.zhourx.reggie.service.UserService;
import com.zhourx.reggie.utils.SendCode;
import com.zhourx.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        // 获取手机号
        String userPhone = user.getPhone();
        if(StringUtils.isNotEmpty(userPhone)){
            // 生成随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            // 发送验证码
            // SendCode.sendCode(code);
            log.info("code:{}",code);
            // 将生成的验证码保存到session中
            session.setAttribute(userPhone,code);
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        // 获取手机号
        String  phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 从session中获取验证码
        Object codeInSession = session.getAttribute(phone);
        // 进行验证码对比（页面提交的验证码和session中保存的验证码对比
        if (codeInSession != null && codeInSession.equals(code)) {
            // 如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user==null){
                // 判断当前用户的手机号是否为新用户，如果是新用户就自动为其创建账号
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
