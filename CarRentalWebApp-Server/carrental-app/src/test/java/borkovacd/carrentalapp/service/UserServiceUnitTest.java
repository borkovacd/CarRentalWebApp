package borkovacd.carrentalapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import borkovacd.carrentalapp.enums.UserType;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /*@Test
    void shouldSavedUserSuccessFully() {
        final User user = new User(null, "ten@mail.com","teten","teten");

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.empty());
        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();

        verify(userRepository).save(any(User.class));

    }
    */ 
    
    /*
    @Test
    void shouldThrowErrorWhenSaveUserWithExistingEmail() {
        final User user = new User(1L, "ten@mail.com","teten","teten");

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        assertThrows(UserRegistrationException.class,() -> {
            userService.createUser(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }
    */

    /*
    @Test
    void updateUser() {
        final User user = new User(1L, "ten@mail.com","teten","teten");

        given(userRepository.save(user)).willReturn(user);

        final User expected = userService.updateUser(user);

        assertThat(expected).isNotNull();

        verify(userRepository).save(any(User.class));
    }
    */

    
    /*
    @Test
    void shouldReturnFindAll() {
        List<User> datas = new ArrayList();
        datas.add(new User(1L, "ten@mail.com","teten","teten"));
        datas.add(new User(2L, "ten@mail.com","teten","teten"));
        datas.add(new User(3L, "ten@mail.com","teten","teten"));

        given(userRepository.findAll()).willReturn(datas);

        List<User> expected = userService.findAllUsers();

        assertEquals(expected, datas);
    }
	*/
    
    @Test
    void findUserById(){
        final Long id = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse("1996-05-05",formatter);
	    final User user = new User(1L, "Samuel", "Stivens", "sstivens@gmail.com", "samuel",
	    		"$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom", "777 Brockton Avenue, Abington MA 2351", 
	    		dateOfBirth, "202-555-0194", UserType.USER, false, false, null);

        given(userRepository.findById(id)).willReturn(Optional.of(user));

        final User expected  = userService.findById(id);

        assertThat(expected).isNotNull();

    }

    /*
    @Test
    void shouldBeDelete() {
        final Long userId=1L;

        userService.deleteUserById(userId);
        userService.deleteUserById(userId);

        verify(userRepository, times(2)).deleteById(userId);
    }
    */

}