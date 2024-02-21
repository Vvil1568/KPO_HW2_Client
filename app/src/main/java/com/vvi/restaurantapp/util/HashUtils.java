package com.vvi.restaurantapp.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) hexString.append(Integer.toHexString(0xFF & b));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("RestaurantApp","An error occurred while hashing the string",e);
            return null;
        }
    }
}
