package sk.tuke.kpi.kp.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.User;
import sk.tuke.kpi.kp.gamestudio.service.UserService;
import sk.tuke.kpi.kp.gamestudio.service.impl.UserServiceJPA;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserServiceRest {

    private final UserService userService = new UserServiceJPA();

    @GetMapping("/{game}/{username}")
    public User getUser(@PathVariable String game, @PathVariable String username) {
        return userService.getUser(game, username);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/{game}")
    public void deleteUser(@PathVariable String game) {
        userService.reset(game);
    }

}
