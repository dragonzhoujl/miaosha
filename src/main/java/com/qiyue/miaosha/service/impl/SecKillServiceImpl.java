package com.qiyue.miaosha.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.qiyue.miaosha.dao.RedisDao;
import com.qiyue.miaosha.dao.SecKillDao;
import com.qiyue.miaosha.dao.SuccessKillDao;
import com.qiyue.miaosha.dto.Exposer;
import com.qiyue.miaosha.dto.SecKillExecution;
import com.qiyue.miaosha.enums.SeckillStatEnum;
import com.qiyue.miaosha.exception.RepeatKillException;
import com.qiyue.miaosha.exception.SecKillCloseException;
import com.qiyue.miaosha.exception.SecKillException;
import com.qiyue.miaosha.pojo.SecKill;
import com.qiyue.miaosha.pojo.SuccessKill;
import com.qiyue.miaosha.service.SecKillService;
@Service
public class SecKillServiceImpl implements SecKillService{
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SecKillDao seckillDao;
	@Autowired
	private SuccessKillDao successKillDao;
	@Autowired
	private RedisDao redisDao;
	
	private final String slat="xvzbnxsd^&&*)(*()kfmv4165323DGHSBJ";
	
	
	
	@Override
	public List<SecKill> getSecKillList() {

		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		SecKill secKill=redisDao.getSeckill(seckillId);
		if(secKill==null) {
			secKill = seckillDao.queryById(seckillId); 
			if(secKill==null){
				return new Exposer(false, seckillId);
			}else {
				redisDao.putSeckill(secKill);
			}
			
		}
		Date startTime=secKill.getStartTime();
		Date endTime=secKill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()) {
			return new Exposer(false, seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5=getMD5(seckillId);
		return new Exposer(true, md5,seckillId);
	}
	@Transactional
	@Override
	public SecKillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SecKillCloseException, RepeatKillException, SecKillException {
		if(md5==null||!md5.equals(getMD5(seckillId))) {
			throw new SecKillException("Seckill data rewrite");
		}
	
		Date nowTime=new Date();
		try {
			int insertCount=successKillDao.insertSuccessKill(seckillId, userPhone);
			if(insertCount<=0) {
				throw new RepeatKillException("Seckill repeated");//�ظ���ɱ
			}else {
				int updateCount=seckillDao.reduceNumber(seckillId, nowTime);
				if(updateCount<=0) {
					throw new SecKillCloseException("Seckill is closed");
				}else {
					SuccessKill successKilled=successKillDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SecKillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
				}
			}
		}catch (SecKillCloseException e) {
			throw e;
		}catch (RepeatKillException e) {
			throw e;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new SecKillException("Seckill inner error "+e.getMessage());
		}
		
	}

	@Override
	public SecKillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
		if(md5==null||!md5.equals(getMD5(seckillId))) {
			throw new SecKillException("Seckill data rewrite");
		}
		Date killTime =new Date();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		try {
			seckillDao.seckillByProcedure(map);
			int result = MapUtils.getInteger(map, "result", -2);  
			if(result==1){
				SuccessKill successKilled=successKillDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SecKillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
			}else{
				return new SecKillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new SecKillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
		
	}

	@Override
	public SecKill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}
	private String getMD5(long seckillId) {
		String base=seckillId+"/"+slat;
		String md5 =DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
