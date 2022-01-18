package com.example.geowalk.models;

import com.example.geowalk.models.entities.SwearWord;
import com.example.geowalk.models.repositories.SwearWordRepo;
import com.example.geowalk.models.repositories.UserRepo;
import com.example.geowalk.services.ImageService;
import com.example.geowalk.utils.SwearWordsFilter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DbSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final SwearWordRepo swearWordRepo;
    private final PasswordEncoder passwordEncoder;
    private final SwearWordsFilter swearWordsFilter;

    @Resource
    private ImageService imageService;

    public DbSeeder(UserRepo userRepo, SwearWordRepo swearWordRepo, PasswordEncoder passwordEncoder, SwearWordsFilter swearWordsFilter) {
        this.userRepo = userRepo;
        this.swearWordRepo = swearWordRepo;
        this.passwordEncoder = passwordEncoder;
        this.swearWordsFilter = swearWordsFilter;
    }

    @Override
    public void run(String... args) {
        //Encode user passwords
        userRepo.findAll().forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        });

        //Seed swear-words.json
        JSONParser parser = new JSONParser();
        try {
            String swearWordsFile = "src/main/resources/static/swear-words.json";
            Object obj = parser.parse(new FileReader(swearWordsFile));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                swearWordRepo.save(new SwearWord(o.toString()));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //Load swear words into SwearWordsFilter
        swearWordsFilter.loadSwearWordsFromDatabase();

        //Images init
        imageService.init();
    }
}
