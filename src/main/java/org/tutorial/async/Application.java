package org.tutorial.async;

import org.tutorial.async.service.UserInfoService;

public class Application {

    public static void main(String[] args) {
        UserInfoService.getInstance().fetchUserInfo(1).ifPresent(System.out::println);
    }
}
