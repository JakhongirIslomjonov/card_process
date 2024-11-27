package uz.dev.cardprocess.service;

import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;

@Service
public interface UserService {
  DataDTO<LoginDTO> checkLoginDetails(LoginDTO loginDTO);
}
