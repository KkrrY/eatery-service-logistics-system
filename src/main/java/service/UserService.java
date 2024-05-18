package service;

import entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    User getUserInfo(String email);

    Page<User> getAllUsers(Pageable pageable);

//    List<Perfume> getCart(List<Long> perfumeIds);

    User updateUserInfo(String email, User user);

//    DataFetcher<List<User>> getAllUsersByQuery();
//
//    DataFetcher<User> getUserByQuery();
}

