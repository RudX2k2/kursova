package app.seeder;

import app.constants.Roles;
import app.entities.Role;
import app.entities.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Transactional
@Service
public class SeederDb {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SeederDb(UserRepository userRepository,
                    RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void SeedAllTables(){
        SeedRoles();
        SeedUsers();
    }

    public void SeedRoles(){
        if(roleRepository.count()==0) {
            Role role = new Role();
            role.setName(Roles.Admin);
            roleRepository.save(role);

            role = new Role();
            role.setName(Roles.User);
            roleRepository.save(role);
        }
    }

    public void SeedUsers(){
        if(userRepository.count()==0) {
            User admin = new User();
            admin.setBalance(999999.999);
            admin.setEmail("admin@gmail.com");
            admin.setName("Jotaro");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Arrays.asList(
                    roleRepository.findByName(Roles.Admin)));

            userRepository.save(admin);
        }
    }
}
