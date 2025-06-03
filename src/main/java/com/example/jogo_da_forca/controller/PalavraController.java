package com.example.jogo_da_forca.controller;

import com.example.jogo_da_forca.dto.Palavra;
import com.example.jogo_da_forca.dto.ResultadoTentativa;
import com.example.jogo_da_forca.dto.TentativaLetra;
import com.example.jogo_da_forca.service.PalavraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
        @RequestMapping("/forca")
public class PalavraController {

    @Autowired
    private PalavraService palavraService;

    @GetMapping("/palavra")
    public Palavra gerarPalavraAleatoria(){
        return palavraService.getPalavraAleatoria();
    }

    @PutMapping("/tentar")
    public ResultadoTentativa tentarLetra(@RequestBody TentativaLetra tentativa) {
        return palavraService.tentarLetra(tentativa.getLetra());
    }

}
