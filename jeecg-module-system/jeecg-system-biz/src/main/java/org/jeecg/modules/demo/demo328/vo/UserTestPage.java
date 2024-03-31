package org.jeecg.modules.demo.demo328.vo;

import java.util.List;
import org.jeecg.modules.demo.demo328.entity.UserTest;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2024-03-28
 * @Version: V1.0
 */
@Data
@ApiModel(value="user_testPage对象", description="用户表")
public class UserTestPage {

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
	/**姓名*/
	@Excel(name = "姓名", width = 15)
	@ApiModelProperty(value = "姓名")
    private String userName;
	/**电话*/
	@Excel(name = "电话", width = 15)
	@ApiModelProperty(value = "电话")
    private String phone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	@ApiModelProperty(value = "邮箱")
    private String email;
	/**学校*/
	@Excel(name = "学校", width = 15, dictTable = "school_test", dicText = "name", dicCode = "name")
    @Dict(dictTable = "school_test", dicText = "name", dicCode = "name")
	@ApiModelProperty(value = "学校")
    private String school;
	/**学历（本科，高职/专科）*/
	@Excel(name = "学历（本科，高职/专科）", width = 15, dicCode = "education")
    @Dict(dicCode = "education")
	@ApiModelProperty(value = "学历（本科，高职/专科）")
    private String education;
	/**所属学院*/
	@Excel(name = "所属学院", width = 15)
	@ApiModelProperty(value = "所属学院")
    private String college;
	/**所属专业*/
	@Excel(name = "所属专业", width = 15)
	@ApiModelProperty(value = "所属专业")
    private String speciality;
	/**学号*/
	@Excel(name = "学号", width = 15)
	@ApiModelProperty(value = "学号")
    private String number;
	/**入学年份*/
	@Excel(name = "入学年份", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "入学年份")
    private Date enrollmentYear;
	/**密码*/
	@Excel(name = "密码", width = 15)
	@ApiModelProperty(value = "密码")
    private String password;


}
