package com.kevin.suishouji;

import java.util.HashMap;

public class ActivityManageHelp {

	private static ActivityManageHelp instance;

	public static ActivityManageHelp getInstance() {
		return instance = (null != instance) ? instance
				: new ActivityManageHelp();
	}

	@SuppressWarnings("rawtypes")
	private static final HashMap<String, Class> ACTIVITY_CLASS = new HashMap<String, Class>();
	{

		ACTIVITY_CLASS.put("HomePageActivity", HomePageActivity.class);
		ACTIVITY_CLASS.put("LookActivity", LookActivity.class);
		ACTIVITY_CLASS.put("AboutActivity", AboutActivity.class);

	}

	public Class<?> getActivityClass(String name) {
		return ACTIVITY_CLASS.get(name);
	}

	public boolean contains(String name) {
		return ACTIVITY_CLASS.containsKey(name);
	}
}
