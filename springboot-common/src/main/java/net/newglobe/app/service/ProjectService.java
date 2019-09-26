package net.newglobe.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.newglobe.app.dao.read.ProjectDao;
import net.newglobe.app.model.Project;
import net.newglobe.app.mybatis.BaseService;

@Service
public class ProjectService extends BaseService<Project, ProjectDao> {

	@Autowired
	ProjectDao projectDao;


}
