package codigoalvo.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import codigoalvo.entity.Categoria;
import codigoalvo.rest.util.ResponseBuilderHelper;
import codigoalvo.service.CategoriaService;
import codigoalvo.service.CategoriaServiceImpl;
import codigoalvo.util.Message;


@Path("/categorias")
public class CategoriaREST {

	private static final String UTF8 = ";charset=UTF-8";
	private static final Logger LOG = Logger.getLogger(CategoriaREST.class);

	CategoriaService service = new CategoriaServiceImpl();

	public CategoriaREST() {
		LOG.debug("####################  construct  ####################");
	}


	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + UTF8)
	public Response find(@Context HttpHeaders headers, @PathParam("id") int id) {
		String token = ResponseBuilderHelper.getTokenFromHttpHeaders(headers);
		ResponseBuilder resposta = ResponseBuilderHelper.checkAuthentication(token);
		if (resposta == null) {
			Categoria entidade = this.service.buscar(id);
			resposta = Response.ok().entity(entidade);
		}
		return resposta.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + UTF8)
	public Response list(@Context HttpHeaders headers) {
		String token = ResponseBuilderHelper.getTokenFromHttpHeaders(headers);
		ResponseBuilder resposta = ResponseBuilderHelper.checkAuthentication(token);
		if (resposta == null) {
			Categoria[] entidades = this.service.listar().toArray(new Categoria[0]);
			resposta = Response.ok().entity(entidades);
		}
		return resposta.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + UTF8)
	@Consumes(MediaType.APPLICATION_JSON + UTF8)
	public Response insert(@Context HttpHeaders headers, Categoria categoria) {
		String token = ResponseBuilderHelper.getTokenFromHttpHeaders(headers);
		ResponseBuilder resposta = ResponseBuilderHelper.checkAuthentication(token);
		if (resposta == null) {
			try {
				Categoria entidade = this.service.gravar(categoria);
				resposta = Response.ok().entity(entidade);
			} catch (Exception e) {
				e.printStackTrace();
				resposta = Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Ocorreu um erro ao salvar!"));
			}
		}
		return resposta.build();
	}

	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON + UTF8)
	@Consumes(MediaType.APPLICATION_JSON + UTF8)
	public Response update(@Context HttpHeaders headers, Categoria categoria, @PathParam("id") int id) {
		String token = ResponseBuilderHelper.getTokenFromHttpHeaders(headers);
		ResponseBuilder resposta = ResponseBuilderHelper.checkAuthentication(token);
		if (resposta == null) {
			try {
				Categoria entidade = this.service.gravar(categoria);
				resposta = Response.ok().entity(entidade);
			} catch (Exception e) {
				e.printStackTrace();
				resposta = Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Ocorreu um erro ao salvar!"));
			}
		}
		return resposta.build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + UTF8)
	public Response remove(@Context HttpHeaders headers, @PathParam("id") int id) {
		String token = ResponseBuilderHelper.getTokenFromHttpHeaders(headers);
		ResponseBuilder resposta = ResponseBuilderHelper.checkAuthentication(token);
		if (resposta == null) {
			try {
				this.service.removerPorId(id);
				resposta = Response.ok().entity(new Message("Categoria removida com sucesso!!!"));
			} catch (SQLException e) {
				e.printStackTrace();
				resposta = Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Ocorreu um erro ao remover!"));
			}
		}
		return resposta.build();
	}
}
