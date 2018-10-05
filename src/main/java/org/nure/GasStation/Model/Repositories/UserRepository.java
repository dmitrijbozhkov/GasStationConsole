package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {

    private ArrayList<User> userList = new ArrayList<User>();

    private Optional<User> findUser(String username) {
        return userList.stream().filter(f -> f.getUsername().equals(username)).findFirst();
    }

    @Override
    public void createUser(String username, String password, String role) throws EntityAlreadyExistsException {
        Optional<User> search = findUser(username);
        if (search.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("User with name of %s already exists", username));
        }
        userList.add(new User(username, password, role));
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        Optional<User> search = findUser(username);
        if (search.isPresent()) {
            return search.get();
        } else {
            throw new EntityNotFoundException(String.format("User by username %s doesn't exist", username));
        }
    }

    @Override
    public String getUserRole(String username) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void setPassword(String username, String password) throws EntityNotFoundException {

    }

    @Override
    public void setRole(String username, String role) throws EntityNotFoundException {

    }

    @Override
    public boolean checkUserCredentials(String username, String password) throws EntityNotFoundException {
        return false;
    }
}
