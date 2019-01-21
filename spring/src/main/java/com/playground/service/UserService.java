package com.playground.service;

import com.playground.model.User;
import com.playground.model.VerificationToken;
import com.playground.repository.RoleRepository;
import com.playground.repository.UserRepository;
import com.playground.repository.VerificationTokenRepository;
import com.playground.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class UserService
 */
@Service
public class UserService implements IUserService {

    /** BCryptPasswordEncoder bCryptPasswordEncoder */
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /** UserRepository userRepository */
    private final UserRepository userRepository;

    /** RoleRepository roleRepository*/
    private final RoleRepository roleRepository;

    /** VerificationTokenRepository verificationTokenRepository */
    private final VerificationTokenRepository verificationTokenRepository;

    /** Environment env */
    private Environment env;

    /** ServletContext servletContext */
    private ServletContext servletContext;

    /**
     * UserService Constructor
     *
     * @param userRepository UserRepository
     */
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       VerificationTokenRepository verificationTokenRepository, Environment environment,
                       ServletContext servletContext) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.env = environment;
        this.servletContext = servletContext;
    }

    @Override
    public User signup(User user) {
        if (StringUtils.isEmpty(user.getMail())) {
            throw new RuntimeException("Mail is not valid");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_UNVERIFIED").get());
        user = userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getMail());
        mail.setFrom("g3.playground.app@gmail.com");
        mail.setSubject("Playground - Account verification");
        mail.setText("Bonjour,\n" +
                "\n" +
                "Merci de vous etre enregistré sur Playground. Voici votre lien de vérification :\n" +
                "\n" +
                servletContext.getContextPath() + "/verification_token/" + verificationToken.getToken() + "\n" +
                "\n" +
                "Amusez-vous bien!\n" +
                "\n" +
                "L'équipe Playground");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(env.getProperty("spring.mail.username"));
        javaMailSender.setHost(env.getProperty("spring.mail.host"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        javaMailSender.setPassword(env.getProperty("spring.mail.password"));
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        javaMailSender.setJavaMailProperties(mailProperties);

        javaMailSender.send(mail);

        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByMail(String mail) {
        return userRepository.findByMail(mail).orElse(null);
    }

    @Override
    public User getUserByMail(String mail) {
        return userRepository.findByMail(mail).orElse(null);
    }

    @Override
    public User updateUser(int id, User updatedUser){
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    @Override
    public User updateUserProfile(int id, User currentUser, User updatedUser){
        updatedUser.setId(id);
        updatedUser.setRole(currentUser.getRole());
        return userRepository.save(updatedUser);
    }

    @Override
    public User banUser(User user) {
        user.setBanned(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
