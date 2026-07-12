package divar.service;

import divar.dto.response.CityResponse;

import java.util.List;

public interface CityService {

    List<CityResponse> getAll();

}