package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.INeighbor;
import fr.stack.grosmanginvo.ascastdemo.models.ISource;
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

    public void postAsCastAdd(ISource source, INeighbor target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_ADD;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("source", source);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<Void> response = this.restTemplate.postForEntity(url, entity, void.class);
    }

    public void postAsCastDel(ISource source, INeighbor target) {
        String url = target.getAddress() + Routes.AS_CAST_ROOT + Routes.AS_CAST_DELL;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("source", source);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<Void> response = this.restTemplate.postForEntity(url, entity, void.class);
    }


}
