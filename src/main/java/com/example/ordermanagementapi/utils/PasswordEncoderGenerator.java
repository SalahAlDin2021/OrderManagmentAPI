/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 8:53 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//this application used for encode password if you need
//for example, if you need to add admin using database
//, you need to generate password for admin using this(not set the password as plain text)
public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("ahmadmohsen.com123"));
        System.out.println(passwordEncoder.encode("ahmadmohsen.com123"));

    }
}