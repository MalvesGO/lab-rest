package codigoalvo.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import codigoalvo.entity.Conta;
import codigoalvo.entity.ContaTipo;
import codigoalvo.repository.ContaDao;
import codigoalvo.repository.ContaDaoJpa;
import codigoalvo.util.EntityManagerUtil;

public class ContaServiceImpl implements ContaService {

	private ContaDao dao;
	private static final Logger LOG = Logger.getLogger(ContaServiceImpl.class);

	public ContaServiceImpl() {
		LOG.trace("####################  construct  ####################");
		this.dao = new ContaDaoJpa(EntityManagerUtil.getEntityManager());
	}

	@Override
	public Conta gravar(Conta conta) throws SQLException {

		try {
			this.dao.beginTransaction();
			if (conta.getTipo() != ContaTipo.CARTAO) {
				conta.setDiaFechamento(null);
				conta.setDiaPagamento(null);
			}
			if (conta.getId() == null) {
				this.dao.criar(conta);
			} else {
				this.dao.atualizar(conta);
			}
			this.dao.commit();
			return conta;
		} catch (Throwable exc) {
			this.dao.rollback();
			LOG.debug("gravar.rollback");
			this.dao.getEntityManager().clear();
			LOG.debug("gravar.dao.em.clear");
			throw new SQLException(exc);
		}

	}

	@Override
	public void remover(Conta conta) throws SQLException {
		try {
			this.dao.beginTransaction();
			this.dao.removerPorId(conta.getId());
			this.dao.commit();
		} catch (Throwable exc) {
			this.dao.rollback();
			throw new SQLException(exc);
		}

	}

	@Override
	public void removerPorId(Integer id) throws SQLException {
		try {
			this.dao.beginTransaction();
			this.dao.removerPorId(id);
			this.dao.commit();
		} catch (Throwable exc) {
			this.dao.rollback();
			throw new SQLException(exc);
		}
	}

	@Override
	public Conta buscar(Integer usuarioId, Integer categoriaId) {
		return this.dao.contaDoUsuario(usuarioId, categoriaId);
	}

	@Override
	public List<Conta> listar(Integer usuarioId, Boolean exibirInativos) {
		return this.dao.contasDoUsuario(usuarioId, exibirInativos);
	}

	@Override
	public Conta buscarPorNome(String nome) {
		Conta conta = null;
		try {
			conta = this.dao.buscarPorNome(nome);
		} catch (NoResultException nre) {
			LOG.debug("Conta não encontrado (nome): " + nome);
		}
		return conta;
	}

}
