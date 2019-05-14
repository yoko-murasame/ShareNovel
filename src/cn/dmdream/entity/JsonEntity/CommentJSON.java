package cn.dmdream.entity.JsonEntity;

import java.sql.Date;
import java.util.List;

public class CommentJSON {
	//{id:2,img:"",replyName:"",beReplyName:"",content:"”",time:"",address:"",osname:"",browse:"",replyBody:[{}]},
	private int id;
	private String img;
	private String replayName;
	private String beReplyName;
	private String content;
	private Date time;
	private String osname;
	private int hitnum;
	private List<CommentJSON> replyBody;
	public List<CommentJSON> getReplyBody() {
		return replyBody;
	}
	public void setReplyBody(List<CommentJSON> replyBody) {
		this.replyBody = replyBody;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getReplayName() {
		return replayName;
	}
	public void setReplayName(String replayName) {
		this.replayName = replayName;
	}
	public String getBeReplyName() {
		return beReplyName;
	}
	public void setBeReplyName(String beReplyName) {
		this.beReplyName = beReplyName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname(String osname) {
		this.osname = osname;
	}
	public int getHitnum() {
		return hitnum;
	}
	public void setHitnum(int hitnum) {
		this.hitnum = hitnum;
	}

	
	
}
