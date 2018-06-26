package com.qiyue.miaosha.dao;

import org.apache.ibatis.annotations.Param;

import com.qiyue.miaosha.pojo.SuccessKill;

public interface SuccessKillDao {
	
	int insertSuccessKill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	SuccessKill queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

}
