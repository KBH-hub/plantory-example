package com.zero.sharingcommunityui.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlantService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();

    @Value("${plant.api.base-url}")
    private String baseUrl;

    @Value("${plant.api.key}")
    private String apiKey;

    public JsonNode getPlantXmlAsJson(String cntntsNo) throws Exception {
        String url = baseUrl
                + "?apiKey=" + apiKey
                + "&cntntsNo=" + cntntsNo;

        // 1) XML 문자열로 받기
        String xml = restTemplate.getForObject(url, String.class);

        // 2) XML → JsonNode (트리 형태) 파싱
        JsonNode root = xmlMapper.readTree(xml);

        return root;
    }
}
