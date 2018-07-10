package top.hao456.ssm.login.service;

import top.hao456.ssm.login.entity.TbUser;

public interface UserService {

	
	public TbUser getUserByUserName(String username);
}
