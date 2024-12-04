package uz.dev.cardprocess.service;

import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;
import uz.dev.cardprocess.dto.SignUp;
import uz.dev.cardprocess.dto.TokenDTO;

@Service
public interface UserService {
    DataDTO<TokenDTO> checkLoginDetails(LoginDTO loginDTO);

    DataDTO<String> signUp(SignUp signUp);
}
