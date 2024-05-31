package client.controller;

import entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static client.constants.PathConstants.*;

@RequestMapping(USER_PROFILE)
@Controller
public class UserProfileController {
    @GetMapping
    public ResponseEntity<User> showUserData(@SessionAttribute User user) {
        return new ResponseEntity<>(user, user == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }


}
