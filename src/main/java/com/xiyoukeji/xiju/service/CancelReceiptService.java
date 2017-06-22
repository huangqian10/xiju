package com.xiyoukeji.xiju.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CancelReceiptService {
	
	@Autowired
	ReceiptService receiptService;
	
	@Autowired
	VoucherService voucherService;
	
	public void cancel(Integer receiptId){
		
	}
}
