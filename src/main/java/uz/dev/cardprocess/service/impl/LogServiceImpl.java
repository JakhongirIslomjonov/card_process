package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.exceptions.BadRequestException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl {

    @Async
    public void writeLog(String path, String message) {
        String currentDir = System.getProperty("user.dir"); // Joriy ish papkasini olish
        String formattedFileName = String.format("%s-%s.log", "log", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        String newMessage = MessageFormat.format("{0} ------> {1}", LocalDateTime.now(), message);
        Path filePath = Paths.get(currentDir, path, formattedFileName);
        try {

            Path logDirPath = filePath.getParent();
            if (logDirPath != null && !Files.exists(logDirPath)) {
                Files.createDirectories(logDirPath); // Papkalarni yaratish
            }

            Files.writeString(filePath, newMessage + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("Internal server error");
        }

    }

    @Async
    public void writeLog(String path, String message, String jsonFormatString) {
        writeLog(path, MessageFormat.format("{0}: {1}", message, jsonFormatString));
    }

    @Async
    public void writeLog(String path, String message, Long id) {
        writeLog(path, MessageFormat.format("{0}: {1}", message, id));
    }
}
