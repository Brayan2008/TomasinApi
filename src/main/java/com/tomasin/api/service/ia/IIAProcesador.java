package com.tomasin.api.service.ia;

import java.util.List;

public interface IIAProcesador {

    List<DeteccionDanioDTO> procesar(byte[] imagen, String contentType);
}
