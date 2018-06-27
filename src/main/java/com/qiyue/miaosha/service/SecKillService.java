package com.qiyue.miaosha.service;

import java.util.List;

import com.qiyue.miaosha.dto.Exposer;
import com.qiyue.miaosha.dto.SecKillExecution;
import com.qiyue.miaosha.exception.RepeatKillException;
import com.qiyue.miaosha.exception.SecKillCloseException;
import com.qiyue.miaosha.exception.SecKillException;
import com.qiyue.miaosha.pojo.SecKill;

public interface SecKillService {
	
	List<SecKill> getSecKillList();
	
	Exposer exportSeckillUrl();
	
	SecKillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SecKillCloseException,RepeatKillException,SecKillException;
	
	
	

}
