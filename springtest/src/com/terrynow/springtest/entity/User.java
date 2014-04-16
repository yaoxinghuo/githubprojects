package com.terrynow.springtest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author xinghuo.yao Email: yaoxinghuo at 126 dot com
 * @version create: May 18, 2009 2:38:07 PM
 */

@Entity
@Table(name = "userinfo")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8752114080173440829L;

	private String id;

	private String username;
	private String no;// 编号
	private String mobile;// 电话
	private String email;// 邮箱
	private String birth;
	private int gender;// 性别
	private String dept;// 单位
	/*
	 * 1 博士 2 研究生 3 本科 4 专科
	 */
	private int edu;// 学历
	/*
	 * 1学士，2硕士，3博士，4其它
	 */
	private int degree;// 学位
	private String spec;// 专业
	private String pro;// 职称
	private String password;
	private boolean disabled;// 账户是否被禁用？false否，true是，被禁用了
	private double balance = 0;// 账户余额

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 
	 * @author xinghuo.yao
	 * @date 2014年4月16日 下午12:28:39
	 * @description 测试不要输出password
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getEdu() {
		return edu;
	}

	public void setEdu(int edu) {
		this.edu = edu;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDept() {
		return dept;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSpec() {
		return spec;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getPro() {
		return pro;
	}

}
