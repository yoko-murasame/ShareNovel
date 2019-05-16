package cn.dmdream.entity;

public class SnUser {

	private Integer userId;//主键 自动生成
	private String userUsername;//用于登录的用户名 唯一 不为空
	private String userPassword;//用户密码 不为空
	private String userNickname;//用户显示昵称 可修改 不为空
	private String userNickpic;//用户头像
	private String userEmail;//用户邮箱 用于激活账号 不为空
	private String userPhone;//用户手机号 可选 用于激活账号 不为空
	private Integer userEmailActive;//默认0:未激活 1:已激活
	private Integer userPhoneActive;//默认0:未激活 1:已激活
	private String userRegisttime;//注册时间 数据库会自动生成 可以设置为null
	private String userCode;//存放激活码
	public SnUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SnUser(Integer userId, String userUsername, String userPassword, String userNickname, String userNickpic,
			String userEmail, String userPhone, Integer userEmailActive, Integer userPhoneActive, String userRegisttime,
			String userCode) {
		super();
		this.userId = userId;
		this.userUsername = userUsername;
		this.userPassword = userPassword;
		this.userNickname = userNickname;
		this.userNickpic = userNickpic;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userEmailActive = userEmailActive;
		this.userPhoneActive = userPhoneActive;
		this.userRegisttime = userRegisttime;
		this.userCode = userCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserUsername() {
		return userUsername;
	}
	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserNickpic() {
		return userNickpic;
	}
	public void setUserNickpic(String userNickpic) {
		this.userNickpic = userNickpic;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Integer getUserEmailActive() {
		return userEmailActive;
	}
	public void setUserEmailActive(Integer userEmailActive) {
		this.userEmailActive = userEmailActive;
	}
	public Integer getUserPhoneActive() {
		return userPhoneActive;
	}
	public void setUserPhoneActive(Integer userPhoneActive) {
		this.userPhoneActive = userPhoneActive;
	}
	public String getUserRegisttime() {
		return userRegisttime;
	}
	public void setUserRegisttime(String userRegisttime) {
		this.userRegisttime = userRegisttime;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Override
	public String toString() {
		return "SnUser [userId=" + userId + ", userUsername=" + userUsername + ", userPassword=" + userPassword
				+ ", userNickname=" + userNickname + ", userNickpic=" + userNickpic + ", userEmail=" + userEmail
				+ ", userPhone=" + userPhone + ", userEmailActive=" + userEmailActive + ", userPhoneActive="
				+ userPhoneActive + ", userRegisttime=" + userRegisttime + ", userCode=" + userCode + "]";
	}
	
}
