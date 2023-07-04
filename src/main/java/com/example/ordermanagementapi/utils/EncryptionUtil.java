/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 1:40 AM
 * Project Name: OrderManagmentAPI
 */

package com.example.ordermanagementapi.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;


// used for encrypt the id of resources if needed
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "my-secret-key";  // Remember to store this securely!

    public static String encryptId(int id) {
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(Integer.toString(id).getBytes());
            return Base64.getEncoder().encodeToString(encValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int decryptId(String encryptedId) {
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedId);
            byte[] decValue = c.doFinal(decodedValue);
            return Integer.parseInt(new String(decValue));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
