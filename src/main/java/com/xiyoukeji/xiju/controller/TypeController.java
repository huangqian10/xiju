package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Params;
import com.xiyoukeji.xiju.model.Type;

@Controller
public class TypeController extends BaseController{

	@RequestMapping("/front/getTypeList.do")
	public void getTypeList(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info", typeService.getAll());
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/saveOrUpdateType.do")
	public void saveOrUpdateType(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="typeName",required=true)String typeName,
			@RequestParam(value="id",required=false)Integer id,
			@RequestParam(value="paramsId",required=true)String paramsId){
		JSONObject jv = new JSONObject();
		if(typeService.isExistTypeName(typeName,id)){
			jv.put("code", Const.EXIST_TYPE);
		}else{
			if(id!=null){//update
				Type type =typeService.getById(id);
				if(type!=null){
					type.setTypeName(typeName);
					type.setParamsId(paramsId);
					typeService.update(type);
					jv.put("code", Const.SUCCESS);
				}else{
					jv.put("code", Const.NO_SUCH_TYPE);
				}
			}else{//add
				Type type =new Type();
				type.setTypeName(typeName);
				type.setId(id);
				type.setParamsId(paramsId);
				typeService.save(type);
				jv.put("code", Const.SUCCESS);
				jv.put("id", type.getId());
			}
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getTypeParams.do")
	public void adminGetTypeList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="typeId",required=true)Integer typeId){
		JSONObject jv = new JSONObject();
		Type type =typeService.getById(typeId);
		if(!StringUtils.isEmpty(type.getParamsId())){
			List<Params> params = paramsService.getParamsByIds(type.getParamsId());
			jv.put("info", params);
		}
		Utils.writeBack(request, response, jv);
	}
	
}
