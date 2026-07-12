package divar.service.impl;

import divar.dto.response.CityResponse;
import divar.repository.CityRepository;
import divar.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CityResponse> getAll() {

        return cityRepository.findAll()
                .stream()
                .map(city -> new CityResponse(
                        city.getId(),
                        city.getName()))
                .toList();
    }
}