package top.hao456.ssm.login.controller;

import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import top.hao456.ssm.common.web.JsonResult;
import top.hao456.ssm.login.entity.TbUser;
import top.hao456.ssm.login.service.UserService;

@Api(value="用户相关",tags= {"用户相关接口"})
@Controller
@RequestMapping("/user")
public class LoginController {

	
	@Resource
	private UserService userService;
	
	
	
	@ApiOperation(value="用户登录接口",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="username",value="用户名",dataType="string", paramType = "query"),
		@ApiImplicitParam(name="password",value="密码",dataType="string", paramType = "query")
	})
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult login(HttpServletRequest request) {
		String message = null;
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
				return new JsonResult(new RuntimeException("用户名不存在"));
			}catch (IncorrectCredentialsException ice) {
				return new JsonResult(new RuntimeException("密码错误"));
			} catch (LockedAccountException lae) {
				return new JsonResult(new RuntimeException("账号被锁"));
            }catch (AuthenticationException ae) {
            	return new JsonResult(new RuntimeException("登录失败"));
			} 
		}
		return new JsonResult();
	}
	
	
	@ApiOperation(value="用户列表接口",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="username",value="用户名",dataType="string", paramType = "query"),
	})
	@RequestMapping("/list")
	@ResponseBody
	public JsonResult getUserList(String username) {
		List<TbUser> list = this.userService.getUserListByUserName(username);
		return new JsonResult(list);
	}
	
	@RequestMapping("/success")
	public String success() {
		return "success";
	}
}
