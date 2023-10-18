package com.example.tokenbucket.ratelimit;

public class BucketKeyGenerator {

    public static String generateKey(String id, String httpMethod, String endPoint) {
        return String.format("%s:%s:%s", id, httpMethod, endPoint);
    }
}
