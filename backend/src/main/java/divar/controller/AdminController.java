package divar.controller;

import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/advertisements")
    public List<AdvertisementResponse> getAllAdvertisements() {
        return adminService.getAllAdvertisements();
    }

    @PutMapping("/advertisements/{id}/approve")
    public void approve(@PathVariable("id") Long id) {
        adminService.approveAdvertisement(id);
    }

    @PutMapping("/advertisements/{id}/reject")
    public void reject(@PathVariable("id") Long id) {
        adminService.rejectAdvertisement(id);
    }

    @DeleteMapping("/advertisements/{id}")
    public void deleteAdvertisement(@PathVariable("id") Long id) {
        adminService.deleteAdvertisement(id);
    }

    @PutMapping("/users/{id}/block")
    public void blockUser(@PathVariable("id") Long id) {
        adminService.blockUser(id);
    }

    @PutMapping("/users/{id}/unblock")
    public void unblockUser(@PathVariable("id") Long id) {
        adminService.unblockUser(id);
    }
}