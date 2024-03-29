package cn.dmdream.vo;

public class JsonMsg {

	public final static int STATUS_SUCCESS = 200;//成功标志
	public final static int STATUS_FAIL = 400;//失败标志
	public final static int STATUS_ERROR = 500;//服务出错
	public final static int STATUS_AUTH = 100;//未授权服务
	
	private int status;//标志位,代表处理结果
	private String msg;//提示信息
	private Object data;//数据
	//Get&Set
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/*
	 * 成功方法
	 */
	public static JsonMsg makeSuccess(String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_SUCCESS);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 失败方法
	 */
	public static JsonMsg makeFail (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_FAIL);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 系统错误
	 */
	public static JsonMsg makeError (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_ERROR);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 无权限
	 */
	public static JsonMsg makeAuth (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_AUTH);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
}
