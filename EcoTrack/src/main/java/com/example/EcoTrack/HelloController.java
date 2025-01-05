package com.example.EcoTrack;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*") 
@RestController
public class HelloController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsageService usageService;

    private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    private Optional<Token> validateToken(HttpServletRequest req)
    {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            String token = authHeader.substring(7);
            return tokenService.findByToken(token);
        }
        return Optional.empty();
    }

    @RequestMapping(path = "/login")
    public String user(@RequestBody User us) {
        List<User> users = userService.findByEmail(us.email);
        User u = users.get(0);
        if (users.size() == 1 && bCryptPasswordEncoder.matches(us.password, u.password)){

            Optional<Token> existingToken = tokenService.findByEmail(us.email);
            if(existingToken.isPresent() && !tokenService.isTokenExpired(existingToken.get()))
            {
                return "Authentication : " + existingToken.get().getToken();
            }
            else
            {
                Token t = new Token(us.email);
                tokenService.save(t);
                return "Authentication : " + t.token;
            }
        }
        return "Authentication Failed";
    }
    @RequestMapping(path = "/register")
    public String registerUser(@RequestBody User us)
    {
        // for (User u : userService.findall())
        // {
        //     if (u.email.equals(us.email)) {
        //         return "User already exists";
        //     }
        // }
        if(userService.findByEmail(us.email).size() == 1)
        {
            return "User already exists";
        }

        final double waterPerPerson = 100;  // in liters
        final double waterPerRoom = 50;     // in liters
        final double electricityPerPerson = 2 ;   // in kWh
        final double electricityPerRoom = 2;     // in kWh
        us.totalWaterUsageLimit = (waterPerPerson * us.men_count) + (waterPerRoom * us.room_count);
        us.totalElectricityUsageLimit = (electricityPerPerson * us.men_count) + (electricityPerRoom * us.room_count);
        us.password = bCryptPasswordEncoder.encode(us.password);
        userService.save(us);
        Token t = new Token(us.email);
        tokenService.save(t);
        return "Authorization : " + t.token+" "+us.totalWaterUsageLimit+" "+us.totalElectricityUsageLimit;
    }

    
    @RequestMapping(path = "/userData")
    public ResponseEntity<User> getUserData(HttpServletRequest req)
    {
        Optional<Token> dbToken = validateToken(req);
        if (dbToken.isPresent() && !tokenService.isTokenExpired(dbToken.get()))
        {
            String email = dbToken.get().getEmail();
            List<User> users = userService.findByEmail(email);
            return ResponseEntity.ok(users.get(0));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @RequestMapping(path = "/allUsers")
    public ResponseEntity<List<User>> findAll(HttpServletRequest req)
    {
        Optional<Token> dbToken = validateToken(req);
        if (dbToken.isPresent() && !tokenService.isTokenExpired(dbToken.get()))
        {
            return ResponseEntity.ok(userService.findall());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }

    @RequestMapping(path = "/authtoken")
    public ResponseEntity<Optional<Token>> tokenString(HttpServletRequest req)
    {
        Optional<Token> dbToken = validateToken(req);
        return ResponseEntity.ok(dbToken);
    }

    @PostMapping(path = "/updateUsage")
    public ResponseEntity<Usage> addUsage(HttpServletRequest req, @RequestBody Usage usage)
    {
        Optional<Token> dbToken = validateToken(req);
        if (dbToken.isPresent() && !tokenService.isTokenExpired(dbToken.get()))
        {
            String email = dbToken.get().getEmail();
            usage.setEmail(email);

            usageService.save(usage);

            UsageResponse ur = usageService.getTotalAndRecentUsage(email);

            int totalWaterUsage = (int) ur.getTotalWaterUsage();
            int totalElectricityUsage = (int) ur.getTotalElectricityUsage();

            int totalWaterUsageLimit = (int) userService.findByEmail(email).get(0).totalWaterUsageLimit;
            int totalElectricityUsageLimit = (int) userService.findByEmail(email).get(0).totalElectricityUsageLimit;

            emailSenderService.sendEmail(email, "EcoTrack: Says hi", " water and electricity. Please use.");

            if (totalWaterUsage > totalWaterUsageLimit && totalElectricityUsage > totalElectricityUsageLimit)
            {
              
                emailSenderService.sendEmail(email, "EcoTrack: Usage Limit Exceeded", "You have exceeded your usage limit of both water and electricity. Please reduce your usage.");
            }
            else if (totalWaterUsage > totalWaterUsageLimit)
            {
                emailSenderService.sendEmail(email, "EcoTrack: Water Usage Limit Exceeded", "You have exceeded your water usage limit. Please reduce your usage.");
            }
            else if (totalElectricityUsage > totalElectricityUsageLimit)
            {
                emailSenderService.sendEmail(email, "EcoTrack: Electricity Usage Limit Exceeded", "You have exceeded your electricity usage limit. Please reduce your usage.");
            }
            System.out.println("--------------------");
            System.out.println("Total Water Usage Limit: " + totalWaterUsageLimit);
            System.out.println("Total Electricity Usage Limit: " + totalElectricityUsageLimit);
            System.out.println("Total Water Usage: " + totalWaterUsage);
            System.out.println("Total Electricity Usage: " + totalElectricityUsage);
            return ResponseEntity.ok(usage);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @RequestMapping(path = "/getUsage")
    public ResponseEntity<UsageResponse>getMethodName(HttpServletRequest req) {

        Optional<Token> dbToken = validateToken(req);
        if (dbToken.isPresent() && !tokenService.isTokenExpired(dbToken.get()))
        {
            String email = dbToken.get().getEmail();
            UsageResponse ur = usageService.getTotalAndRecentUsage(email);
            return ResponseEntity.ok(ur);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @RequestMapping(path = "/getDailyUsage")
    public ResponseEntity<List<DailyUsage>> getDailyUsages(HttpServletRequest req) {

        Optional<Token> dbToken = validateToken(req);
        if (dbToken.isPresent() && !tokenService.isTokenExpired(dbToken.get()))
        {
            String email = dbToken.get().getEmail();
            List<DailyUsage> dailyUsages = usageService.getDailyUsages(email);
            return ResponseEntity.ok(dailyUsages);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
}
