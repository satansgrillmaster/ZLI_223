package ch.zli.m223.zli.init;

import java.util.*;

import javax.transaction.Transactional;

import ch.zli.m223.zli.model.impl.RoleImpl;
import ch.zli.m223.zli.model.impl.UserCountryImpl;
import ch.zli.m223.zli.model.impl.UserSalutationImpl;
import ch.zli.m223.zli.repository.UserCountryRepository;
import ch.zli.m223.zli.repository.UserSalutationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.zli.m223.zli.model.impl.AppUserImpl;
import ch.zli.m223.zli.repository.RoleRepository;
import ch.zli.m223.zli.repository.UserRepository;

@Component
public class ServerInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCountryRepository userCountryRepository;

    @Autowired
    private UserSalutationRepository userSalutationRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {

        RoleImpl roleAdmin = new RoleImpl("admin");
        RoleImpl roleUser = new RoleImpl("user");
        RoleImpl roleStaff = new RoleImpl("staff");


        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        roleRepository.save(roleStaff);

        Collection<AppUserImpl> adminUsers = new ArrayList<>();
        Collection<AppUserImpl> staffUsers = new ArrayList<>();
        Collection<AppUserImpl> users = new ArrayList<>();

        // initialize salutations
        UserSalutationImpl salutation1 = userSalutationRepository.save(new UserSalutationImpl("Herr"));
        UserSalutationImpl salutation2 = userSalutationRepository.save(new UserSalutationImpl("Frau"));
        UserSalutationImpl salutation3 = userSalutationRepository.save(new UserSalutationImpl("Andere"));

        // initialize countries
        UserCountryImpl country1 = userCountryRepository.save(new UserCountryImpl("CH","Schweiz"));
        UserCountryImpl country2 = userCountryRepository.save(new UserCountryImpl("DE","Deutschland"));
        UserCountryImpl country3 = userCountryRepository.save(new UserCountryImpl("AT","Österreich"));

        // Initialize users
        AppUserImpl user = userRepository.save(new AppUserImpl("user@user.ch", "user", 1, 1));
        users.add(user);

        AppUserImpl user2 = userRepository.save(new AppUserImpl("admin@admin.ch", "admin", 2, 2));
        adminUsers.add(user2);
        users.add(user2);


        AppUserImpl user3 = userRepository.save(new AppUserImpl("staff@staff.ch", "staff", 3, 3));
        users.add(user3);
        staffUsers.add(user3);

        AppUserImpl user4 = userRepository.save(new AppUserImpl("maetthe_hollenstein@hotmail.com", "password", 1, 1));
        adminUsers.add(user4);
        users.add(user4);


        // Set roles
        roleAdmin.setUsers(adminUsers);
        roleUser.setUsers(users);
        roleStaff.setUsers(staffUsers);

    }

}