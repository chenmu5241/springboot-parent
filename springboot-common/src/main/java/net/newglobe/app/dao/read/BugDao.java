package net.newglobe.app.dao.read;

import net.newglobe.app.model.Bug;
import net.newglobe.app.mybatis.BaseDao;
import net.newglobe.app.mybatis.MyBatisRepository;

@MyBatisRepository
public interface BugDao extends BaseDao<Bug> {

}