package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.dtos.ProductInDTO;
import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

/**
 * Controller PROTEGIDO — rotas /ws/products/**
 * Todas as rotas exigem token JWT válido (filtrado pelo Gateway).
 * Operações de escrita exigem role Admin (X-User-Type = 0).
 */
@RestController
@RequestMapping("/ws/products")
public class WsProductController {

    private final ProductRepository repository;

    public WsProductController(ProductRepository repository) {
        this.repository = repository;
    }

    private ProductEntity convertDTOtoEntity(ProductInDTO dto) {
        var entity = new ProductEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @PostMapping
    public ResponseEntity<ProductEntity> postProduct(
            @RequestBody ProductInDTO dto,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Integer type) throws AuthenticationException {

        UserRoleValidator.requireAdmin(type);

        var product = convertDTOtoEntity(dto);
        product.setStock(10);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Atualiza um produto existente.
     * Permitido: apenas Admin (X-User-Type = 0)
     */
    @PutMapping("/{idProduct}")
    public ResponseEntity<ProductEntity> putProduct(
            @PathVariable Long idProduct,
            @RequestBody ProductInDTO dto,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Integer type) throws AuthenticationException {

        UserRoleValidator.requireAdmin(type);

        // Verifica se o produto existe antes de atualizar
        repository.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        var product = convertDTOtoEntity(dto);
        product.setId(idProduct);
        repository.save(product);
        return ResponseEntity.ok(product);
    }

    /**
     * Remove um produto.
     * Permitido: apenas Admin (X-User-Type = 0)
     */
    @DeleteMapping("/{idProduct}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long idProduct,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Integer type) throws AuthenticationException {

        UserRoleValidator.requireAdmin(type);

        // Verifica se o produto existe antes de excluir
        repository.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        repository.deleteById(idProduct);
        return ResponseEntity.ok("Produto excluído com sucesso.");
    }

    // ----- Handlers de exceção -----

    /**
     * 403 para falhas de autorização (role incorreta).
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * 400 para erros genéricos (produto não encontrado, dados inválidos etc.).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String message = e.getMessage().replace("\r\n", "");
        return ResponseEntity.badRequest().body(message);
    }
}