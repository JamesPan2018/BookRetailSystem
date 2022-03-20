package com.jpan.retail.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INTERNAL_SERVER_ERROR(600, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
	CANNOT_SAVE_NULL_CONTENT(601, "Cannot save null content", HttpStatus.BAD_REQUEST),
	CANNOT_SAVE_EXISTING_CONTENT(602, "Cannot save existing content", HttpStatus.BAD_REQUEST),
	MEMBER_NOT_FOUND(603, "Member was not found", HttpStatus.BAD_REQUEST),
	MEMBER_INVALID_TYPE(604, "Member type is invalid", HttpStatus.BAD_REQUEST);


	ErrorCode(int code, String msg, HttpStatus httpStatus) {
		this.errorCode = code;
		this.errorMsg = msg;
		this.httpStatus = httpStatus;
	}

	private final int errorCode;
	private final String errorMsg;
	private final HttpStatus httpStatus;

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}