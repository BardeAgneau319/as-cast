package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.MockData;
import fr.stack.grosmanginvo.ascastdemo.models.Node;
import fr.stack.grosmanginvo.ascastdemo.models.Source;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class HttpService {

    private final RestTemplate restTemplate;
    private final Logger logger = Logger.getLogger(HttpService.class.getName());

    public void postAsCastAdd(Source source, Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_ADD;
        try {
            this.restTemplate.postForObject(url, source, void.class);
        } catch (RestClientException e) {
            logger.log(Level.SEVERE, String.format("Failed to execute request POST %s", url), e);
        }
    }

    public void postAsCastDel(Source source, Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_DELL;
        try {
            this.restTemplate.postForObject(url, source, void.class);
        } catch (RestClientException e) {
            logger.log(Level.SEVERE, String.format("Failed to execute request POST %s", url), e);
        }
    }


    public MockData getAsCastData(Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_DATA;
        return this.restTemplate.getForObject(url, MockData.class);
    }

    public Source getAsCastSource(Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_SOURCE;
        return this.restTemplate.getForObject(url, Source.class);
    }


    public void postAdminNeighbor(Node neighbor, Node target) {
        String url = target.getAddress() + Routes.ADMIN_ROOT + Routes.ADMIN_NEIGHBORS;
        this.restTemplate.postForObject(url, neighbor, void.class);
    }

    public void deleteAdminNeighbor(int neighborPort, Node target) {
        String url = String.format("%s%s%s/%d", target.getAddress(), Routes.ADMIN_ROOT, Routes.ADMIN_NEIGHBORS, neighborPort);
        this.restTemplate.delete(url, void.class);
    }
}
