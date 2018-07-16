package com.biyouche.test;

import com.biyouche.enums.SmsEnue;
import com.biyouche.message.SmsUtil;

public class Sms {
	public static void main(String[] args) {
		boolean send = SmsUtil.send("", SmsEnue.CODE_05.getCode(), "1234");
		System.out.println(send);
	}

}
