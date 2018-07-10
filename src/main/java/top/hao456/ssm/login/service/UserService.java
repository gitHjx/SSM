package top.hao456.ssm.login.service;


import java.util.List;

import top.hao456.ssm.login.entity.TbUser;

public interface UserService {

	
	public TbUser getUserByUserName(String username);
	
	public List<TbUser> getUserListByUserName(String username);
}
