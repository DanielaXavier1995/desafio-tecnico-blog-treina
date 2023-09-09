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

import com.blog.api.treina.model.Post;
import com.blog.api.treina.repository.PostRepository;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> cadastrarPost(@RequestBody Post post) {

        Post postCadastrado = postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(postCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Post>> listarPost() {

        List<Post> listaDePost = postRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaDePost);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> listarPostPeloId(@PathVariable("id") Long id) {
        Optional<Post> postPeloId = postRepository.findById(id);

        if (postPeloId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(postPeloId.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> atualizarPost(@PathVariable("id") Long id, @RequestBody Post post) {
        Optional<Post> postExistente = postRepository.findById(id);

        if (postExistente.isPresent()) {
            postExistente.get().setTitulo(post.getTitulo());
            postExistente.get().setConteudo(post.getConteudo());

            return ResponseEntity.status(HttpStatus.OK).body(postRepository.save(postExistente.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPostPeloId(@PathVariable Long id) {
        Optional<Post> postExistente = postRepository.findById(id);

        if (postExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        postRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post deletada com sucesso!");
    }

}
