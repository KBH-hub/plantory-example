package com.zero.sharingcommunityui.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.zero.sharingcommunityui.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    // /api/plants/12938
    @GetMapping(value = "/{cntntsNo}",  produces = MediaType.APPLICATION_JSON_VALUE) //JSON 강제
    public JsonNode getPlant(@PathVariable String cntntsNo) throws Exception {
        JsonNode res = plantService.getPlantXmlAsJson(cntntsNo);
        return res;
    }
}
