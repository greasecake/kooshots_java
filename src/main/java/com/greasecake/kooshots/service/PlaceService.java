package com.greasecake.kooshots.service;

import com.greasecake.kooshots.docs.ResponseRow;
import com.greasecake.kooshots.docs.ResponseRows;
import com.greasecake.kooshots.entity.MapLink;
import com.greasecake.kooshots.entity.Place;
import com.greasecake.kooshots.entity.Tag;
import com.greasecake.kooshots.model.PlacesRequest;
import com.greasecake.kooshots.repository.MapLinksRepository;
import com.greasecake.kooshots.repository.PlaceRepository;
import com.greasecake.kooshots.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MapLinksRepository mapLinksRepository;

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public byte[] getImage(String url) {
        String image = WebClient.create(url).get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return Base64.getDecoder().decode(image);
    }

    private List<Double> formatCoordinates(String coordinates) {
        return Arrays.stream(coordinates.split(","))
                .map(str -> new BigDecimal(str.strip()).setScale(6, RoundingMode.HALF_UP).doubleValue())
                .collect(Collectors.toList());
    }

    public List<Place> saveAll(ResponseRows rows) {
        placeRepository.deleteAll();
        tagRepository.deleteAll();
        mapLinksRepository.deleteAll();
        List<Place> places = new ArrayList<>();
        for (ResponseRow row : rows.getValues()) {
            try {
                Set<Tag> tags = new HashSet<>();
                Place place = new Place();
                List<String> tagNames = Arrays.stream(row.getTags().split(","))
                        .map(String::strip).filter(str -> !str.matches("\\s*"))
                        .collect(Collectors.toList());
                for (String tagName : tagNames) {
                    tags.add(tagRepository.findByName(tagName) == null ? new Tag(tagName) : tagRepository.findByName(tagName));
                }
                List<Double> coordinates = formatCoordinates(row.getCoordinates());
                place.setId(row.getId());
                place.setName(row.getName());
                place.setDescription(row.getDescription());
                place.setAddress(row.getAddress());
                place.setLatitude(coordinates.get(0));
                place.setLongitude(coordinates.get(1));
                place.setPhotoUrl(row.getPhoto());
                place.setTags(tags);
                place = placeRepository.save(place);

                MapLink mapLink = new MapLink();
                mapLink.setYandex(row.getYandexLink());
                mapLink.setGoogle(row.getGoogleLink());
                mapLink.setGis(row.getGisLink());
                mapLink.setPlace(place);
                mapLinksRepository.save(mapLink);
                place.setMapLink(mapLink);

                places.add(place);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return places;
    }

    public Page<Place> findByLocation(PlacesRequest request, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 3);
        return placeRepository.getPlacesByCoordinates(request.getLatitude(), request.getLongitude(), Double.valueOf(request.getDistance()), pageable);
    }
}
