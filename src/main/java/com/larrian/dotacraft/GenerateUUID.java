package com.larrian.dotacraft;
import java.util.UUID;

public class GenerateUUID {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++ ) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid.toString().toUpperCase());
        }
    }
}