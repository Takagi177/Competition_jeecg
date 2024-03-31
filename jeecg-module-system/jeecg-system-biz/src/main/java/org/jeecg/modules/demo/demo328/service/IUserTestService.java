package org.jeecg.modules.demo.demo328.service;

import org.jeecg.modules.demo.demo328.entity.UserTest;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2024-03-28
 * @Version: V1.0
 */
public interface IUserTestService extends IService<UserTest> {

	/**
	 * 添加一对多
	 *
	 * @param userTest
	 */
	public void saveMain(UserTest userTest) ;
	
	/**
	 * 修改一对多
	 *
   * @param userTest
	 */
	public void updateMain(UserTest userTest);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
