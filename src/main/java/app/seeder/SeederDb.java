package app.seeder;

import app.constants.Roles;
import app.entities.Product;
import app.entities.Role;
import app.entities.User;
import app.repositories.ProductRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
    private final ProductRepository productRepository;


    @Autowired
    public SeederDb(UserRepository userRepository,
                    RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder,
                    ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }
    public void SeedAllTables(){
        SeedRoles();
        SeedUsers();
        SeedProducts();
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

    public void SeedProducts(){
        if(productRepository.count() == 0){
            Product product = new Product();
            product.setName("Nothing");
            product.setAmount(0);
            product.setWeight(0.0);
            product.setDescription("Some chemical");
            product.setSrc("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8iNi_c2tN9jxhbLgOfrEed8th28H1f-QsNA&usqp=CAU");
            productRepository.save(product);
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
