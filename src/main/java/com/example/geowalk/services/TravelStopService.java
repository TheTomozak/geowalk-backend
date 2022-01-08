package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.models.dto.requests.TravelStopReqDto;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.repositories.TravelStopRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TravelStopService {

    private final TravelStopRepo travelStopRepository;

    private final String NOT_FOUND_BLOG_POST = "Travel stop with given id not found";

    public TravelStopService(TravelStopRepo travelStopRepository) {
        this.travelStopRepository = travelStopRepository;
    }

    public List<TravelStop> getAllTravelStop(){
        return travelStopRepository.findAll();
    }

    public List<TravelStop> getAllTravelStopByLocation(String country, String city, String street){

        if(city == null && street != null){
            throw new BadRequestException("To find street type city too");
        } else if(city == null) {
            return travelStopRepository.findAllByVisibleIsTrueAndCountry(country);
        } else if(street == null){
            return travelStopRepository.findAllByVisibleIsTrueAndCountryAndCity(country, city);
        } else {
            return travelStopRepository.findAllByVisibleIsTrueAndCountryAndCityAndStreet(country, city, street);
        }
    }

    public List<TravelStop> getOrCreateTravelStopsByLocation(List<TravelStopReqDto> travelStopReqDtoList){

        List<TravelStop> newTS = new ArrayList<>();
        List<TravelStop> tSList = new ArrayList<>();

        travelStopReqDtoList.forEach(e -> {
            String country = e.getCountry();
            String city = e.getCity();
            String street = e.getStreet();

            if(city == null && street != null) {
                throw new BadRequestException("To find street type city too");
            }


            TravelStop tS = travelStopRepository.findByVisibleIsTrueAndCountryAndCityAndStreet(country, city, street);
            if(tS != null){
                tSList.add(tS);
            }
            else{
                tS = createTravelStop(e);
                newTS.add(tS);
            }
        });

        travelStopRepository.saveAll(newTS);
        tSList.addAll(newTS);
        return tSList;
    }

    public TravelStop getTravelStopById(Long travelStopId) {
        return travelStopRepository.findById(travelStopId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BLOG_POST));
    }

    public TravelStop createTravelStop(TravelStopReqDto travelStopReqDto){
        return new TravelStop(
                travelStopReqDto.getName(),
                travelStopReqDto.getLatitude(),
                travelStopReqDto.getLongitude(),
                travelStopReqDto.getCountry(),
                travelStopReqDto.getCity(),
                travelStopReqDto.getStreet()
        );
    }


}