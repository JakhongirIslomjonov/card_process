package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;
import uz.dev.cardprocess.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public DataDTO<LoginDTO> checkLoginDetails(LoginDTO loginDTO) {
        return null;
    }
}
