package uz.dev.cardprocess;

import java.util.UUID;

public class UUIDGenerated {
    public static void main(String[] args) {
        // Tasodifiy UUID yaratish
        UUID idempotencyKey = UUID.randomUUID();

        // Natijani konsolga chiqarish
        System.out.println("Generated Idempotency Key: " + idempotencyKey);
    }
}
