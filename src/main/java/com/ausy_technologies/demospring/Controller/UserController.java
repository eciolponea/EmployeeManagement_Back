package com.ausy_technologies.demospring.Controller;


import com.ausy_technologies.demospring.Mapper.UserMapper;
import com.ausy_technologies.demospring.Model.DAO.Role;
import com.ausy_technologies.demospring.Model.DAO.User;
import com.ausy_technologies.demospring.Model.DTO.UserDto;
import com.ausy_technologies.demospring.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/addRole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {


        Role roleAdded = this.userService.saveRole(role);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded","addNewRole");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(roleAdded);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User userAdded = this.userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded","addNewUser");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(userAdded);
    }

    @PostMapping("/addUser2/{idRole}")
    public ResponseEntity<User> saveUser2(@RequestBody User user, @PathVariable int idRole)
    {
        User userAdded = this.userService.saveUser2(user, idRole);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded","addNewUser with one role");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(userAdded);
    }

    @PostMapping("/addUser3/{roleList}")
    public ResponseEntity<User> saveUser3(@RequestBody User user , @PathVariable List<Role> roleList)
    {
        User userAdded = this.userService.saveUser3(user, roleList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded","addNewUser with multiple roles");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(userAdded);
    }

    @GetMapping("/getUserDtoById/{id}")
    public UserDto getUserDtoById(@PathVariable int id) {

            User user = this.userService.findUserById(id);
            return this.userMapper.convertToUserDto(user);

    }

    @GetMapping("/getAllUsersDto")
    public List<UserDto> getAllUsersDto()
    {
        List<User> users= this.userService.findAllUsers();

        return users
                .stream()
                .map(u-> this.userMapper.convertToUserDto(u)).collect(Collectors.toList());
    }

    @GetMapping("/findRoleBy/{id}")
    public ResponseEntity<Role> findRoleById(@PathVariable int id)
    {

        Role role = this.userService.findRoleById(id);
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }

    @GetMapping("/findUserBy/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id)
    {
        User user = this.userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/findAllRoles")
    public List<Role> findAllRoles()
    {
        return  userService.findAllRoles();
    }



    @GetMapping("/allUsers")
    public List<User> findAllUsers()
    {
        return this.userService.findAllUsers();
    }

    @DeleteMapping("/deleteUserById/{id}")
    public void deleteUser(@PathVariable int id)
    {
        this.userService.deleteUserById(id);
    }

    @PutMapping("updateUserById/{id}")
    public void updateUser(@RequestBody User user, @PathVariable int id) {
        User user2 = this.userService.findUserById(id);

        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setUsername(user.getUsername());
        user2.setPassword(user.getPassword());
        user2.setEmail(user.getPassword());
        user2.setBirthday(user.getBirthday());

        this.userService.saveUser(user2);
    }

}
