package org.jeecg.modules.demo.demo328.service.impl;

import org.jeecg.modules.demo.demo328.entity.UserTest;
import org.jeecg.modules.demo.demo328.mapper.UserTestMapper;
import org.jeecg.modules.demo.demo328.service.IUserTestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2024-03-28
 * @Version: V1.0
 */
@Service
public class UserTestServiceImpl extends ServiceImpl<UserTestMapper, UserTest> implements IUserTestService {

	@Autowired
	private UserTestMapper userTestMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(UserTest userTest) {
		userTestMapper.insert(userTest);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(UserTest userTest) {
		userTestMapper.updateById(userTest);
		
		//1.先删除子表数据
		
		//2.子表数据重新插入
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		userTestMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			userTestMapper.deleteById(id);
		}
	}
	
}
