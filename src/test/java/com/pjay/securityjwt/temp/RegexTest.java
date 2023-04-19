package com.pjay.securityjwt.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void 한글만된다_test() {
        String value = "홓s";
        boolean result = Pattern.matches("^[가-힣]+$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    @Test
    public void 한글은안된다_test() {
        String value = "ㅎ";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생,
        System.out.println("테스트 : "+result);
    }

    @Test
    public void 영어만된다_test() {
        String value = "sss1";
        boolean result = Pattern.matches("^[a-zA-Z]+$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    @Test
    public void 영어는안된다_test() {
        String value = "a";
        boolean result = Pattern.matches("^[^a-zA-Z]*$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생,
        System.out.println("테스트 : "+result);
    }

    @Test
    public void 영어와숫자만된다_test() {
        String value = "sssㄹㄴㄹㅇ";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    @Test
    public void 영어만되고_길이는최소2최대4이다_test() {
        String value = "ssddd";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    // username, email, fullname

    // username : 영어/숫자 2~20자
    @Test
    public void username_test() {
        String value = "sss12";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    @Test
    public void email_test() {
        String value = "test@test.com";
        boolean result = Pattern.matches("[a-zA-Z0-9]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}",value);
        System.out.println("테스트 : "+result);

    }

    // fullname : 영문, 한글, 길이 1~20
    @Test
    public void fullname_test(){
        String value = "쌀";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$",value); // ^ : 시작, & : 끝, + : 1번 이상의 발생
        System.out.println("테스트 : "+result);
    }

    @Test
    public void account_gubun_test1(){
        String value = "DEPOSIT";
        boolean result = Pattern.matches("^(DEPOSIT)$",value); // 정확한 문자를 하려면 ()를 하면 된다.
        System.out.println("테스트 : "+result);
    }
    @Test
    public void account_gubun_test2(){
        String value = "TRANSFER";
        boolean result = Pattern.matches("^(DEPOSIT|TRANSFER)$",value); // 정확한 문자를 하려면 ()를 하면 된다.
        System.out.println("테스트 : "+result);
    }

    @Test
    public void account_tel_test1(){
        String value = "010-1111-2222";
        boolean result = Pattern.matches("^[0-9]{3}-[0-9]{4}-[0-9]{4}",value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void account_tel_test2(){
        String value = "01011112222";
        boolean result = Pattern.matches("^[0-9]{11}",value);
        System.out.println("테스트 : "+result);
    }
}
