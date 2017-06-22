package com.xiyoukeji.xiju.service;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
	@Autowired
	RedisCache redisCache;
	
	public Integer[] StringtoInt(String str) {
		Integer ret[] = new Integer[str.split(";").length];
		StringTokenizer toKenizer = new StringTokenizer(str, ";");
		int i = 0;
		while (toKenizer.hasMoreElements()) {
			ret[i++] = Integer.valueOf(toKenizer.nextToken());
		}
		return ret;
	}
}
