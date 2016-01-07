package codigoalvo.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import codigoalvo.entity.Transacao;
import codigoalvo.entity.TransacaoTipo;
import codigoalvo.repository.PlanejamentoDaoJpa;
import codigoalvo.repository.TransacaoDao;
import codigoalvo.repository.TransacaoDaoJpa;
import codigoalvo.util.DateUtil;
import codigoalvo.util.EntityManagerUtil;

public class TransacaoServiceImpl implements TransacaoService {

	private TransacaoDao dao;
	private static final Logger LOG = Logger.getLogger(TransacaoServiceImpl.class);

	public TransacaoServiceImpl() {
		LOG.debug("####################  construct  ####################");
		this.dao = new TransacaoDaoJpa(EntityManagerUtil.getEntityManager());
	}

	@Override
	public Transacao gravar(Transacao transacao) throws SQLException {

		try {
			this.dao.beginTransaction();
			if (transacao.getId() == null) {
				this.dao.criar(transacao);
			} else {
				this.dao.atualizar(transacao);
			}
			this.dao.commit();
			return transacao;
		} catch (Throwable exc) {
			this.dao.rollback();
			LOG.debug("gravar.rollback");
			this.dao.getEntityManager().clear();
			LOG.debug("gravar.dao.em.clear");
			throw new SQLException(exc);
		}

	}

	@Override
	public void remover(Transacao transacao) throws SQLException {
		try {
			this.dao.beginTransaction();
			this.dao.remover(transacao.getId());
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
			this.dao.remover(id);
			this.dao.commit();
		} catch (Throwable exc) {
			this.dao.rollback();
			throw new SQLException(exc);
		}
	}

	@Override
	public Transacao buscar(Integer usuarioId, Integer transacaoId) {
		return this.dao.transacaoDoUsuario(usuarioId, transacaoId);
	}

	@Override
	public List<Transacao> listarDoPeriodo(Integer usuarioId, Integer mes, Integer ano, boolean considerarDataPagamento, TransacaoTipo tipo) {

		Calendar periodo = DateUtil.primeiroDiaDoMes(mes, ano);
		Date dataInicial = periodo.getTime();
		periodo.add(Calendar.MONTH, 1);
		Date dataFinal = periodo.getTime();
		Logger.getLogger(PlanejamentoDaoJpa.class).debug("dataInicial: "+dataInicial);
		Logger.getLogger(PlanejamentoDaoJpa.class).debug("dataFinal: "+dataFinal);
		List<Transacao> response = this.dao.transacoesDoUsuarioNoPeriodo(usuarioId, dataInicial, dataFinal, considerarDataPagamento, tipo);
		//LOG.debug(response);
		return response;
	}

}
