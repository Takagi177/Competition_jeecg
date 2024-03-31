package org.jeecg.modules.demo.demo328.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.jeecg.modules.demo.demo328.entity.UserTest;
import org.jeecg.modules.demo.demo328.vo.UserTestPage;
import org.jeecg.modules.demo.demo328.service.IUserTestService;
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
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2024-03-28
 * @Version: V1.0
 */
@Api(tags="用户表")
@RestController
@RequestMapping("/demo328/userTest")
@Slf4j
public class UserTestController {
	@Autowired
	private IUserTestService userTestService;
	
	/**
	 * 分页列表查询
	 *
	 * @param userTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户表-分页列表查询")
	@ApiOperation(value="用户表-分页列表查询", notes="用户表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<UserTest>> queryPageList(UserTest userTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserTest> queryWrapper = QueryGenerator.initQueryWrapper(userTest, req.getParameterMap());
		Page<UserTest> page = new Page<UserTest>(pageNo, pageSize);
		IPage<UserTest> pageList = userTestService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param userTestPage
	 * @return
	 */
	@AutoLog(value = "用户表-添加")
	@ApiOperation(value="用户表-添加", notes="用户表-添加")
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserTestPage userTestPage) {
		UserTest userTest = new UserTest();
		BeanUtils.copyProperties(userTestPage, userTest);
		userTestService.saveMain(userTest);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userTestPage
	 * @return
	 */
	@AutoLog(value = "用户表-编辑")
	@ApiOperation(value="用户表-编辑", notes="用户表-编辑")
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserTestPage userTestPage) {
		UserTest userTest = new UserTest();
		BeanUtils.copyProperties(userTestPage, userTest);
		UserTest userTestEntity = userTestService.getById(userTest.getId());
		if(userTestEntity==null) {
			return Result.error("未找到对应数据");
		}
		userTestService.updateMain(userTest);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户表-通过id删除")
	@ApiOperation(value="用户表-通过id删除", notes="用户表-通过id删除")
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		userTestService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户表-批量删除")
	@ApiOperation(value="用户表-批量删除", notes="用户表-批量删除")
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userTestService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户表-通过id查询")
	@ApiOperation(value="用户表-通过id查询", notes="用户表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserTest> queryById(@RequestParam(name="id",required=true) String id) {
		UserTest userTest = userTestService.getById(id);
		if(userTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(userTest);

	}
	

    /**
    * 导出excel
    *
    * @param request
    * @param userTest
    */
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserTest userTest) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<UserTest> queryWrapper = QueryGenerator.initQueryWrapper(userTest, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<UserTest> userTestList = userTestService.list(queryWrapper);

      // Step.3 组装pageList
      List<UserTestPage> pageList = new ArrayList<UserTestPage>();
      for (UserTest main : userTestList) {
          UserTestPage vo = new UserTestPage();
          BeanUtils.copyProperties(main, vo);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "用户表列表");
      mv.addObject(NormalExcelConstants.CLASS, UserTestPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户表数据", "导出人:"+sysUser.getRealname(), "用户表"));
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
    //@RequiresPermissions("org.jeecg.modules.demo:user_test:importExcel")
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
              List<UserTestPage> list = ExcelImportUtil.importExcel(file.getInputStream(), UserTestPage.class, params);
              for (UserTestPage page : list) {
                  UserTest po = new UserTest();
                  BeanUtils.copyProperties(page, po);
                  userTestService.saveMain(po);
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
