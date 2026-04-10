package ru.lebedev.dealership.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.services.UserService;
import ru.lebedev.dealership.controller.user.dto.UserInputDto;
import ru.lebedev.dealership.controller.user.dto.UserOutputDto;
import ru.lebedev.dealership.controller.user.mapper.UserMapper;
import ru.lebedev.dealership.domain.user.User;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UserInputDto userInputDto) {
        User user = userMapper.toEntity(userInputDto);
        Long id = userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<UserOutputDto> findByPhone(@RequestParam String phone) {
        return userService.findByPhone(phone)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}