package org.jeecg.modules.demo.school.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.school.entity.SchoolTest;
import org.jeecg.modules.demo.school.vo.SchoolTestPage;
import org.jeecg.modules.demo.school.service.ISchoolTestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import java.util.stream.Collectors;

 /**
 * @Description: 学校信息表
 * @Author: jeecg-boot
 * @Date:   2024-03-31
 * @Version: V1.0
 */
@Api(tags="学校信息表")
@RestController
@RequestMapping("/school/schoolTest")
@Slf4j



public class SchoolTestController {
	@Autowired
	private ISchoolTestService schoolTestService;


	 /**
	  * 查询所有包含 'name' 列的记录
	  *
	  * @return 查询结果
	  */
	 @GetMapping(value = "/names")
	 public Result<List<SchoolTest>> queryAllNames() {
		 QueryWrapper<SchoolTest> queryWrapper = new QueryWrapper<>();
		 // 假设我们要查询的列名为 'name'，这里直接使用列名作为查询条件
		 queryWrapper.select("name"); // 仅选择 'name' 列
		 List<SchoolTest> schoolTests = schoolTestService.list(queryWrapper);
		 return Result.OK(schoolTests);
	 }


	/**
	 * 分页列表查询
	 *
	 * @param schoolTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "学校信息表-分页列表查询")
	@ApiOperation(value="学校信息表-分页列表查询", notes="学校信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SchoolTest>> queryPageList(SchoolTest schoolTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SchoolTest> queryWrapper = QueryGenerator.initQueryWrapper(schoolTest, req.getParameterMap());
		Page<SchoolTest> page = new Page<SchoolTest>(pageNo, pageSize);
		IPage<SchoolTest> pageList = schoolTestService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param schoolTestPage
	 * @return
	 */
	@AutoLog(value = "学校信息表-添加")
	@ApiOperation(value="学校信息表-添加", notes="学校信息表-添加")
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SchoolTestPage schoolTestPage) {
		SchoolTest schoolTest = new SchoolTest();
		BeanUtils.copyProperties(schoolTestPage, schoolTest);
		schoolTestService.saveMain(schoolTest);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param schoolTestPage
	 * @return
	 */
	@AutoLog(value = "学校信息表-编辑")
	@ApiOperation(value="学校信息表-编辑", notes="学校信息表-编辑")
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SchoolTestPage schoolTestPage) {
		SchoolTest schoolTest = new SchoolTest();
		BeanUtils.copyProperties(schoolTestPage, schoolTest);
		SchoolTest schoolTestEntity = schoolTestService.getById(schoolTest.getId());
		if(schoolTestEntity==null) {
			return Result.error("未找到对应数据");
		}
		schoolTestService.updateMain(schoolTest);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学校信息表-通过id删除")
	@ApiOperation(value="学校信息表-通过id删除", notes="学校信息表-通过id删除")
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		schoolTestService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学校信息表-批量删除")
	@ApiOperation(value="学校信息表-批量删除", notes="学校信息表-批量删除")
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.schoolTestService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "学校信息表-通过id查询")
	@ApiOperation(value="学校信息表-通过id查询", notes="学校信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SchoolTest> queryById(@RequestParam(name="id",required=true) String id) {
		SchoolTest schoolTest = schoolTestService.getById(id);
		if(schoolTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(schoolTest);

	}
	

    /**
    * 导出excel
    *
    * @param request
    * @param schoolTest
    */
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SchoolTest schoolTest) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<SchoolTest> queryWrapper = QueryGenerator.initQueryWrapper(schoolTest, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<SchoolTest> schoolTestList = schoolTestService.list(queryWrapper);

      // Step.3 组装pageList
      List<SchoolTestPage> pageList = new ArrayList<SchoolTestPage>();
      for (SchoolTest main : schoolTestList) {
          SchoolTestPage vo = new SchoolTestPage();
          BeanUtils.copyProperties(main, vo);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "学校信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, SchoolTestPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学校信息表数据", "导出人:"+sysUser.getRealname(), "学校信息表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("org.jeecg.modules.demo:school_test:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<SchoolTestPage> list = ExcelImportUtil.importExcel(file.getInputStream(), SchoolTestPage.class, params);
              for (SchoolTestPage page : list) {
                  SchoolTest po = new SchoolTest();
                  BeanUtils.copyProperties(page, po);
                  schoolTestService.saveMain(po);
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
