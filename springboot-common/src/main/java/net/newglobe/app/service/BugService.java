package net.newglobe.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.newglobe.app.dao.read.BugDao;
import net.newglobe.app.model.Bug;
import net.newglobe.app.mybatis.BaseService;

@Service
public class BugService extends BaseService<Bug, BugDao> {

	@Autowired
	BugDao BugDao;


}
