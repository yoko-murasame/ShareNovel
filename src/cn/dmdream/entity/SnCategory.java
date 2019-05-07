package cn.dmdream.entity;

public class SnCategory {

	private Integer catId;//True	分类id自增主键
	private String catName;//True	分类名称
	private Integer catParentid;//当前分类的父id，默认0代表顶级分类
	private Integer catGender;//当前分类所属性别分类(男生喜欢0/女生喜欢1)
	public SnCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SnCategory(Integer catId, String catName, Integer catParentid, Integer catGender) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.catParentid = catParentid;
		this.catGender = catGender;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public Integer getCatParentid() {
		return catParentid;
	}
	public void setCatParentid(Integer catParentid) {
		this.catParentid = catParentid;
	}
	public Integer getCatGender() {
		return catGender;
	}
	public void setCatGender(Integer catGender) {
		this.catGender = catGender;
	}
	@Override
	public String toString() {
		return "SnCategory [catId=" + catId + ", catName=" + catName + ", catParentid=" + catParentid + ", catGender="
				+ catGender + "]";
	}
}
