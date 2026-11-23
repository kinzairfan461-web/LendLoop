package com.lendloop.firstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Authstore {

    private static final String PREF = "LendLoopUsers";

    private static String hashPassword(String password, byte[] salt)
            throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), salt, 120000, 256
        );

        byte[] hash = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec)
                .getEncoded();

        spec.clearPassword();

        return Base64.encodeToString(hash, Base64.NO_WRAP);
    }

    public static boolean register(
            Context context, String name,
            String email, String password) {

        try {
            SharedPreferences prefs =
                    context.getSharedPreferences(
                            PREF, Context.MODE_PRIVATE);

            String key = email.trim().toLowerCase();

            if (prefs.contains("salt_" + key)) {
                return false;
            }

            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            String saltString =
                    Base64.encodeToString(salt, Base64.NO_WRAP);

            String passwordHash = hashPassword(password, salt);

            return prefs.edit()
                    .putString("name_" + key, name)
                    .putString("salt_" + key, saltString)
                    .putString("hash_" + key, passwordHash)
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int login(
            Context context, String email, String password) {

        try {
            SharedPreferences prefs =
                    context.getSharedPreferences(
                            PREF, Context.MODE_PRIVATE);

            String key = email.trim().toLowerCase();

            if (!prefs.contains("salt_" + key)) {
                return 0;
            }

            byte[] salt = Base64.decode(
                    prefs.getString("salt_" + key, ""),
                    Base64.NO_WRAP
            );

            String savedHash =
                    prefs.getString("hash_" + key, "");

            String enteredHash = hashPassword(password, salt);

            return MessageDigest.isEqual(
                    savedHash.getBytes("UTF-8"),
                    enteredHash.getBytes("UTF-8")
            ) ? 1 : 2;

        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }
}
