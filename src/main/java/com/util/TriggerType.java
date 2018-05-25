package com.util;

public enum TriggerType {
	
	TYPE1("remind","推送提醒"),
	TYPE2("price","价格提醒"),
	TYPE3("indicator","技术分析"),
	TYPE4("notice","公司公告"),
	TYPE5("news","公司新闻"),
	TYPE6("trade_info","交易信息"),
	TYPE7("rel_stock","关联个股");
	
	private String name;
	private String desc;
	
	private TriggerType(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static boolean isExist(String name){
		for (TriggerType type : TriggerType.values()) {
			if (type.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
