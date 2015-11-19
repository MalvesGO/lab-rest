package codigoalvo.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.repository.UsuarioDaoJpa;
import codigoalvo.util.EntityManagerUtil;
import codigoalvo.util.SegurancaUtil;
import codigoalvo.util.SegurancaUtilMd5;

public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioDao dao;
	private SegurancaUtil segurancaUtil;

	public UsuarioServiceImpl() {
		Logger.getLogger(UsuarioServiceImpl.class).debug("####################  construct  ####################");
		this.dao = new UsuarioDaoJpa(EntityManagerUtil.getEntityManager());
		this.segurancaUtil = new SegurancaUtilMd5();
	}

	@Override
	public Usuario gravar(Usuario usuario) throws SQLException {

		String senhaText = usuario.getSenha();
		if (!this.segurancaUtil.criptografado(usuario.getSenha())) {
			usuario.setSenha(this.segurancaUtil.criptografar(senhaText));
		}
		try {
			this.dao.beginTransaction();
			if (usuario.getId() == null) {
				this.dao.criar(usuario);
			} else {
				this.dao.atualizar(usuario);
			}
			this.dao.commit();
			return usuario;
		} catch (Throwable exc) {
			if (usuario.getId() == null) {
				usuario.setSenha(senhaText);
			}
			this.dao.rollback();
			throw new SQLException(exc);
		}

	}

	@Override
	public void remover(Usuario usuario) throws SQLException {
		try {
			this.dao.remover(usuario.getId());
		} catch (Throwable exc) {
			throw new SQLException(exc);
		}

	}

	@Override
	public void removerPorId(Integer id) throws SQLException {
		try {
			this.dao.remover(id);
		} catch (Throwable exc) {
			throw new SQLException(exc);
		}
	}

	@Override
	public Usuario buscar(Integer id) {
		return this.dao.buscar(id);
	}

	@Override
	public List<Usuario> listar() {
		return this.dao.listar();
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		Usuario usuario = null;
		try {
			usuario = this.dao.buscarPorLogin(login);
		} catch (NoResultException nre) {
			Logger.getLogger(UsuarioServiceImpl.class).debug("Usuario não encontrado (login): " + login);
		}
		return usuario;
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		Usuario usuario = null;
		try {
			usuario = this.dao.buscarPorEmail(email);
		} catch (NoResultException nre) {
			Logger.getLogger(UsuarioServiceImpl.class).debug("Usuario não encontrado (email): " + email);
		}
		return usuario;
	}

}
