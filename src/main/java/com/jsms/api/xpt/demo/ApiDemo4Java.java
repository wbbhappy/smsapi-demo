package com.jsms.api.xpt.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * @ClassName: ApiDemo
 * @Description: TODO
 *
 */
public class ApiDemo4Java {
	/**
	 * 短信提供商开设账号时提供一下参数:
	 *
	 * 账号、密码、通信key、IP、端口
	 */
	static String account = "JSM42755";
	static String password = "0zv1u1og";
	static String veryCode = "4wjykdxz17uh";
	static String http_url  = "http://112.74.76.186:8030";
	/**
	 * 默认字符编码集
	 */
	public static final String CHARSET_UTF8 = "UTF-8";
	/**
	 * 查询账号余额
	 * @return 账号余额，乘以10为短信条数
	 * String xml字符串，格式请参考文档说明
	 */
	public static String getBalance(){
		String balanceUrl = http_url + "/service/httpService/httpInterface.do?method=getAmount";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",account);
		params.put("password",password);
		params.put("veryCode",veryCode);
		String result = sendHttpPost(balanceUrl, params);
		return result;
	}
	/**
	 * 发送普通短信  普通短信发送需要人工审核
	 * 请求地址：
	 *   UTF-8编码：/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK编码：/service/httpService/httpInterface.do?method=sendGbkMsg
	 * @param mobile 手机号码, 多个号码以英文逗号隔开,最多支持100个号码
	 * @param content 短信内容
	 * @return
	 * String xml字符串，格式请参考文档说明
	 */
	public static String sendSms(String mobile,String content){
		String sendSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("msgtype", "1");
		params.put("code", "utf-8");
		String result = sendHttpPost(sendSmsUrl, params);
		return result;
	}
	/**
	 * 模版短信,无需人工审核，直接发送
	 *   (短信模版的创建参考客户端操作手册)
	 *   模版：@1@会员，动态验证码@2@(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *   参数值:@1@=某某,@2@=4293
	 *   手机接收内容：【短信签名】某某会员，动态验证码4293(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *
	 *   请求地址：
	 *   UTF-8编码：/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK编码：/service/httpService/httpInterface.do?method=sendGbkMsg
	 *   注意：
	 * 	1.发送模板短信变量值不能包含英文逗号和等号（, =）
	 *  2.特殊字符进行转义:
	 *  	+   %2b
	 *  	空格    %20
	 * 		&   %26
	 * 		%	%25
	 * @param mobile 手机号码
	 * @param tplId 模板编号，对应客户端模版编号
	 * @param content 模板参数值，以英文逗号隔开，如：@1@=某某,@2@=4293
	 * @return xml字符串，格式请参考文档说明
	 */
	public static String sendTplSms(String mobile,String tplId,String content){
		String sendTplSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);	//变量值，以英文逗号隔开
		params.put("msgtype", "2");		//2-模板短信
		params.put("tempid", tplId);	//模板编号
		params.put("code", "utf-8");
		String result = sendHttpPost(sendTplSmsUrl, params);
		return result;
	}
	/***
	 * 查询下发短信的状态报告
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryReport(){
		String reportUrl = http_url + "/service/httpService/httpInterface.do?method=queryReport";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(reportUrl, params);
		return result;
	}
	/**
	 * 查询上行回复短信
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryMo(){
		String moUrl = http_url + "/service/httpService/httpInterface.do?method=queryMo";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(moUrl, params);
		return result;
	}
	/***
	 *
	 * @param apiUrl 接口请求地址
	 * @param paramsMap 请求参数集合
	 * @return xml字符串，格式请参考文档说明
	 * String
	 */
	private static String sendHttpPost(String apiUrl, Map<String, String> paramsMap) {
		String responseText = "";
		StringBuilder params = new StringBuilder();
		Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		try {
			URL url = new URL(apiUrl);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CHARSET_UTF8);
			out.write(params.toString()); //post的关键所在！
			out.flush();
			out.close();
			//读取响应返回值
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,CHARSET_UTF8));
			String temp = "";
			while (( temp = br.readLine()) != null) {
				responseText += temp;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//查询账号余额
		System.out.println(getBalance().toString());
		//发送普通短信(需要审核)，修改接收短信的手机号码及短信内容,多个号码以英文逗号隔开，最多支持100个号码
		//System.out.println(sendSms("159*******1,159*******2", "您的验证码是8888,请注意保密，勿将验证码告知他人。"));
		/**
		 * 发送模板短信，修改接收短信的手机号码及调用的模板编号
		 * 注意：
		 * 	1.发送模板短信变量值不能包含英文逗号和等号（, =）
		 *  2.特殊字符进行转义:
		 *  	+   %2b
		 *  	空格    %20
		 * 		&   %26
		 * 		%	%25
		 */
		//System.out.println(sendTplSms("15100317880","JSM42755-0001","@1@=331,@2@=asdd"));
		//查询下发短信的状态报告
		//System.out.println(queryReport());
		//查询上行回复短信
		//System.out.println(queryMo());
	}
}
