package org.jeecg.modules.demo.school.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 学校信息表
 * @Author: jeecg-boot
 * @Date:   2024-03-31
 * @Version: V1.0
 */
@Data
@ApiModel(value="school_testPage对象", description="学校信息表")
public class SchoolTestPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**学校名称*/
	@Excel(name = "学校名称", width = 15)
	@ApiModelProperty(value = "学校名称")
    private String name;
	/**学校所属赛区*/
	@Excel(name = "学校所属赛区", width = 15)
	@ApiModelProperty(value = "学校所属赛区")
    private String zone;


}
