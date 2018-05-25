package com.util;


public enum TriggerField {
	REMIND("price_remind","价格提醒",TriggerType.TYPE1),
	PRICE0("follow_price","关注价格",TriggerType.TYPE2),
	PRICE1("target_price","目标价格",TriggerType.TYPE2),
	PRICE2("day_up","日涨幅",TriggerType.TYPE2),
	PRICE3("day_dn","日跌幅",TriggerType.TYPE2),
	PRICE4("min5_up","5分钟涨幅",TriggerType.TYPE2),
	PRICE5("min5_dn","5分钟跌幅",TriggerType.TYPE2),
	INDICATOR1("MACD","MSCD",TriggerType.TYPE3),
	INDICATOR2("RSI","RSI",TriggerType.TYPE3),
	INDICATOR3("BOLL","BOLL",TriggerType.TYPE3),
	INDICATOR4("KDJ","KDJ",TriggerType.TYPE3),
	NOTICE("notice","公司最新公告信息",TriggerType.TYPE4),
	NEWS("news","公司最新新闻资讯",TriggerType.TYPE5),
	TRADE_INFO("trade_info","交易所最新交易信息",TriggerType.TYPE6),
	REL_STOCK1("rel_day_up","关联个股日涨幅",TriggerType.TYPE7),
	REL_STOCK2("rel_day_dn","关联个股日跌幅",TriggerType.TYPE7);
	
	private String name;
	private String desc;
	private TriggerType type;
	
	private TriggerField(String name, String desc, TriggerType type) {
		this.name = name;
		this.desc = desc;
		this.type = type;
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

	public TriggerType getType() {
		return type;
	}

	public void setType(TriggerType type) {
		this.type = type;
	}
	
	public static boolean isExist(String name){
		for (TriggerField field : TriggerField.values()) {
			if (field.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
