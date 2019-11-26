
package com.soulmatefree.soulmate.controller;


import com.mysql.cj.util.StringUtils;
import com.soulmatefree.soulmate.model.ZenMaster;
import com.soulmatefree.soulmate.service.ZenMasterService;
import com.soulmatefree.soulmate.utils.BaseResult;
import com.soulmatefree.soulmate.utils.CommonStatic;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.ZenMasterController
 * @version: 1.0
 */
@Api(value = "禅宗大师", description = "禅宗大师相关接口", position = 100, protocols = "http")
@RestController
public class ZenMasterController {

	@Autowired
	private ZenMasterService zenMasterService;

	@ApiOperation(
			value = "大师列表",
			notes = "大师列表，以集合的形式展示"
	)
	@GetMapping(value = "zenMasters")
	public List<ZenMaster> list() {
		return zenMasterService.findAll();
	}

	@ApiOperation(
			value = "添加大师",
			notes = "添加大师"
	)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "description", value = "描述", required = true, dataType = "String"),
			@ApiImplicitParam(name = "nickName", value = "别名", required = false, dataType = "String"),
			@ApiImplicitParam(name = "sex", value = "性别", required = false, dataType = "Boolean"),
			@ApiImplicitParam(name = "dynasty", value = "朝代", required = false, dataType = "String"),
			@ApiImplicitParam(name = "age", value = "年龄", required = false, dataType = "Integer"),
	})
	@ApiResponses({
			@ApiResponse(code = 400, message = "请求参数有误"),
			@ApiResponse(code = 500, message = "插入失败"),
	})
	@PostMapping(value = "zenMaster")
	public BaseResult<String> create(ZenMaster zenMaster) {
		if (null == zenMaster || null != zenMaster.getId()){
			return BaseResult.failWithCodeAndMsg(400,"参数传递错误，请检查！");
		}
		if (zenMasterService.save(zenMaster)<1){
			return BaseResult.failWithCodeAndMsg(401,"插入失败！");
		}
		return BaseResult.success();
	}

	@ApiOperation(
			value = "修改大师信息",
			notes = "修改大师信息"
	)
	@PutMapping(value = "zenMaster")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = false, dataType = "Integer"),
			@ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String"),
			@ApiImplicitParam(name = "description", value = "描述", required = false, dataType = "String"),
			@ApiImplicitParam(name = "nickName", value = "别名", required = false, dataType = "String"),
			@ApiImplicitParam(name = "sex", value = "性别", required = false, dataType = "Boolean"),
			@ApiImplicitParam(name = "dynasty", value = "朝代", required = false, dataType = "String"),
			@ApiImplicitParam(name = "age", value = "年龄", required = false, dataType = "Integer"),
	})
	@ApiResponses({
			@ApiResponse(code = 400, message = "请求参数有误"),
			@ApiResponse(code = 500, message = "插入失败"),
	})
	public BaseResult<String> update(ZenMaster zenMaster) {
		if (null == zenMaster || null == zenMaster.getId()){
			return BaseResult.failWithCodeAndMsg(400,"参数传递错误，请检查！");
		}
		if (zenMasterService.update(zenMaster)<1){
			return BaseResult.failWithCodeAndMsg(401,"编辑失败！");
		}
		return BaseResult.success();
	}

	@ApiOperation(
			value = "修改大师名字",
			notes = "修改大师名字"
	)
	@PatchMapping(value="/zenMaster/name")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
	})
	public BaseResult<String> updateName(Integer id,String name) {
		if (null == id || StringUtils.isNullOrEmpty(name)){
			return BaseResult.failWithCodeAndMsg(400,"参数传递错误，请检查！");
		}
		ZenMaster zenMaster = new ZenMaster();
		zenMaster.setId(id);
		zenMaster.setName(name);
		if (zenMasterService.update(zenMaster)<1){
			return BaseResult.failWithCodeAndMsg(401,"编辑失败！");
		}
		return BaseResult.success();
	}

	@ApiOperation(
			value = "根据id查找大师",
			notes = "根据id查找大师"
	)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer"),
	})
	@GetMapping(value = "zenMaster/{id}")
	public BaseResult<ZenMaster> get(@PathVariable Integer id) {
		return BaseResult.successWithData(this.zenMasterService.findZenMaster(id));
	}

	@ApiOperation(
			value = "删除大师",
			notes = "删除大师"
	)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer"),
	})
	@DeleteMapping(value = "zenMaster/{id}")
	public void delete(@PathVariable("id") Integer id) {
		this.zenMasterService.deleteZenMaster(id);
	}

	@ApiOperation(
			value = "从redis中获取大师列表",
			notes = "先从redis中取，若不存在，则从数据库中取"
	)
	@GetMapping(value = "redis/zenMasters")
	public Object findAllFromRedis() {
		return  zenMasterService.findAllFromRedis(CommonStatic.ALL_ZEN_MASTER,CommonStatic.ZEN_MASTER);
	}


	@ApiOperation(
			value = "清空redis中获取大师列表",
			notes = "添加大师的时候可以重新reload列表"
	)
	@GetMapping(value = "redis/reload/zenMasters")
	public Object reloadZenMastersRedis() {
		return zenMasterService.reloadZenMastersRedis(CommonStatic.ALL_ZEN_MASTER,CommonStatic.ZEN_MASTER);
	}


	@GetMapping(value = "redis/zenMasters/list")
	public Object findAllFromRedisByList() {
		return  zenMasterService.findAllFromRedisByList(CommonStatic.ALL_ZEN_MASTER_LIST);
	}





}
