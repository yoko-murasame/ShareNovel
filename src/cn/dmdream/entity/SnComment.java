package cn.dmdream.entity;

public class SnComment {

	private Integer commId;//True	评论主键 自增
	private SnUser commUser;//True	外键 评论用户id 用户回显需要用到因此要保存完整对象
	private SnNovel commNovel;//True	外键 评论小说id 这里考虑到效率 封装时仅存Novelid主键
	private String commContent;//True	评论内容 可以是富文本
	private String commTime;//False	评论时间,数据库自动生成
	private Integer commHitnum;//False 评论点赞数
	private SnComment commParent;//False 评论的父评论id 用于评论回复
	public SnComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SnComment(Integer commId, SnUser commUser, SnNovel commNovel, String commContent, String commTime,
			Integer commHitnum, SnComment commParent) {
		super();
		this.commId = commId;
		this.commUser = commUser;
		this.commNovel = commNovel;
		this.commContent = commContent;
		this.commTime = commTime;
		this.commHitnum = commHitnum;
		this.commParent = commParent;
	}
	public Integer getCommId() {
		return commId;
	}
	public void setCommId(Integer commId) {
		this.commId = commId;
	}
	public SnUser getCommUser() {
		return commUser;
	}
	public void setCommUser(SnUser commUser) {
		this.commUser = commUser;
	}
	public SnNovel getCommNovel() {
		return commNovel;
	}
	public void setCommNovel(SnNovel commNovel) {
		this.commNovel = commNovel;
	}
	public String getCommContent() {
		return commContent;
	}
	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}
	public String getCommTime() {
		return commTime;
	}
	public void setCommTime(String commTime) {
		this.commTime = commTime;
	}
	public Integer getCommHitnum() {
		return commHitnum;
	}
	public void setCommHitnum(Integer commHitnum) {
		this.commHitnum = commHitnum;
	}
	public SnComment getCommParent() {
		return commParent;
	}
	public void setCommParent(SnComment commParent) {
		this.commParent = commParent;
	}
	@Override
	public String toString() {
		return "SnComment [commId=" + commId + ", commUser=" + commUser + ", commNovel=" + commNovel + ", commContent="
				+ commContent + ", commTime=" + commTime + ", commHitnum=" + commHitnum + ", commParent=" + commParent
				+ "]";
	}
}
