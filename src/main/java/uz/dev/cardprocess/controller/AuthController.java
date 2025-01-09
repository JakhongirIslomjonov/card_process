package uz.dev.cardprocess.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;
import uz.dev.cardprocess.dto.SignUpDTO;
import uz.dev.cardprocess.dto.TokenDTO;
import uz.dev.cardprocess.service.UserService;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public DataDTO<TokenDTO> checkout(@RequestBody @Valid LoginDTO loginDTO) {
        return userService.checkLoginDetails(loginDTO);
    }

    @PostMapping("/sign-up")
    public DataDTO<String> signUp(@RequestBody  @Valid SignUpDTO signUp) {
        return userService.signUp(signUp);
    }


}

