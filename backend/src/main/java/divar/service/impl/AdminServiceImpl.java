package divar.service.impl;

import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public List<AdvertisementResponse> getAllAdvertisements() {
        return List.of();
    }

    @Override
    public void approveAdvertisement(Long advertisementId) {

    }

    @Override
    public void rejectAdvertisement(Long advertisementId) {

    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {

    }

    @Override
    public void blockUser(Long userId) {

    }

    @Override
    public void unblockUser(Long userId) {

    }
}