package com.carsaver.codereview.web.api;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import com.carsaver.codereview.service.ZipCodeLookupService;
import com.carsaver.codereview.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/*
* Changed Controller to RestController
* RestController is a convenience annotation which acts like both @Controller and @ResponseBody
*/
@RestController
public class UserApiController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ZipCodeLookupService zipCodeLookupService;

    @GetMapping("/users/create")
    public User createUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.enabled = false;

        /*
        * Changed create 'createuser' to camel-case 'createUser'
        * Added an value for the newUser.enabled field so it wasn't null
        * Removed the extra 'User' object
        * Combined the two if statements
        * Returned the results of the save
        */
        if(!email.contains("@test.com")) {
            newUser.enabled = true;
            emailService.sendConfirmation(email);
        }

        /*
        * Should we save an account with an invalid email?
         */
        return userRepository.save(newUser);
    }

    /**
     * updates user's address
     * @param id - assume valid existing id
     * @param zipCode - assume valid zipCode
     * @param city - assume valid if present otherwise null
     * @return updated User
     */
    @GetMapping("/users/updateLocation")
    public User updateUserLocation(@RequestParam Long id, @RequestParam String zipCode, @RequestParam(required = false) String city) {
        /*
        * Exception handling
         */
        User user = userRepository.findById(id).orElseThrow();

        user.setZipCode(zipCode);
        user.setCity(city != null ? city : zipCodeLookupService.lookupCityByZip(zipCode));
        //user.setCity(Optional.ofNullable(city).orElse(zipCodeLookupService.lookupCityByZip(zipCode)));

        return userRepository.save(user);
    }

    @GetMapping("/users/delete")
    public void deleteUser(@RequestParam String userId) {
        userRepository.deleteById(Long.parseLong(userId));
    }
}
