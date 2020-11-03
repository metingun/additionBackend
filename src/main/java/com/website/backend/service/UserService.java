package com.website.backend.service;

import com.website.backend.model.ChangePasswordModel;
import com.website.backend.model.UserModel;
import com.website.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String createUser(UserModel userModel) {
        userRepo.save(userModel);
        return "Saved Successfully";
    }

    public List<UserModel> getAll() {
        return userRepo.findAll();
    }
    public String saveAll(List<UserModel> userModels) {
        userRepo.saveAll(userModels);
        return "Saved Successfully";
    }
    public String changePassword(ChangePasswordModel changePasswordModel) {
        UserModel user = userRepo.findByUserNameAndPassword(changePasswordModel.getUserName(),changePasswordModel.getPassword());
        if (user != null) {
            user.setPassword(changePasswordModel.getNewPassword());
            userRepo.save(user);
            return "Change Password Successfully";
        }
        else{
            return null;
        }
    }

    public UserModel login(UserModel userModel) {
        UserModel user = userRepo.findByUserNameAndPassword(userModel.getUserName(),userModel.getPassword());
        if (user!=null) {
            userModel.setPower(user.getPower());
            return userModel;
        } else {
            return null;
        }
    }

    public UserModel getSession(UserModel userModel) {
        return login(userModel);
    }
}
