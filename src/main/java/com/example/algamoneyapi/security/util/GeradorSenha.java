package com.example.algamoneyapi.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

     public static void main(String []args){
         BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
         System.out.println(enconder.encode("admin"));
     }
}
