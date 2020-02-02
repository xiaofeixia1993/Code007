package com.wyh.controller;

import com.google.gson.Gson;
import com.wyh.Service.UserService;
import com.wyh.entity.User;
import com.wyh.entity.VaptchaMessage;
import com.wyh.util.CryptographyUtil;
import com.wyh.util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * 用户控制器
 * @author wyh
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录请求
     * @param user
     * @param vaptcha_token
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(User user, String vaptcha_token, HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isEmpty(user.getUserName().trim())) {
            map.put("success", false);
            map.put("errorInfo", "请输入用户名！");
        }else if(StringUtil.isEmpty(user.getPassword().trim())) {
            map.put("success", false);
            map.put("errorInfo", "请输入密码！");
        }else if (!vaptchaCheck(vaptcha_token, request.getRemoteHost())){
            map.put("success", false);
            map.put("errorInfo", "人机验证失败！");
        }else {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), CryptographyUtil.md5(user.getPassword(), CryptographyUtil.SALT));
            try {
                subject.login(token);
                String userName = (String) SecurityUtils.getSubject().getPrincipal();
                User currentUser = userService.findByUserName(userName);
                if (currentUser.isOff()) {
                    map.put("success", false);
                    map.put("errorInfo", "该用户已经被封禁，请联系管理员！");
                    subject.logout();
                }else {
                    request.getSession().setAttribute("currentUser", currentUser);
                    map.put("success", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("success", false);
                map.put("errorInfo", "用户名或者密码错误！");
            }
        }
        return map;
    }

    /**
     * 用户注册
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/register")
    public Map<String, Object> register(@Valid User user, BindingResult bindingResult, String vaptcha_token, HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        if (bindingResult.hasErrors()) {
            map.put("success", false);
            map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
        }else if (userService.findByUserName(user.getUserName()) != null){
            map.put("success", false);
            map.put("errorInfo", "用户名已存在，请更换！");
        }else if (userService.findByEmail(user.getEmail()) != null){
            map.put("success", false);
            map.put("errorInfo", "邮箱已存在，请更换！");
        }else if (!vaptchaCheck(vaptcha_token, request.getRemoteHost())){
            map.put("success", false);
            map.put("errorInfo", "人机验证失败！");
        }else {
            user.setPassword(CryptographyUtil.md5(user.getPassword(), CryptographyUtil.SALT));
            user.setRegisterDate(new Date());
            user.setImageName("default.jpg");
            userService.save(user);
            map.put("success", true);
        }
        return map;
    }

    /**
     * 人机验证结果判断
     * @param token
     * @param ip
     * @return
     * @throws Exception
     */
    private boolean vaptchaCheck(String token,String ip)throws Exception{
        String body="";
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost("http://api.vaptcha.com/v2/validate");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("id", "5b53cdb6fc650e53f84fd4c8"));
        nvps.add(new BasicNameValuePair("secretkey", "c2ab71f50ec248a2a5b7bd04cfdf90bc"));
        nvps.add(new BasicNameValuePair("scene", ""));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("ip", ip));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse r = httpClient.execute(httpPost);
        HttpEntity entity = r.getEntity();

        if(entity!=null){
            body = EntityUtils.toString(entity, "utf-8");
            System.out.println(body);
        }
        r.close();
        httpClient.close();
        Gson gson = new Gson();
        VaptchaMessage message=gson.fromJson(body, VaptchaMessage.class);
        if(message.getSuccess()==1){
            return true;
        }else{
            return false;
        }

    }
}
