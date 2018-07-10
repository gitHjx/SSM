package top.hao456.ssm.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;

import top.hao456.ssm.login.dao.TbUserMapper;
import top.hao456.ssm.login.entity.TbUser;
import top.hao456.ssm.login.entity.TbUserExample;
import top.hao456.ssm.login.entity.TbUserExample.Criteria;
import top.hao456.ssm.login.service.UserService;

@Service
public class UserServiceImpl implements UserService{

		
	@Resource
	private TbUserMapper tbUserMapper;
	
	@Override
	public TbUser getUserByUserName(String username) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = this.tbUserMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	
	
}
