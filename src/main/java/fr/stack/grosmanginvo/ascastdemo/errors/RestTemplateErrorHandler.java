package fr.stack.grosmanginvo.ascastdemo.errors;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private Logger logger = Logger.getLogger(ResponseErrorHandler.class.getName());

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) {
        try {
            logger.log(Level.SEVERE, String.format("HTTP ERROR %d '%s'", httpResponse.getRawStatusCode(), httpResponse.getStatusText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
