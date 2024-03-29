package net.newglobe.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.newglobe.app.model.Project;
import net.newglobe.app.model.SysAccount;
import net.newglobe.app.model.vo.DataResult;
import net.newglobe.app.model.vo.PageVo;
import net.newglobe.app.model.vo.PagingDataResult;
import net.newglobe.app.model.vo.Result;
import net.newglobe.app.service.ProjectService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RequestMapping("/project")
@Controller
public class ProjectController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/toList")
	public String toAccountList(HttpServletRequest request) {
		return "app/project/list";
	}

	@RequestMapping("toEdit")
	public String toEdit(HttpServletRequest request, Long id) {
		try {
			Project project = projectService.selectById(id);
			request.setAttribute("project", project);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return "app/project/edit";
	}

	@RequestMapping("toAdd")
	public String toAdd(HttpServletRequest request) {
		try {
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return "app/project/add";
	}

	@RequestMapping("getList")
	@ResponseBody
	public PagingDataResult<Project> getData(@ModelAttribute Project t, PageVo pageVo) {
		PagingDataResult<Project> result = null;
		try {
			t.setStatus(1);
			Example example = new Example(Project.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status=1");
			if (!StringUtils.isEmpty(t.getName())) {
				criteria.andCondition("name like '%" + t.getName() + "%'");
			}
			result = projectService.selectListByExample(pageVo, example);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("查询数据异常!");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Result insert(Project t) {
		SysAccount user = (SysAccount)SecurityUtils.getSubject().getPrincipal();
		Result result = new Result();
		try {
			Date date = new Date();
			if (t.getId() == null) {
				t.setUpdateTime(date);
				t.setCreateTime(date);
				t.setCreator(user.getId());
				t.setUpdator(user.getId());
				projectService.insertSelective(t);
			} else {
				t.setUpdateTime(date);
				t.setCreateTime(date);
				t.setCreator(user.getId());
				t.setUpdator(user.getId());
				projectService.updateByIdSelective(t);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("遇到问题");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
	@RequestMapping("insertOrUpdateByName")
	@ResponseBody
	public Result insertOrUpdateByName(Project t) {
		Result result = new Result();
		try {
			Date date = new Date();
			String name = t.getName();
			if(StringUtils.isNotEmpty(name)) {

				Project tmp = new Project();
				tmp.setName(t.getName());
				tmp.setUrl(t.getUrl());
				List<Project> selectList = projectService.selectList(tmp);
				if(selectList.size()>0) {
					Project project = selectList.get(0);
					t.setId(project.getId());

					t.setUpdateTime(date);
					t.setCreateTime(date);
					projectService.updateByIdSelective(t);
				}else {
					t.setUpdateTime(date);
					t.setCreateTime(date);
					projectService.insertSelective(t);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("遇到问题");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Result delete(String ids) {
		Result result = new Result();
		try {
			projectService.deleteByIds(Project.class, ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("遇到问题");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	@RequestMapping("check")
	@ResponseBody
	public DataResult<Map<String, String>> checkAccount(Project t) {
		DataResult<Map<String, String>> result = new DataResult<Map<String, String>>();
		try {
			Long count = projectService.selectCount(t);
			Map<String, String> map = new HashMap<String, String>();
			if (count != 0) {
				map.put("error", "用户名已存在");
			} else {
				map.put("ok", "可以使用");
			}
			result.setData(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("遇到问题");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

}
