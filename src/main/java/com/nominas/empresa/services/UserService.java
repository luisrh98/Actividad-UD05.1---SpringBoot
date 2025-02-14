package com.nominas.empresa.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nominas.empresa.models.AppUser;
import com.nominas.empresa.models.Roles;
import org.springframework.security.core.userdetails.User;
import com.nominas.empresa.repositories.UserRepository;
import com.nominas.empresa.repositories.RolesRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        
        if (user == null) {
            System.out.println("🔴 Usuario no encontrado en la base de datos: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        System.out.println("🟢 Usuario encontrado: " + user.getUsername() + " con roles: " + user.getRoles());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Debe estar hasheada en la base de datos
                .roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
                .build();
    }


    public AppUser registerNewUserAccount(AppUser accountDto) throws Exception {
        if (userRepository.findByEmail(accountDto.getEmail()) != null) {
            throw new Exception("El correo ya está en uso");
        }

        // Verificar si el rol "USER" existe, si no, crearlo
        Roles userRoles = rolesRepository.findByName("USER");
        if (userRoles == null) {
            userRoles = new Roles();
            userRoles.setName("USER");
            rolesRepository.save(userRoles);
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(accountDto.getUsername());
        newUser.setEmail(accountDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        newUser.setEnabled(true);
        newUser.getRoles().add(userRoles);

        return userRepository.save(newUser);
    }
}
