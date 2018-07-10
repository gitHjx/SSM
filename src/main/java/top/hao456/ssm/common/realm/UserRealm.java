package top.hao456.ssm.common.realm;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

import top.hao456.ssm.login.entity.TbUser;
import top.hao456.ssm.login.service.UserService;


public class UserRealm extends AuthenticatingRealm {

	@Resource
	private UserService userService;
	
	/**
	 * 1.AuthenticationInfo获取认证消息，如果数据库中没有数据，返回null,如果得到正确的用户名，和密码，返回指定类型的对象。
	 * 
	 * 2.AuthenticationInfo  可以使用SimpleAuthenticationInfo的一个实现类，来封装正确的用户名和密码
	 * 
	 * 3.token就是我们需要认证的token
	 * 
	 * 
	 */
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		/**
		 * 1. 将token转换成usernamepasswordtoken
		 * 
		 * 2.获取用户名即可
		 * 
		 * 3.查询数据库，是否存在指定用户名的用户。
		 * 
		 * 4.如果查询到了，我们封装查询结果，返回给我们的调用
		 * 
		 * 5.如果没有查询到，拋出一个异常
		 */
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		TbUser user = userService.getUserByUserName(username);
		SimpleAuthenticationInfo info = null;
		if(user!=null) {
			Object principal = username;
			
			Object credentials = user.getPassword();
			
			String realmName = this.getName();
			
			String salt =user.getUsername();
			ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
			info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
		}else {
			throw new UnknownAccountException();
		}
		
		return info;
	}

	public static void main(String[] args) {
		String saltStr = UUID.randomUUID().toString();
		ByteSource credentialsSalt = ByteSource.Util.bytes("sally");
		SimpleHash simpleHash = new SimpleHash("MD5", "123456", credentialsSalt, 1024);
		System.out.println(saltStr);
		System.out.println(simpleHash.toString());
	}
}
