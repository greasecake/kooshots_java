package com.greasecake.kooshots.controller;

import com.greasecake.kooshots.docs.GoogleDocClient;
import com.greasecake.kooshots.docs.ResponseRows;
import com.greasecake.kooshots.entity.Place;
import com.greasecake.kooshots.model.PlacesRequest;
import com.greasecake.kooshots.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KooshotsController {
    @Autowired
    private PlaceService service;
    @Autowired
    private GoogleDocClient docClient;

    @GetMapping( "/places/download")
    public ResponseRows downloadPlaces() {
        return docClient.getData();
    }

    @GetMapping( "places/reload")
    public List<Place> reloadPlaces() {
        return service.saveAll(downloadPlaces());
    }

    @GetMapping("/places/all")
    public List<Place> findAll() {
        return service.findAll();
    }

    @GetMapping("/places")
    public Page<Place> findByLocation(@RequestBody PlacesRequest request, @PathVariable int pageNum) {
        return service.findByLocation(request, pageNum);
    }

    @GetMapping("/image")
    public byte[] downloadImage(String url) {
        return service.getImage(url);
    }
}
