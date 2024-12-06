package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.LoginDTO;
import uz.dev.cardprocess.dto.SignUpDTO;
import uz.dev.cardprocess.dto.TokenDTO;
import uz.dev.cardprocess.entity.User;
import uz.dev.cardprocess.entity.enums.RoleName;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.repository.RoleRepository;
import uz.dev.cardprocess.repository.UserRepository;
import uz.dev.cardprocess.service.JwtService;
import uz.dev.cardprocess.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

        @Override
        public DataDTO<TokenDTO> checkLoginDetails(LoginDTO loginDTO) {
            String token;
            try {
                Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                token = jwtService.genToken((UserDetails) auth.getPrincipal());
            } catch (AuthenticationException e) {
                throw new BadRequestException(e.getMessage());
            }
            return new DataDTO<>(new TokenDTO(token));
        }

    @Override
    public DataDTO<String> signUp(SignUpDTO signUp) {
        if ((userRepository.findByEmail(signUp.getEmail()).isEmpty())) {
            userRepository.save(
                    User.builder()
                            .email(signUp.getEmail())
                            .fullName(signUp.getFullName())
                            .password(passwordEncoder.encode(signUp.getPassword()))
                            .roles(Objects.nonNull(roleRepository.findByRoleName(RoleName.ROLE_CLIENT)) ? List.of(roleRepository.findByRoleName(RoleName.ROLE_CLIENT)) : Collections.emptyList())
                            .build());
            return new DataDTO<>("success sign up");
        }
        return new DataDTO<>("error  already sign up  process");
    }
}
