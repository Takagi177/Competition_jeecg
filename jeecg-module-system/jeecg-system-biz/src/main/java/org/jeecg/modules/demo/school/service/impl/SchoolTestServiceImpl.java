package org.jeecg.modules.demo.school.service.impl;

import org.jeecg.modules.demo.school.entity.SchoolTest;
import org.jeecg.modules.demo.school.mapper.SchoolTestMapper;
import org.jeecg.modules.demo.school.service.ISchoolTestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 学校信息表
 * @Author: jeecg-boot
 * @Date:   2024-03-31
 * @Version: V1.0
 */
@Service
public class SchoolTestServiceImpl extends ServiceImpl<SchoolTestMapper, SchoolTest> implements ISchoolTestService {

	@Autowired
	private SchoolTestMapper schoolTestMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(SchoolTest schoolTest) {
		schoolTestMapper.insert(schoolTest);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(SchoolTest schoolTest) {
		schoolTestMapper.updateById(schoolTest);
		
		//1.先删除子表数据
		
		//2.子表数据重新插入
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		schoolTestMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			schoolTestMapper.deleteById(id);
		}
	}
	
}
