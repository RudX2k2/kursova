package app.web;

import app.entities.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.seeder.SeederDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SeederDb seederDb;

    @Autowired
    public HomeController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          SeederDb seederDb) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.seederDb=seederDb;
        this.seederDb.SeedAllTables();
    }
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        User u = new User();
        model.addAttribute("user", u);
        return "create";
    }

    @PostMapping("/create")
    public String create(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
        {
            return "create";
        }
        if(userRepository.findByEmail(user.getEmail()) != null)
        {
            model.addAttribute("Error", "email occupied");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/";
    }
}
