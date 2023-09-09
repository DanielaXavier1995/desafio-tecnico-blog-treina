package com.blog.api.treina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.treina.model.Comentario;
import com.blog.api.treina.repository.ComentarioRepository;

@RestController
@RequestMapping(value = "/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @PostMapping
    public ResponseEntity<Comentario> cadastrarComentario(@RequestBody Comentario comentario) {

        Comentario comentarioCadastrado = comentarioRepository.save(comentario);

        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listarComentario() {

        List<Comentario> listaDeComentario = comentarioRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaDeComentario);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> listarComentarioPeloId(@PathVariable("id") Long id) {
        Optional<Comentario> comentarioPeloId = comentarioRepository.findById(id);

        if (comentarioPeloId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(comentarioPeloId.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizarComentario(@PathVariable("id") Long id,
            @RequestBody Comentario comentario) {
        Optional<Comentario> comentarioExistente = comentarioRepository.findById(id);

        if (comentarioExistente.isPresent()) {
            comentarioExistente.get().setTexto(comentario.getTexto());

            return ResponseEntity.status(HttpStatus.OK).body(comentarioRepository.save(comentarioExistente.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarComentarioPeloId(@PathVariable Long id) {
        Optional<Comentario> comentarioExistente = comentarioRepository.findById(id);

        if (comentarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        comentarioRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Comentario deletada com sucesso!");
    }

}
