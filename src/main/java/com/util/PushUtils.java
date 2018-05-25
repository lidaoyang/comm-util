package com.util;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class PushUtils {

	/**友盟推送（按别名推送）
	 * @param alias_type	别名类型
	 * @param alias		别名
	 * @param device_type	设备类型
	 * @param push_type		推送类型(0:通知推送,1:静默推送)
	 * @param p_jo	推送对象
	 * @return 推送结果
	 */
	public static JSONObject push_ym(String alias_type,String alias,String device_type,String push_type,JSONObject p_jo) {
		JSONObject ret = new JSONObject();
		try {
			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			String url = "";
			com.alibaba.fastjson.JSONObject root = new com.alibaba.fastjson.JSONObject();
			String appMasterSecret = "";
			root.put("timestamp", timestamp);
//			root.put("device_tokens", device_tokens+","+device_tokens2);
			root.put("type", "customizedcast");// unicast-单播,listcast-列播(要求不超过500个device_token), customizedcast(通过开发者自有的alias进行推送), 包括以下两种case: - alias: 对单个或者多个alias进行推送
			root.put("alias_type", alias_type);
			root.put("alias", alias);
			root.put("production_mode", "false");
			root.put("description", p_jo.getString("desc"));
			com.alibaba.fastjson.JSONObject payload = new com.alibaba.fastjson.JSONObject();
			if ("ios".equals(device_type)) {
				appMasterSecret = "";
				root.put("appkey", "");
				com.alibaba.fastjson.JSONObject aps = new com.alibaba.fastjson.JSONObject();
//				aps.put("alert", "这是一条测试推送20%%"); 
				//iOS10 新增带title，subtile的alert格式如下
				com.alibaba.fastjson.JSONObject alert = new com.alibaba.fastjson.JSONObject();
				alert.put("title", p_jo.getString("title"));
				if (p_jo.containsKey("subtitle")) {
					alert.put("subtitle", p_jo.getString("subtitle"));
				}
				alert.put("body", p_jo.getString("text"));
				aps.put("alert", alert);
				if ("1".equals(push_type)) {
					aps.put("content-available", 1);
				}
				payload.put("aps", aps);
				//扩展信息"d","p"为友盟保留字段，
				if (p_jo.containsKey("extra")) {
					payload.putAll(p_jo.getJSONObject("extra"));
				}
			}else if ("android".equals(device_type)){
				appMasterSecret = "";
				root.put("appkey", "");
				com.alibaba.fastjson.JSONObject body = new com.alibaba.fastjson.JSONObject();
				body.put("ticker", p_jo.getString("ticker"));// 必填 通知栏提示文字
				body.put("title", p_jo.getString("title"));// 必填 通知标题
				body.put("text", p_jo.getString("text")); // 必填 通知文字描述 
				body.put("after_open", "go_app");
				payload.put("body", body);
				//扩展信息可选 用户自定义key-value。只对"通知"
				if (p_jo.containsKey("extra")) {
					payload.put("extra", p_jo.getJSONObject("extra"));
				}
				payload.put("display_type", "notification");
				if ("1".equals(push_type)) {
					payload.put("display_type", "message");
				}
			}
			root.put("payload", payload);
	        String postBody = root.toJSONString();
	        String sign = DigestUtils.md5Hex(("POST" + url + postBody +appMasterSecret).getBytes("utf8"));
	        url = url + "?sign=" + sign;
	        HttpResponse<String> httpResponse = Unirest.post(url)
	        		.header("User-Agent", "Mozilla/5.0")
	        		.body(postBody)
	        		.asString();
	        ret = JSON.parseObject(httpResponse.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static void main(String[] args) {
//		String device_tokens = "f115367d700c9985585f84c581a20008304ad414fa356eb3596dbf52daf98f31";
//		String alias ="25614"+device_tokens;
		String device_tokens = "AmPQoOk_hFdKvngZZafBBD80HO3rBTpUy-KNUeWzLXXp";
		String alias ="26145"+device_tokens;
		for (int i = 0; i < 5; i++) {
			JSONObject p_jo = new JSONObject();
			p_jo.put("ticker", "提示"+i);
			p_jo.put("title", "测试推送"+i);
//			p_jo.put("subtitle", "测试推送2");
			p_jo.put("text", "这是一条测试alias推送20%%"+i);
			p_jo.put("desc", "测试"+i);
			System.out.println(push_ym("priceUpdn", alias, "android", "0", p_jo));
		}
	}
}
