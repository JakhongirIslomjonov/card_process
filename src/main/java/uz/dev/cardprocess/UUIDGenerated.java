package uz.dev.cardprocess;

import java.util.UUID;

public class    UUIDGenerated {
    public static void main(String[] args) {
        UUID idempotencyKey = UUID.randomUUID();
        System.out.println("Generated Idempotency Key: " + idempotencyKey);
    }
}
