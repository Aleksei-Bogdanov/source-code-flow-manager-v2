package iprody.controller;

import iprody.entity.User;
import iprody.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{name}")
    public Mono<User> getUser(@PathVariable String name){
        return userRepository.findByName(name);
    }

    @PostMapping
    public Mono<User> createMember(@RequestBody User user){
        return userRepository.save(user);
    }

}
