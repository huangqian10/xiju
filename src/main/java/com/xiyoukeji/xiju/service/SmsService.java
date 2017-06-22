package com.xiyoukeji.xiju.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.utils.JavaSmsApi;

@Service
public class SmsService {
	public void sendSms(String mobile, String message) {
		try {
			JavaSmsApi.sendMsg(mobile, message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
