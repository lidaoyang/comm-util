package com.alipay.config;
import com.alipay.util.UtilDate;

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088711473571556";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_user_id = partner;

	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMNvXTfNcWUODXZxRDuMhUrQeox1EValRvNKkxGPH42mzIxWB/ngEj6LmD6ynvcd84jJQaGgZ8rw5Glg3tHOlGVwrQHNF9qh9YAKC2ff/cfOnyrarjxTCtN53JVf1mCI95TJsmgZsTWSPg++XJ6usbMB3KYG8/TCBwPMhDG3wG0jAgMBAAECgYEAsSShHvJ1pP5uz3vtN/iv3NdJLUTzeVEIFX/z/Ed5d0DISS+1vRUQFGcdkT8AfcdFNigN+D1XtwhQUQAAF7YipJZUyflIpy5t15GqBhBHc3G9sdA03NMgG27Bse2m3Nz1fghFm7/iNHM812OI5uUYEljq8NDsPgIdWOqxiCy2H+kCQQDhDHfyMPqFkzzBM42ArbdxRwUP3O42zASoN0THI5BjmKjbl4YeBUpLl9+ZrtMenOAN/QC7Ujd+cGk4Ez528OtXAkEA3lBBL+wVa2u7HRX2n9iTBmK89E+yA6jgWLBtG2C1rXF0/e9+f9h2CYM/dBbzWCFx3gF32PjEP42TP8c6V6d5FQJBALbH1bOTLCXJDBMut8LHVLJgKDBWE/PViALHwMisjd0WZC7Vxkwjp8/1rdm4MCgw6Zjy3+FESWYcDplIp/cafUkCQQDF8ou4keuYlHotMctBFOQEwZIhr3yACbSJA76CrB/YabeUC9WFHEYtvjGXun++rzjmRkRia07Cj9rW5RwrxDDpAkEAwuz6IdwFdMJ/IYbVM4FOqSVG4roboObv0+yRCciCAm7YnvP4qKreDkZEimLwkOcZxJUj/vtYiTgaJ4M4KrBIgg==";
	
	// 支付宝公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm 
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/refund_fastpay_by_platform_pwd-JAVA-UTF-8/notify_url.jsp";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "/usr/local/tomcat/temp/alilog/";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	 //退款日期 时间格式 yyyy-MM-dd HH:mm:ss
    public static String refund_date = UtilDate.getDateFormatter();
		
	// 调用的接口名，无需修改
	public static String service = "refund_fastpay_by_platform_pwd";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

