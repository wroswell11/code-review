package com.carsaver.codereview.web.api;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import com.carsaver.codereview.service.EmailService;
import com.carsaver.codereview.service.ZipCodeLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserApiControllerTest {
    private UserApiController spyController;
    private UserRepository mockRepo;
    private EmailService mockEmailService;
    private ZipCodeLookupService mockZipService;

    private String firstName = "Test";
    private String lastName = "User";
    private String inactiveEmail = "test.user@test.com";
    private String activeEmail = "test.user@nottest.com";
    private String city = "Franklin";
    private String zipCode = "37067";

    private User testUser;

    @BeforeEach
    public void setup() {
        spyController = spy(new UserApiController());
        mockRepo = mock(UserRepository.class);
        mockEmailService = mock(EmailService.class);
        mockZipService = mock(ZipCodeLookupService.class);

        spyController.userRepository = mockRepo;
        spyController.emailService = mockEmailService;
        spyController.zipCodeLookupService = mockZipService;

        testUser = new User();
        testUser.setFirstName(firstName);
        testUser.setLastName(lastName);
    }

    @Test
    public void createUser_createsNewUserAndSavesToDB() {
        testUser.setEmail(activeEmail);
        testUser.enabled = true;

        when(mockRepo.save(any(User.class))).thenReturn(testUser);

        User testUser = spyController.createUser(firstName, lastName, activeEmail);

        verify(mockRepo, times(1)).save(any(User.class));
    }

    @Test
    public void createUser_enablesUser_ifUserEmailHasCorrectDomain() {
        testUser.setEmail(activeEmail);
        testUser.enabled = true;

        when(mockRepo.save(any(User.class))).thenReturn(testUser);

        spyController.createUser(firstName, lastName, activeEmail);

        verify(mockEmailService, times(1)).sendConfirmation(anyString());
    }

/*    @Test
    public void updateUserLocation_updatesUserProfileWithCityAndZip() {
        testUser.setCity(city);
        testUser.setZipCode(zipCode);
        Optional<User> moctionalUser = mock(Optional.class);

        when(mockRepo.findById(anyLong())).thenReturn(moctionalUser);
        //when(moctionalUser.orElseThrow()).thenReturn(testUser);
        when(mockRepo.save(any(User.class))).thenReturn(testUser);

        spyController.updateUserLocation(1L, zipCode, city);

        verify(mockZipService, never()).lookupCityByZip(anyString());
        verify(mockRepo, times(1)).save(any(User.class));
    }

    @Test
    public void updateUserLocation_updatesUserProfileWithZip_ifCityIsNull() {
        testUser.setCity(city);
        testUser.setZipCode(zipCode);

        when(mockRepo.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(testUser));
        when(mockZipService.lookupCityByZip(anyString())).thenReturn("37067");
        when(mockRepo.save(any(User.class))).thenReturn(testUser);

        spyController.updateUserLocation(1L, zipCode, null);

        verify(mockZipService, times(1)).lookupCityByZip(anyString());
        verify(mockRepo, times(1)).save(any(User.class));
    }*/
}