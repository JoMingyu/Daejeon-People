package com.planb.support.routing;

public class RESTResource implements Comparable<RESTResource> {
	private String functionCategory;
	private String summary;
	private String method;
	private String uri;
	private String requestHeaders;
	private String params;
	private String requestBody;
	private int successCode;
	private String responseHeaders;
	private String responseBody;
	private int failureCode;
	private String etc;
	
	@Override
	public int compareTo(RESTResource o) {
		return o.functionCategory.compareTo(this.functionCategory);
	}
	
	public RESTResource(String functionCategory, String summary, String method, String uri, String requestHeaders, String params, String requestBody, int successCode, String responseHeaders, String responseBody, int failureCode, String etc) {
		this.setFunctionCategory(functionCategory);
		this.setSummary(summary);
		this.setMethod(method);
		this.setUri(uri);
		this.setRequestHeaders(requestHeaders);
		this.setParams(params);
		this.setRequestBody(requestBody);
		this.setSuccessCode(successCode);
		this.setResponseHeaders(responseHeaders);
		this.setResponseBody(responseBody);
		this.setFailureCode(failureCode);
		this.setEtc(etc);
	}

	public String getFunctionCategory() {
		return functionCategory;
	}

	public void setFunctionCategory(String functionCategory) {
		this.functionCategory = functionCategory;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public int getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(int successCode) {
		this.successCode = successCode;
	}

	public String getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(String responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public int getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(int failureCode) {
		this.failureCode = failureCode;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}
}
