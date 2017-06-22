package com.xiyoukeji.xiju.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.model.Activity;


@Service("taskService")
@Transactional
public class TimeTaskService {
	
	@Autowired
	ActivityService activityService;
	public void endActivity(){
		int nowTime =(int)(System.currentTimeMillis()/1000);
		List<Activity> needResetActivity=activityService.getActivityForReset(nowTime);
		for(Activity activity:needResetActivity){
			
		}
		
	}
}
