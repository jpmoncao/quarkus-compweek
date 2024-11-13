package br.com.fatec;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Operation(
        summary = "Criar Produto",
        description = "Cria um novo produto"
    )
    @APIResponse(
        responseCode = "201",
        description = "Produto cadastrado com sucesso!"
    )
    @APIResponse(
        responseCode = "400",
        description = "Requisição com dados em inconformidade!"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro inesperado!"
    )
    @Transactional
    @POST
    @Path("/produto")
    public Response criarProduto(@Valid ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
            produtoDTO.getNome(),
            produtoDTO.getDescricao(),
            produtoDTO.getPreco()
        );

        produto.persist();

        return Response.status(Response.Status.CREATED).entity(produto).build();
    }
    
    @Operation(
        summary = "Listar Produtos",
        description = "Lista os produtos do banco de dados"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produtos listados com sucesso!"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro inesperado!"
    )
    @GET
    @Path("/produtos")
    public Response listarProdutos() {
        return Response.status(Response.Status.OK).entity(Produto.listAll()).build();
    }

    @Operation(
        summary = "Busca Produto pelo ID",
        description = "Lista um produto pelo ID"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto listado com sucesso!"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto não encontrado!"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro inesperado!"
    )
    @GET
    @Path("/produto/{id}")
    public Response buscarProduto(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);

        if (produto == null) 
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto!").build();

        return Response.status(Response.Status.OK).entity(produto).build();
    }

    @Operation(
        summary = "Deleta Produto",
        description = "Deleta um produto pelo ID"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto deletado com sucesso!"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto não encontrado!"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro inesperado!"
    )
    @Transactional
    @DELETE
    @Path("/produto/{id}")
    public Response deletarProduto(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);

        if (produto == null) 
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto!").build();

        Produto.deleteById(id);

        return Response.status(Response.Status.OK).entity("Produto "+ id +" foi deletado com sucesso!").build();
    }
    
    @Operation(
        summary = "Atualizar Produto",
        description = "Atualiza todos os dados de um produto"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto atualizado com sucesso!"
    )
    @APIResponse(
        responseCode = "400",
        description = "Requisição com dados em inconformidade!"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto não encontrado!"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro inesperado!"
    )
    @Transactional
    @PUT
    @Path("/produto/{id}")
    public Response atualizarProduto(@Valid @PathParam("id") Long id, ProdutoDTO produtoDTO) {
        Produto produto = Produto.findById(id);

        if (produto == null) 
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto!").build();

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());

        produto.persist();

        return Response.status(Response.Status.OK).entity("Produto "+ id +" foi atualizado com sucesso!").build();
    }
}
