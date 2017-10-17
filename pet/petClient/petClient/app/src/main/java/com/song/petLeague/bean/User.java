package com.song.petLeague.bean;

import java.io.Serializable;

/**
 * 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:45:04 
*
 */
public class User implements Serializable{
	private String id;
	private String name;
	private String pwd;
	private String headUrl;
	private String headBgUrl;

	private String uPhoneNumber;

	private String uSex;

	private String uAge;

	private String uCollege;

	private String uMajor;

	private String uClass;

	private String uStudentNumber;

	private String uCity;

	private String uBirthday;

	private String uSignature;

	private String uConstellation;

	private String uEmotion;

	private String uUsuallyCity;

	private String uHabbies;

	private String uLikeSomething;

	public User() {

	}

	public User(String id, String name, String pwd, String headUrl, String headBgUrl){
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.headUrl = headUrl;
		this.headBgUrl = headBgUrl;
	}

	public User(String id, String name, String headUrl, String headBgUrl){
		this.id = id;
		this.name = name;
		this.headUrl = headUrl;
		this.headBgUrl = headBgUrl;
	}

	public User(String id, String name, String headUrl, String headBgUrl, String uPhoneNumber,
				String uSex, String uCollege, String uCity, String uBirthday){
		this.id = id;
		this.name = name;
		this.headUrl = headUrl;
		this.headBgUrl = headBgUrl;
		this.uPhoneNumber = uPhoneNumber;
		this.uSex = uSex;
		this.uCollege = uCollege;
		this.uCity = uCity;
		this.uBirthday = uBirthday;
	}

	public User(String id, String name, String pwd){
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public User(String id, String name, String pwd, String headUrl, String headBgUrl, String uPhoneNumber, String uSex, String uAge, String uCollege, String uMajor, String uClass, String uStudentNumber, String uCity, String uBirthday, String uSignature, String uConstellation, String uEmotion, String uUsuallyCity, String uHabbies, String uLikeSomething) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.headUrl = headUrl;
		this.headBgUrl = headBgUrl;
		this.uPhoneNumber = uPhoneNumber;
		this.uSex = uSex;
		this.uAge = uAge;
		this.uCollege = uCollege;
		this.uMajor = uMajor;
		this.uClass = uClass;
		this.uStudentNumber = uStudentNumber;
		this.uCity = uCity;
		this.uBirthday = uBirthday;
		this.uSignature = uSignature;
		this.uConstellation = uConstellation;
		this.uEmotion = uEmotion;
		this.uUsuallyCity = uUsuallyCity;
		this.uHabbies = uHabbies;
		this.uLikeSomething = uLikeSomething;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getHeadBgUrl() {
		return headBgUrl;
	}

	public void setHeadBgUrl(String headBgUrl) {
		this.headBgUrl = headBgUrl;
	}

	public String getuPhoneNumber() {
		return uPhoneNumber;
	}

	public void setuPhoneNumber(String uPhoneNumber) {
		this.uPhoneNumber = uPhoneNumber;
	}

	public String getuSex() {
		return uSex;
	}

	public void setuSex(String uSex) {
		this.uSex = uSex;
	}

	public String getuAge() {
		return uAge;
	}

	public void setuAge(String uAge) {
		this.uAge = uAge;
	}

	public String getuCollege() {
		return uCollege;
	}

	public void setuCollege(String uCollege) {
		this.uCollege = uCollege;
	}

	public String getuMajor() {
		return uMajor;
	}

	public void setuMajor(String uMajor) {
		this.uMajor = uMajor;
	}

	public String getuClass() {
		return uClass;
	}

	public void setuClass(String uClass) {
		this.uClass = uClass;
	}

	public String getuStudentNumber() {
		return uStudentNumber;
	}

	public void setuStudentNumber(String uStudentNumber) {
		this.uStudentNumber = uStudentNumber;
	}

	public String getuCity() {
		return uCity;
	}

	public void setuCity(String uCity) {
		this.uCity = uCity;
	}

	public String getuBirthday() {
		return uBirthday;
	}

	public void setuBirthday(String uBirthday) {
		this.uBirthday = uBirthday;
	}

	public String getuSignature() {
		return uSignature;
	}

	public void setuSignature(String uSignature) {
		this.uSignature = uSignature;
	}

	public String getuConstellation() {
		return uConstellation;
	}

	public void setuConstellation(String uConstellation) {
		this.uConstellation = uConstellation;
	}

	public String getuEmotion() {
		return uEmotion;
	}

	public void setuEmotion(String uEmotion) {
		this.uEmotion = uEmotion;
	}

	public String getuUsuallyCity() {
		return uUsuallyCity;
	}

	public void setuUsuallyCity(String uUsuallyCity) {
		this.uUsuallyCity = uUsuallyCity;
	}

	public String getuHabbies() {
		return uHabbies;
	}

	public void setuHabbies(String uHabbies) {
		this.uHabbies = uHabbies;
	}

	public String getuLikeSomething() {
		return uLikeSomething;
	}

	public void setuLikeSomething(String uLikeSomething) {
		this.uLikeSomething = uLikeSomething;
	}

	@Override
	public String toString() {
		return "id = " + id
				+ "; name = " + name
				+ "; headUrl = " + headUrl;
	}
}
