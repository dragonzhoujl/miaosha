package com.qiyue.miaosha.dto;

import com.qiyue.miaosha.enums.SeckillStatEnum;
import com.qiyue.miaosha.pojo.SuccessKill;

public class SecKillExecution {
	
	private long seckillId;
	
	private int state;
	
	private String stateInfo;
	
	private SuccessKill successKill;
	
	

	public SecKillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKill successKill) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successKill = successKill;
	}
	public SecKillExecution(long seckillId, SeckillStatEnum  statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}
	
	
	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKill getSuccessKill() {
		return successKill;
	}

	public void setSuccessKill(SuccessKill successKill) {
		this.successKill = successKill;
	}
	
	

}
