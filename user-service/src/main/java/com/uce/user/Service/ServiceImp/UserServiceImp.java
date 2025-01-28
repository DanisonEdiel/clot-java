package com.uce.user.Service.ServiceImp;

import java.util.List;

import org.bson.types.ObjectId;

import com.uce.user.Model.User;

public interface UserServiceImp {
    User registerUser(User user);

    User findByUser(ObjectId id);

    List<User> getAll();
}
