package divar.controller;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.entity.User;
import divar.enums.AdStatus;
import divar.service.AdvertisementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import divar.dto.request.SearchAdvertisementRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }


    @PostMapping
    public AdvertisementResponse create(
            @Valid @RequestBody CreateAdvertisementRequest request,
            Authentication authentication) {

        System.out.println("=== CREATE ADVERTISEMENT ===");
        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());

        User owner = (User) authentication.getPrincipal();

        return advertisementService.create(request, owner);
    }

    @PostMapping("/search")
    public Page<AdvertisementResponse> search(
            @Valid@RequestBody SearchAdvertisementRequest request) {

        return advertisementService.search(request);
    }

    @GetMapping("/{id}")
    public AdvertisementResponse getById(@PathVariable Long id) {
        return advertisementService.findById(id);
    }

    @GetMapping
    public List<AdvertisementResponse> getAll() {
        return advertisementService.findAll();
    }

    @GetMapping("/status/{status}")
    public List<AdvertisementResponse> getByStatus(@PathVariable AdStatus status) {
        return advertisementService.findByStatus(status);
    }

    @GetMapping("/category/{categoryId}")
    public List<AdvertisementResponse> getByCategory(@PathVariable Long categoryId) {
        return advertisementService.findByCategory(categoryId);
    }

    @GetMapping("/city/{cityId}")
    public List<AdvertisementResponse> getByCity(@PathVariable Long cityId) {
        return advertisementService.findByCity(cityId);
    }

    @GetMapping("/owner/{ownerId}")
    public List<AdvertisementResponse> getByOwner(@PathVariable Long ownerId) {
        return advertisementService.findByOwner(ownerId);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<AdvertisementResponse> uploadImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {

        AdvertisementResponse response =
                advertisementService.uploadImage(id, image);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public AdvertisementResponse update(@PathVariable Long id,
                                        @Valid@RequestBody UpdateAdvertisementRequest request) {
        return advertisementService.update(id, request);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long imageId) {

        advertisementService.deleteImage(imageId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        advertisementService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/sold")
    public AdvertisementResponse markAsSold(@PathVariable Long id) {

        return advertisementService.markAsSold(id);
    }


}