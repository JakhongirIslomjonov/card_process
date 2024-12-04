package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;
import uz.dev.cardprocess.dto.TokenDTO;
import uz.dev.cardprocess.service.JwtService;
import uz.dev.cardprocess.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public DataDTO<TokenDTO>    checkLoginDetails(LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        String token = jwtService.genToken((UserDetails) auth.getPrincipal());
        return new DataDTO<>(new TokenDTO(token));
    }
}
