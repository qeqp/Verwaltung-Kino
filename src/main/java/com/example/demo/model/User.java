package com.example.demo.model;

import java.security.Principal;
import java.util.Set;

public interface User {
    String getUniqueString();

    void setUniqueString(String uniqueString);

    String getPassword();

    void setPassword(String password);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);
}
