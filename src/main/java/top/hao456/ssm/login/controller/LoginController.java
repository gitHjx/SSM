package top.hao456.ssm.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class LoginController {

	
	
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Subject currentUser = SecurityUtils.getSubject();
		/* 判断当前用户是否登录,没有则进行认证 */
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// rememberMe 记住我
			// token.setRememberMe(true);
			try {
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				throw new UnknownAccountException("用户不存在！");
			}catch (IncorrectCredentialsException ice) {
				throw new IncorrectCredentialsException("密码错误！");
			} catch (LockedAccountException lae) {
				throw new LockedAccountException("账户被锁！");
            }catch (AuthenticationException ae) {
				throw new AuthenticationException("登录失败");
			} 
		}
		System.out.println("登录成功!");
		return "success";
	}
}
