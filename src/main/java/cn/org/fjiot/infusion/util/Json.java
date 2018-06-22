package cn.org.fjiot.infusion.util;

import java.io.Serializable;

public class Json implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean code;
	
	private String message;
	
	private Object data;
	
	public Json(boolean code) {
		this(code, null);
	}
	
	public Json(boolean code, String message) {
		this(code, message, null);
	}
	
	public Json(boolean code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public boolean isCode() {
		return code;
	}

	public void setCode(boolean code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
