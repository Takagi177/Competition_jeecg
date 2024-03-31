package org.jeecg.modules.demo.school.service;

import org.jeecg.modules.demo.school.entity.SchoolTest;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 学校信息表
 * @Author: jeecg-boot
 * @Date:   2024-03-31
 * @Version: V1.0
 */
public interface ISchoolTestService extends IService<SchoolTest> {


	/**
	 * 添加一对多
	 *
	 * @param schoolTest
	 */
	public void saveMain(SchoolTest schoolTest) ;
	
	/**
	 * 修改一对多
	 *
   * @param schoolTest
	 */
	public void updateMain(SchoolTest schoolTest);
	
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
