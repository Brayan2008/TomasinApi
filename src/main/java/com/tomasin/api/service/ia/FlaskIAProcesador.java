package com.tomasin.api.service.ia;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("!dev")
public class FlaskIAProcesador implements IIAProcesador {

    private final RestClient restClient;

    public FlaskIAProcesador(@Value("${app.ia.flask.url}") String flaskUrl) {
        this.restClient = RestClient.create(flaskUrl);
    }

    @Override
    public List<DeteccionDanioDTO> procesar(byte[] imagen) {
        var body = new MultipartBodyBuilder();
        body.part("imagen", new ByteArrayResource(imagen))
                .filename("imagen.jpg")
                .contentType(MediaType.IMAGE_JPEG);

        var respuesta = restClient.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body.build())
                .retrieve()
                .body(FlaskResponse.class);

        if (respuesta == null || respuesta.detecciones() == null) {
            return Collections.emptyList();
        }
        return respuesta.detecciones();
    }

    record FlaskResponse(List<DeteccionDanioDTO> detecciones) {}
}
