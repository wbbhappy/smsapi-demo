package com.jsms.api.xpt.demo;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
/**
 *  Http Demo for Java
 * 采用jodd-3.7.1 调用
 * 参考文档《短信http接口文档.doc》
 * @ClassName: ApiDemo4Jodd
 * @Description: TODO
 * @author Peng_Hu
 * @date 2016年8月11日 下午1:38:31
 *
 */
public class ApiDemo4Jodd {
	/**
	 * 短信提供商开设账号时提供一下参数:
	 * 账号、密码、通信key、IP、端口
	 */
	static String account = "JSM42755";
	static String password = "0zv1u1og";
	static String veryCode = "4wjykdxz17uh";
	static String http_url  = "http://112.74.76.186:8030";
	/**
	 *  发送普通短信  普通短信发送需要人工审核
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return
	 * String
	 */
	public static String sendSms(String mobile,String content){
		HttpRequest httpRequest = HttpRequest.post(http_url+"/service/httpService/httpInterface.do");
		httpRequest.query("method", "sendMsg");
		httpRequest.query("username", account);
		httpRequest.query("password", password);
		httpRequest.query("veryCode", veryCode);
		httpRequest.query("mobile", mobile);
		httpRequest.query("content", content);
		httpRequest.query("code", "utf-8");
		httpRequest.query("msgtype", "1");
		httpRequest.header("Content-Type", "charset=UTF-8");
		HttpResponse httpResponse = httpRequest.send();
		httpResponse.charset("UTF-8");
		String result = httpResponse.bodyText();
		System.out.println(result);
		return result;
	}
	/**
	 * 模版短信,无需人工审核，直接发送
	 *   (短信模版的创建参考客户端操作手册)
	 *   模版：@1@会员，动态验证码@2@(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *   参数值:@1@=某某,@2@=4293
	 *   手机接收内容：【短信签名】某某会员，动态验证码4293(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *   注意:发送模板短信变量值不能包含英文逗号和等号（, =）
	 * @param mobile 手机号码
	 * @param tplId 模板编号，对应客户端模版编号
	 * @param content 模板参数值，以英文逗号隔开，如：@1@=某某,@2@=4293
	 * @return xml字符串，格式请参考文档说明
	 */
	public static String sendTplSms(String mobile,String tplId,String content){
		HttpRequest httpRequest = HttpRequest.post(http_url+"/service/httpService/httpInterface.do");
		httpRequest.query("method", "sendMsg");
		httpRequest.query("username", account);
		httpRequest.query("password", password);
		httpRequest.query("veryCode", veryCode);
		httpRequest.query("mobile", mobile);
		httpRequest.query("content", content);	//变量值，以英文逗号隔开
		httpRequest.query("msgtype", "2");		//2-模板短信
		httpRequest.query("tempid", tplId);		//模板编号
		httpRequest.query("code", "utf-8");
		httpRequest.header("Content-Type", "charset=UTF-8");
		HttpResponse httpResponse = httpRequest.send();
		httpResponse.charset("UTF-8");
		String result = httpResponse.bodyText();
		System.out.println(result);
		return result;
	}
	/***
	 * 查询下发短信的状态报告
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryReport(){
		HttpRequest httpRequest = HttpRequest.post(http_url+"/service/httpService/httpInterface.do?method=queryReport");
		httpRequest.query("username", account);
		httpRequest.query("password", password);
		httpRequest.query("veryCode", veryCode);
		httpRequest.header("Content-Type", "charset=UTF-8");
		HttpResponse httpResponse = httpRequest.send();
		httpResponse.charset("UTF-8");
		String result = httpResponse.bodyText();
		System.out.println(result);
		return result;
	}
	/**
	 * 查询上行回复短信
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryMo(){
		HttpRequest httpRequest = HttpRequest.post(http_url+"/service/httpService/httpInterface.do?method=queryMo");
		httpRequest.query("username", account);
		httpRequest.query("password", password);
		httpRequest.query("veryCode", veryCode);
		httpRequest.header("Content-Type", "charset=UTF-8");
		HttpResponse httpResponse = httpRequest.send();
		httpResponse.charset("UTF-8");
		String result = httpResponse.bodyText();
		System.out.println(result);
		return result;
	}
	public static void main(String[] args) {
		//发送普通短信，需要审核
		//sendSms("15100317880","测试短信，无需理会。");
		//发送模板短信，无需审核
		sendTplSms("15100317880","JSM42755-0001","@1@=6666");
		//sendTplSms("15100317880","JSM42755-0002","@1@=hp,@2@=8888");
		//获取短信状态报告
		queryReport();
		//获取上行回复短信
		//queryMo();
	}
}
