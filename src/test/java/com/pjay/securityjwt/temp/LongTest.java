package com.pjay.securityjwt.temp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongTest {

    @Test
    public void long_test() throws Exception {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // when
        if(number1 == number2){
            System.out.println("테스트 : 동일합니다");
        } else {
            System.out.println("테스트 : 동일하지 않습니다");
        }

        Long amount1 = 100L;
        Long amount2 = 1000L;

        if(amount1 < amount2){
            System.out.println("테스트 : amount1이 작습니다");
        } else {
            System.out.println("테스트 : amount1이 큽니다");
        }


        // then
    }

    @Test
    public void long_test2() throws Exception {
        // given
        Long v1 = 128L;
        Long v2 = 128L;

        // when
        if(v1 < v2){
            System.out.println("테스트 : v1이 작습니다");
        } else if(v1 == v2){
            System.out.println("테스트 : 같습니다");
        } else if(v1 > v2){
            System.out.println("테스트 : v1이 큽니다");
        } else if(v1.longValue() == v2.longValue()){
            System.out.println("테스트 : longValue()를 이용하여 비교 v1과 v2는 같습니다");
        }
    }

    @Test
    public void long_test3() throws  Exception {
        // given
        Long v1 = 128L;
        Long v2 = 128L;

        assertThat(v1).isEqualTo(v2);
    }
}
