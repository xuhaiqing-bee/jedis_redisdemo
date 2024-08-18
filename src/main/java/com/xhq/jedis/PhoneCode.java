package com.xhq.jedis;

import redis.clients.jedis.Jedis;

import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.Scanner;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.jedis
 * @Project：jedis_redisdemo
 * @name：PhoneCoe
 * @Date：2024/8/17 15:08
 * @Filename：PhoneCode
 */
public class PhoneCode {
    public static void main(String[] args) {
        verifyCode("12345678901", "444");
        verifyPhone("12345678901");
    }

    /**
     * 校验验证码
     * @param phone
     * @param code
     */
    private static void verifyCode(String phone, String code) {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        // 验证码key
        String codeKey = "verifyCode:" + phone + ":code";
        String code1 = jedis.get(codeKey);
        if (code1 == null) {
            System.out.println("验证码已过期");
        } else if (code1.equals(code)) {
            System.out.println("验证码正确");
        } else {
            System.out.println("验证码错误");
        }
        jedis.close();
    }

    /**
     * 生成验证码,每个手机只能发送三次,每次验证码存到codeKey中
     * @param phone
     *
     */
    private static void verifyPhone(String phone) {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        // 发送次数key
        String countKey = "verifyCode:" + phone + ":count";
        // 验证码key
        String codeKey = "verifyCode:" + phone + ":code";
        // 获取发送次数
        String count = jedis.get(countKey);
        if (count == null) {
            // 发送次数为空
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            // 发送次数小于等于2
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            // 发送次数超过限制
            System.out.println("发送次数超过限制");
            jedis.close();
            //退出
            return;
        }
        // 生成验证码
        String v_code = generateVerificationCode(6);
        // 将验证码存入redis
        jedis.setex(codeKey, 120, v_code);
        jedis.close();

    }

    // 生成指定长度的验证码的方法
    private static String generateVerificationCode(int codeLength) {
        // 定义验证码字符集
        String codeChars = "0123456789";
        StringBuilder verificationCode = new StringBuilder();   // 使用StringBuilder来拼接验证码
        // 创建Random对象
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {  // 循环生成指定长度的验证码
            char randomChar = codeChars.charAt(random.nextInt(codeChars.length()));   // 从字符集中随机选择一个字符
            verificationCode.append(randomChar); // 将选定的字符追加到验证码中
        }
        return verificationCode.toString();  // 返回生成的验证码字符串
    }

}
