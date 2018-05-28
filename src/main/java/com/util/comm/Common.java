package com.util.comm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.redis.RedisClient;
import com.util.DateUtils;
import com.util.HttpRequestUtil;

public class Common {
	private static final Logger LOG = Logger.getLogger(Common.class.getName());

	public static boolean send_code_channel(String content, String phone,
			String host) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Sn", "SDK-ZQ-CZWL-0736");
		map.put("Pwd", "Candzen_#10");
		map.put("mobile", phone);
		map.put("content", content);
		String retcode = "-1";
		try {
			String jsonResponse = HttpRequestUtil.request(host
					+ "/WebService.asmx/mt", map,
					HttpRequestUtil.REQUEST_TYPE_POST, "utf-8");
			Document document = DocumentHelper.parseText(jsonResponse);
			retcode = document.getStringValue();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if ("0".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用成功");
			return true;
		} else if ("-1".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用失败，失败原因：用户名或密码错误");
			return false;
		} else if ("-2".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用失败，失败原因：发送短信余额不足");
			return false;
		} else if ("-3".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用失败，失败原因：内容超过300个字");
			return false;
		} else if ("-4".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用失败，失败原因：IP不符合");
			return false;
		} else if ("-7".equals(retcode)) {
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
					+ " ========> 短信发送接口调用失败，失败原因：手机号错误");
			return false;
		} else if ("-404".equals(retcode)) {
			if ("http://114.215.202.188:8081".equals(host)) {
				LOG.debug(DateUtils
						.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")
						+ " ========> 短信发送接口调用失败，失败原因：系统异常");
			} else {
				send_code_channel(content, phone, "http://sms.4006555441.com");
			}
		}
		return false;
	}
	public static boolean send_code_channel(String content, String phone) {
		String retcode = "-1";
		String url = Constant.SEND_MSG_PATH;
		HttpResponse<String> resString = null;
		try {
			resString = Unirest.post(url)
					.field("account", Constant.MSG_ACCOUNT)
					.field("pswd", Constant.MSG_PSWD)
					.field("mobile", phone)
					.field("msg", content)
					.field("needstatus", "true")
					.field("product", "")
			.asString();
		} catch (UnirestException e) {
			e.printStackTrace();
			LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，连接短信服务器失败");
		}
		int resCode = 0;
		String resptime = "";
		String msgid = "";
		if (resString!=null) {
			resCode = resString.getStatus();
			if (resCode==404) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：404,phone:"+phone+",content:"+content);
				return false;
			}
			String resStr = resString.getBody();
			String[] ret = resStr.split("\n");
			String[] resSta = ret[0].split(",");
			resptime = resSta[0];
			retcode = resSta[1];
			if (ret.length>1) {
				msgid = ret[1];
			}
		}
		if (resCode==200) {
			if ("0".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用成功，发送时间["+resptime+"]，MSGID["+msgid+"]");
				return true;
			}else if ("101".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：无此用户");
				return false;
			}else if ("102".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：密码错");
				return false;
			}else if ("104".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：系统忙（因平台侧原因，暂时无法处理提交的短信）");
				return false;
			}else if ("105".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：敏感短信（短信内容包含敏感词）");
				return false;
			}else if ("106".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：消息长度错（内容要求控制在300字符以内）");
				return false;
			}else if ("107".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：包含错误的手机号码");
				return false;
			}else if ("108".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：手机号码个数错（群发>50000或<=0;单发>200或<=0）");
				return false;
			}else if ("109".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：无发送额度（该用户可用短信数已使用完）");
				return false;
			}else if ("112".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：无此产品，用户没有订购该产品");
				return false;
			}else if ("113".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：extno格式错（非数字或者长度不对）");
				return false;
			}else if ("115".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：自动审核驳回");
				return false;
			}else if ("116".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：签名不合法，未带签名（用户必须带签名的前提下）");
				return false;
			}else if ("117".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
				return false;
			}else if ("118".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：用户没有相应的发送权限");
				return false;
			}else if ("119".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：用户已过期");
				return false;
			}else if ("120".equals(retcode)) {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：内容不在白名单中");
				return false;
			}else {
				LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，失败原因：未知");
				return false;
			}
		}
		LOG.debug(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+ " ========> 短信发送接口调用失败，连接短信服务器失败");
		return false;
	}
	public static boolean send_code_by_phone(String phone) {
		boolean relust = false;
//		RedisClient.del_keys("user:validcode:"+phone+"*");
		String code = rand_num_code(6);
		System.out.println(DateUtils.DateToStr(new Date(), "")+"Verify Code: phone => "+phone+" ; code => "+code);
		String content =  "欢迎使用众米，验证码是:"+code+"。";
		boolean ret = send_code_channel(content, phone);
		if (ret) {
			String key = "user:validcode:"+phone+":"+code;
			JSONObject jobj = new JSONObject();
			jobj.put("verify_code", code);
			jobj.put("verify_phone", phone);
			jobj.put("start_date", new Date().getTime()/1000);
			relust =true;
			int rt = RedisClient.setex(key, jobj.toString(), 950);
			if (rt==0) {
				relust=false;
				System.out.println(DateUtils.DateToStr(new Date(), "")+"[set_verify_code_fail]====Verify Code: phone => "+phone+" ; code => "+code);
			}
		}
		return relust;
	}
	
	public static Json send_code_by_phone(String phone,String flag) {
		Json j = new Json();
		j.setSuccess(false);
		String code = rand_num_code(6);
		LOG.info("Verify Code: phone => "+phone+" ; code => "+code);
		String content =  "您的验证码是:"+code;
		if ("2".equals(flag)) {
			content = content+"，用户糖果积分兑换业务，请务必使用本人手机！本短信由系统自动发出，如非本人操作请忽略。";
		}
		try {
			RedisClient.setex("test", "1", 5);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setNum(-1);
			return j;
		}
		boolean ret = send_code_channel(content, phone);
		if (ret) {
			String key = "user:validcode:"+phone+":"+code;
			JSONObject jobj = new JSONObject();
			jobj.put("verify_code", code);
			jobj.put("verify_phone", phone);
			jobj.put("start_date", new Date().getTime()/1000);
			j.setResult(code);
			j.setSuccess(true);
			int rt = RedisClient.setex(key, jobj.toString(), 950);
			if (rt==0) {
				j.setSuccess(false);
				j.setNum(-1);
				LOG.info("[set_verify_code_fail]====Verify Code: phone => "+phone+" ; code => "+code);
				return j;
			}
		}
		return j;
	}

	// 获取9位随机数
	public static String rand_num_code(int num) {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < num; i++)
			result = result * 10 + array[i];

		String code = result + "";
		if (code.length() < num) {
			code = "0" + code;
		}
		return code;
	}
	// 获取范围随机数
	public static int rand_num_range(int min,int max) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	public static  JSONObject check_verification_code(String phone, String valid_code) {
		JSONObject jobj = new JSONObject();
		if ("".equals(phone)) {
			jobj.put("state", "error");
			jobj.put("msg", "no_phone_num");
			jobj.put("msg_desc", "手机号码错误");
			return jobj;
		}
		if ("".equals(valid_code)) {
			jobj.put("state", "error");
			jobj.put("msg", "no_valid_code");
			jobj.put("msg_desc", "短信验证码为空");
			return jobj;
		}
		if (!Common.validate_phone(phone)) {
			jobj.put("state", "error");
			jobj.put("msg", "wrong_phone_num");
			jobj.put("msg_desc", "手机号码错误");
			return jobj;
		}
		String key = "user:validcode:"+phone+":"+valid_code;
		String strvcode = RedisClient.get_value(key);
		JSONObject j = JSON.parseObject(strvcode);
		if (j==null||j.isEmpty()) {
			jobj.put("state", "error");
			jobj.put("msg", "verify_code_error");
			jobj.put("msg_desc", "短信验证码错误或失效");
			return jobj;
		}
		String start_date = j.getString("start_date");
		long starttime = Long.parseLong(start_date);
		long nowtime = new Date().getTime()/1000;
		long minute = (nowtime - starttime)/60;
		if (minute>Constant.EXPIRES_IN) {
			jobj.put("state", "error");
			jobj.put("msg", "valid_code_expires");
			jobj.put("msg_desc", "短信验证码失效");
			return jobj;
		}
		jobj.put("state", "success");
		jobj.put("msg", "check_verify_code_success");
		jobj.put("msg_desc", "短信验证码验证成功");
		return jobj;
	}
	public static boolean validate_phone(String phone) {
		Pattern p = Pattern
				.compile("^1[3|4|5|7|8][0-9]\\d{4,8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	public static boolean validate_email(String email) {
		Pattern p = Pattern
				.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	public static boolean validate_nickname(String nickname) {
		Pattern p = Pattern.compile("^[0-9a-zA-Z\u4e00-\u9fa5]{1,10}$");
		Matcher m = p.matcher(nickname);
		return m.matches();
	}
	
	public static void setHeaderAllowOrigin(HttpServletResponse response,String domain) {
		if (domain==null) {
			domain = "*";
		}
		response.addHeader("Access-Control-Allow-Origin",domain);
		response.addHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS"); 
		response.addHeader("Access-Control-Allow-Headers", "Content-type,hello");
		response.addHeader("Access-Control-Max-Age", "50");
	}
	public static void main(String[] args) {
		System.out.println("-----------------------------------");
		send_code_by_phone("15158152796", "2");
	}
}
