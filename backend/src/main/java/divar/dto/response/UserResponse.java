package divar.dto.response;

import divar.enums.UserRole;
import divar.enums.UserStatus;

public class UserResponse {

    private Long id;

    private String fullName;

    private String username;

    private String phoneNumber;

    private String email;

    private UserRole role;

    private double averageRating;

    private int ratingCount;

    private UserStatus status;

    public UserResponse(){}

    public UserResponse(Long id, String fullName, String username, String phoneNumber, String email,
                        UserRole role, double averageRating, int ratingCount, UserStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}