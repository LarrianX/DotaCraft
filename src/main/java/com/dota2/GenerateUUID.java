package com.dota2;
import java.util.UUID;

public class GenerateUUID {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().toUpperCase());
    }
}