package uz.dev.cardprocess.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.TokenDTO;
import uz.dev.cardprocess.exceptions.BadRequestException;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/{id}")
    public DataDTO<TokenDTO> checkout(@PathVariable Integer id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("null keldi ");
        }
        return new DataDTO<>(new TokenDTO());
    }

    @GetMapping("/error")
    public String createError() {
        throw new RuntimeException("Test error for Sentry integration");
    }
}

