package com.icommerce.nab.order.service.demo;

import com.icommerce.nab.entity.user.Account;

import java.util.Date;

public class DemoDataUtils {

    private DemoDataUtils() {
    }

    public static Account createSimpleAccount() {
        Account account = new Account();
        account.setEmailAddress("chuong_123@mailnator.com");
        account.setStartDate(new Date(System.currentTimeMillis()));
        account.setClosed(false);
        account.setUserName("chuong_123");
        account.setPhoneNum("123456789");
        return account;
    }
}
