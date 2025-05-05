package com.example.Conference.room.reservation.system.models.users;

import com.example.Conference.room.reservation.system.entities.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetailsResponse {

    public MyUserDetailsResponse(MyUser myUser) {
        this.id = myUser.getId();
        this.username = myUser.getUsername();
        this.email = myUser.getEmail();
        this.role = myUser.getRole().toString();
    }

    private Long id;
    private String username;
    private String email;
    private String role;
}
