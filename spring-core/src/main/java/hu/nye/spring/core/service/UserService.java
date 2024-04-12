package hu.nye.spring.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.spring.core.entity.UserEntity;
import hu.nye.spring.core.exception.UserNotFoundException;
import hu.nye.spring.core.repistory.IUserRepository;
import hu.nye.spring.core.request.UserRequest;
import lombok.SneakyThrows;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserEntity saveUser(UserRequest userRequest) {
        UserEntity userEntity = UserEntity.builder()
            .name(userRequest.getName())
            .age(userRequest.getAge())
            .email(userRequest.getEmail())
            .registrationDate(userRequest.getRegistrationDate())
            .build();
        return userRepository.save(userEntity);
    }

    @Override
    @SneakyThrows
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @SneakyThrows
    public UserEntity updateUser(Long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setName(userRequest.getName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setAge(userRequest.getAge());
        userEntity.setRegistrationDate(userRequest.getRegistrationDate());
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAllByAge(int age){
        return  userRepository.findAllByAge(age);
    }
}
