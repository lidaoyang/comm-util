package com.util.comm;

public class Constant {
	
	/**
	 * 阿里云图片上传服务器用户名
	 */
	public final static String SFTP_REQ_USERNAME = "robot";
	/**
	 * 阿里云图片上传服务器密码
	 */
	public final static String SFTP_REQ_PASSWORD = "candzen";
	/**
	 * 阿里云图片上传服务器端口号
	 */
	public final static String SFTP_REQ_PORT = "25280";
	/**
	 * 阿里云图片上传根目录
	 */
	public final static String IMAGE_BASE_PATH = "/mnt/usr/local/tomcat/apache-tomcat-7.0.63_8081/webapps/mimikj/images";
//	public final static String IMAGE_BASE_PATH = "F:/workspace3/images";
	/**
	 *权息数据路径
	 */
	public final static String STOCK_RIGHT_DATA_PATH = "/usr/local/tomcat/temp/wsSHSZ_SPLITs.txt";
//	public final static String STOCK_RIGHT_DATA_PATH = "C:/wsWDZ/wsSHSZ_SPLITs.txt";
	/**
	 *K线wdz数据路径
	 */
	public final static String KLINE_DATA_PATH = "/usr/local/tomcat/temp/klines_data/wstock_US_20171001_10_Day.zip";
//	public final static String KLINE_DATA_PATH = "E:\\workspace3\\港股\\历史K线数据\\wstock_HK_1998-20170704_Day.zip";
	/**
	 *用户默认头像
	 */
	public final static String USER_DEFALUT_PIC = "user/default/default.png";
	/**
	 * 发送短信接口的host(志晴短信机，停用)
	 */
	public final static String SEND_MSG_HOST = "http://sms.4006555441.com";
	/**
	 * 发送短信接口的地址(示远短信机，使用中)
	 */
	public final static String SEND_MSG_PATH = "http://send.18sms.com/msg/HttpBatchSendSM";
	/**
	 * 发送短信的账号
	 */
	public final static String MSG_ACCOUNT = "004256"; //004256 正式 002002 测试
	/**
	 * 发送短信的账号
	 */
	public final static String MSG_PSWD = "Zhongmiapp889";  //Zhongmiapp889 正式 Sy123456 测试
	/**
	 * 短信验证码过期分钟
	 */
	public final static int EXPIRES_IN = 15;
	
	/**
	 * leancloud 推送URL
	 */
	public final static String PUSH_REMIND_URL = "https://cn.avoscloud.com/1.1/push";
	
	/**
	 *极光推送URL
	 */
	public final static String JPUSH_REMIND_URL = "https://api.jpush.cn/v3/push";
	/**
	 *极光推送APPKEY
	 */
	public final static String JPAPPKEY ="86d269801e79955f8664a430";
	/**
	 *极光推送MASTERSECRET
	 */
	public final static String JPMASTERSECRET = "5075e42463be47bce6842392";
	/**
	 *微信公众号网页授权URL域名
	 */
	public final static String WX_AUTHORIZE_URL = "https://api.weixin.qq.com";
	
	/**
	 *微信公众号网页授权AppID
	 */
	public final static String WX_APPID = "wxaef9d201c98c1e20";
	
	/**
	 *微信公众号网页授权AppSecret
	 */
	public final static String WX_APPSECRET = "c9632b7e0006026e1dda074ce1f9fb0a";
	
}
