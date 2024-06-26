package org.example.userservice.user;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 100, nullable = false, unique = true)
    private String email; // 인증시 필요한 필드
    @Column(length = 256, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String username;
    @Column(length = 255, nullable = false)
    private String address;
    @Column(length = 100,  nullable = false)
    private String phoneNumber;

//    @Column(length = 30)
//    @Convert(converter = StringArrayConverter.class)
//    private List<String> roles = new ArrayList<>(); // role은 한 개 이상

    @Builder
    public User(long id, String email, String password, String username, String address, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
//        this.roles = roles;
    }


    public void updateInfo(String password, String address, String phoneNumber){
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
