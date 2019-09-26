package net.newglobe.app.controller;

import java.util.Date;
import java.util.List;

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

import net.newglobe.app.model.Bug;
import net.newglobe.app.model.Project;
import net.newglobe.app.model.SysAccount;
import net.newglobe.app.model.vo.PageVo;
import net.newglobe.app.model.vo.PagingDataResult;
import net.newglobe.app.model.vo.Result;
import net.newglobe.app.service.BugService;
import net.newglobe.app.service.ProjectService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RequestMapping("/bug")
@Controller
public class BugController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BugService bugService;
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/toList")
	public String toAccountList(HttpServletRequest request) {
		return "app/bug/list";
	}

	@RequestMapping("toEdit")
	public String toEdit(HttpServletRequest request, Long id) {
		try {
			Bug bug = bugService.selectById(id);
			request.setAttribute("bug", bug);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return "app/bug/edit";
	}

	@RequestMapping("toAdd")
	public String toAdd(HttpServletRequest request) {
		try {
			Project p = new Project();
			p.setStatus(1);
			List<Project> projects = projectService.selectList(p);
			request.setAttribute("projects", projects);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return "app/bug/add";
	}

	@RequestMapping("getList")
	@ResponseBody
	public PagingDataResult<Bug> getData(@ModelAttribute Bug t, PageVo pageVo) {
		PagingDataResult<Bug> result = null;
		try {
			t.setStatus(1);
			Example example = new Example(Project.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status=1");
			if(!StringUtils.isEmpty(t.getProjectName())) {
				criteria.andCondition("project_name like '%" + t.getProjectName() + "%'");
			}
			result = bugService.selectListByExample(pageVo, example);
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
	public Result insert(Bug t) {
		SysAccount user = (SysAccount)SecurityUtils.getSubject().getPrincipal();
		Result result = new Result();
		try {
			Date date = new Date();
			if (t.getId() == null) {
				t.setUpdateTime(date);
				t.setCreateTime(date);
				t.setCreator(user.getId());
				t.setUpdator(user.getId());
				bugService.insertSelective(t);
			} else {
				t.setUpdateTime(date);
				t.setCreateTime(date);
				bugService.updateByIdSelective(t);
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
			bugService.deleteByIds(Bug.class, ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("遇到问题");
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
}
