package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.SearchAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.entity.*;
import divar.enums.AdStatus;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import divar.dto.request.UpdateAdvertisementRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceImplTest {

    @Mock
    AdvertisementRepository advertisementRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    AdvertisementImageRepository advertisementImageRepository;

    @InjectMocks
    AdvertisementServiceImpl service;

    private User owner;
    private Category category;
    private City city;
    private Advertisement advertisement;

    @BeforeEach
    void setup(){

        owner = new User();
        owner.setId(1L);
        owner.setFullName("Mohammad");

        category = new Category();
        category.setId(1L);
        category.setName("Laptop");

        city = new City();
        city.setId(1L);
        city.setName("Tehran");

        advertisement =
                new Advertisement(
                        "MacBook",
                        "M3",
                        50000L,
                        owner,
                        category,
                        city);

        advertisement.setId(10L);
        advertisement.setStatus(AdStatus.ACTIVE);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(owner, null)
        );
    }

    @Test
    void create_ShouldSaveAdvertisement(){

        CreateAdvertisementRequest request =
                new CreateAdvertisementRequest(
                        "MacBook",
                        "M3",
                        50000L,
                        1L,
                        1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(cityRepository.findById(1L))
                .thenReturn(Optional.of(city));

        when(advertisementRepository.save(any()))
                .thenReturn(advertisement);

        AdvertisementResponse response =
                service.create(request,owner);

        assertNotNull(response);
        assertEquals("MacBook",response.getTitle());

        verify(advertisementRepository).save(any());
    }

    @Test
    void create_ShouldThrow_WhenCategoryNotFound(){

        CreateAdvertisementRequest request =
                new CreateAdvertisementRequest(
                        "Mac",
                        "Desc",
                        10L,
                        1L,
                        1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->service.create(request,owner));
    }

    @Test
    void create_ShouldThrow_WhenCityNotFound(){

        CreateAdvertisementRequest request =
                new CreateAdvertisementRequest(
                        "Mac",
                        "Desc",
                        10L,
                        1L,
                        1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(cityRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->service.create(request,owner));
    }

    @Test
    void findById_ShouldReturnAdvertisement(){

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        AdvertisementResponse response =
                service.findById(10L);

        assertEquals(10L,response.getId());
    }

    @Test
    void findById_ShouldThrow_WhenNotFound(){

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class,
                ()->service.findById(10L));
    }

    @Test
    void search_ShouldReturnAdvertisements(){

        SearchAdvertisementRequest request =
                new SearchAdvertisementRequest();

        Page<Advertisement> page =
                new PageImpl<>(List.of(advertisement));

        when(advertisementRepository.findAll(
                any(Specification.class),
                any(Pageable.class)))
                .thenReturn(page);

        Page<AdvertisementResponse> response =
                service.search(request);

        assertEquals(1,response.getContent().size());
    }

    @Test
    void search_ShouldThrow_WhenMinGreaterThanMax(){

        SearchAdvertisementRequest request =
                new SearchAdvertisementRequest();

        request.setMinPrice(1000.0);
        request.setMaxPrice(100.0);

        assertThrows(BadRequestException.class,
                ()->service.search(request));
    }
    @Test
    void update_ShouldUpdateAdvertisement() {

        UpdateAdvertisementRequest request =
                new UpdateAdvertisementRequest();

        request.setTitle("New Title");
        request.setDescription("New Description");
        request.setPrice(90000L);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(advertisementRepository.save(any()))
                .thenReturn(advertisement);

        AdvertisementResponse response =
                service.update(10L, request);

        assertNotNull(response);

        verify(advertisementRepository).save(advertisement);
    }
    @Test
    void delete_ShouldDeleteAdvertisement() {

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        service.delete(10L);

        assertEquals(AdStatus.DELETED,
                advertisement.getStatus());

        verify(advertisementRepository).save(advertisement);
    }
    @Test
    void delete_ShouldThrow_WhenAlreadyDeleted() {

        advertisement.setStatus(AdStatus.DELETED);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                () -> service.delete(10L));
    }
    @Test
    void markAsSold_ShouldSuccess() {

        advertisement.setStatus(AdStatus.ACTIVE);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(advertisementRepository.save(any()))
                .thenReturn(advertisement);

        AdvertisementResponse response =
                service.markAsSold(10L);

        assertNotNull(response);

        assertEquals(AdStatus.SOLD,
                advertisement.getStatus());
    }
    @Test
    void markAsSold_ShouldThrow_WhenAlreadySold() {

        advertisement.setStatus(AdStatus.SOLD);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                () -> service.markAsSold(10L));
    }
    @Test
    void markAsSold_ShouldThrow_WhenNotActive() {

        advertisement.setStatus(AdStatus.PENDING);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                () -> service.markAsSold(10L));
    }
    @Test
    void uploadImage_ShouldSuccess() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        "test.jpg",
                        "image/jpeg",
                        "image".getBytes(StandardCharsets.UTF_8));

        ReflectionTestUtils.setField(
                service,
                "uploadDir",
                System.getProperty("java.io.tmpdir")
        );
        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        AdvertisementResponse response =
                service.uploadImage(10L, file);

        assertNotNull(response);

        verify(advertisementRepository).save(advertisement);
    }
    @Test
    void uploadImage_ShouldThrow_WhenExtensionInvalid() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        "virus.exe",
                        "application/octet-stream",
                        "abc".getBytes());

        ReflectionTestUtils.setField(
                service,
                "uploadDir",
                System.getProperty("java.io.tmpdir")
        );
        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                () -> service.uploadImage(10L, file));
    }
    @Test
    void deleteImage_ShouldDeleteSuccessfully() {

        AdvertisementImage image =
                new AdvertisementImage("/uploads/test.jpg",
                        advertisement);

        image.setId(1L);

        ReflectionTestUtils.setField(
                service,
                "uploadDir",
                System.getProperty("java.io.tmpdir")
        );
        when(advertisementImageRepository.findById(1L))
                .thenReturn(Optional.of(image));

        service.deleteImage(1L);

        verify(advertisementImageRepository)
                .delete(image);
    }
    @Test
    void deleteImage_ShouldThrow_WhenImageNotFound() {

        when(advertisementImageRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteImage(1L));
    }
}