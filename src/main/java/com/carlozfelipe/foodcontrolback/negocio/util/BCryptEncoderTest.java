package com.carlozfelipe.foodcontrolback.negocio.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoderTest {
    public static void main(String args[]){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for(int i=0; i<10;i++){
            String passEncode = encoder.encode("asdasd");
            System.out.println(passEncode);
        }

    }
}
