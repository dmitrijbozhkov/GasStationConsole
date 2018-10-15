package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.nure.GasStation.Model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserRepository {// implements IUserRepository {
//
//    private ArrayList<User> userList = new ArrayList<User>();
//
//    private Optional<User> findUser(String username) {
//        return userList.stream().filter(f -> f.getUsername().equals(username)).findFirst();
//    }
//
//    @Override
//    public void createUser(String username, String password, UserRoles role) throws EntityAlreadyExistsException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            throw new EntityAlreadyExistsException(String.format("User with name of %s already exists", username));
//        }
//        userList.add(new User(username, password, role));
//    }
//
//    @Override
//    public User getUserByUsername(String username) throws EntityNotFoundException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            return search.get();
//        } else {
//            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
//        }
//    }
//
//    @Override
//    public UserRoles getUserRole(String username) throws EntityNotFoundException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            return search.get().getRoles();
//        } else {
//            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
//        }
//    }
//
//    @Override
//    public void setPassword(String username, String password) throws EntityNotFoundException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            search.get().setPassword(password);
//        } else {
//            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
//        }
//    }
//
//    @Override
//    public void setRole(String username, UserRoles role) throws EntityNotFoundException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            search.get().setRoles(role);
//        } else {
//            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
//        }
//    }
//
//    @Override
//    public boolean checkUserCredentials(String username, String password) throws EntityNotFoundException {
//        Optional<User> search = findUser(username);
//        if (search.isPresent()) {
//            if (search.get().getPassword().equals(password)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
//        }
//    }
}
