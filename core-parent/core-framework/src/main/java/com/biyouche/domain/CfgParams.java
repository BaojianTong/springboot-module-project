package com.biyouche.domain;

import java.io.Serializable;

/**
 * 参数配置
 * @author lgh
 *
 */
public class CfgParams implements Serializable {
		

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -760074907031932338L;

	/**
	 * 分类
	 */
	private String pmCate;
	
	/**
	 * 关键字
	 */
	private String pmKey;
	
	/**
	 * 值
	 */
	private String pmValue;
	
	/**
	 * 描述
	 */
	private String pmRemark;

	public CfgParams() {
		super();
	}

	public String getPmCate() {
		return pmCate;
	}

	public void setPmCate(String pmCate) {
		this.pmCate = pmCate;
	}

	public String getPmKey() {
		return pmKey;
	}

	public void setPmKey(String pmKey) {
		this.pmKey = pmKey;
	}

	public String getPmValue() {
		return pmValue;
	}

	public void setPmValue(String pmValue) {
		this.pmValue = pmValue;
	}

	public String getPmRemark() {
		return pmRemark;
	}

	public void setPmRemark(String pmRemark) {
		this.pmRemark = pmRemark;
	}

	@Override
	public String toString() {
		return "CfgParams [pmCate=" + pmCate + ", pmKey=" + pmKey + ", pmValue=" + pmValue + ", pmRemark=" + pmRemark
				+ "]";
	}

	
	
}
