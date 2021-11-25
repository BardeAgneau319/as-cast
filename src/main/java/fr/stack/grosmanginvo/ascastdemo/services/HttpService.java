package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.MockData;
import fr.stack.grosmanginvo.ascastdemo.models.Node;
import fr.stack.grosmanginvo.ascastdemo.models.Source;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HttpService {

    private final RestTemplate restTemplate;

    public void postAsCastAdd(Source source, Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_ADD;
        this.restTemplate.postForObject(url, source, void.class);
    }

    public void postAsCastDel(Source source, Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_DELL;
        this.restTemplate.postForObject(url, source, void.class);
    }


    public MockData getAsCastData(Node target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_DATA;
        return this.restTemplate.getForObject(url, MockData.class);
    }
}
