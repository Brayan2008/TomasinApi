package com.tomasin.api.service.ia;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class MockIAProcesador implements IIAProcesador {

    @Override
    public List<DeteccionDanioDTO> procesar(byte[] imagen) {
        return Collections.emptyList();
    }
}
