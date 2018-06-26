package com.qiyue.miaosha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiyue.miaosha.pojo.SecKill;

public interface SecKillDao {

	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	SecKill queryById(long seckilled);
	
	List<SecKill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	public void seckillByProcedure(Map<String, Object> paramMap);
}
