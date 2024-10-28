package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    @PostMapping
    public void createUser(@RequestBody User newUser){
        userService.saveUser(newUser);

    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
       User userInDb = userService.findByUserName(userName);
       if(userInDb != null){
           userInDb.setUserName(user.getUserName());
           userInDb.setPassword(user.getPassword());
           userService.saveUser(userInDb);
       }

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
