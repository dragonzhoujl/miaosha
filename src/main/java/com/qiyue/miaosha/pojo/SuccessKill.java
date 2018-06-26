package com.qiyue.miaosha.pojo;

import java.sql.Date;

import javax.crypto.ShortBufferException;

public class SuccessKill {
	private long userPhone;
	private short state;
	private Date createdTime;
	private SecKill secKill;
	/**
	 * @return the userPhone
	 */
	public long getUserPhone() {
		return userPhone;
	}
	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * @return the state
	 */
	public short getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(short state) {
		this.state = state;
	}
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return the secKill
	 */
	public SecKill getSecKill() {
		return secKill;
	}
	/**
	 * @param secKill the secKill to set
	 */
	public void setSecKill(SecKill secKill) {
		this.secKill = secKill;
	}
	

}
