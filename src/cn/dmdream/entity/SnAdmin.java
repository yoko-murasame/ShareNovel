package cn.dmdream.entity;

public class SnAdmin {
	private Integer adminId;//主键
	private String adminUsername;//管理员用户名
	private String adminPassword;//管理员密码
	private String adminNickpic;//管理员头像
	public SnAdmin() {
		super();
	}
	public SnAdmin(Integer adminId, String adminUsername, String adminPassword, String adminNickpic) {
		super();
		this.adminId = adminId;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.adminNickpic = adminNickpic;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getAdminNickpic() {
		return adminNickpic;
	}
	public void setAdminNickpic(String adminNickpic) {
		this.adminNickpic = adminNickpic;
	}
	@Override
	public String toString() {
		return "SnAdmin [adminId=" + adminId + ", adminUsername=" + adminUsername + ", adminPassword=" + adminPassword
				+ ", adminNickpic=" + adminNickpic + "]";
	}
	
}
