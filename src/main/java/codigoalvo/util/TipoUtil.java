package codigoalvo.util;

import java.util.ArrayList;
import java.util.List;

import codigoalvo.entity.PagamentoTipo;
import codigoalvo.entity.UsuarioTipo;

public class TipoUtil {

	private static final String TIPOS_USUARIO_JSON = buildTiposUsuarioJson();
	private static final String TIPOS_PAGAMENTO_JSON = buildTiposPagamentoJson();

	public static String getTiposUsuarioJson() {
		return TIPOS_USUARIO_JSON;
	}

	public static String getTiposPagamentoJson() {
		return TIPOS_PAGAMENTO_JSON;
	}

	private static String buildTiposUsuarioJson() {
		List<String> tipos = new ArrayList<String>();
		for (UsuarioTipo usuarioTipo : UsuarioTipo.values()) {
			tipos.add(usuarioTipo.name());
		}
		return JsonUtil.toJson(tipos.toArray(new String[0]));
	}

	private static String buildTiposPagamentoJson() {
		List<String> tipos = new ArrayList<String>();
		for (PagamentoTipo pagamentoTipo : PagamentoTipo.values()) {
			tipos.add(pagamentoTipo.name());
		}
		return JsonUtil.toJson(tipos.toArray(new String[0]));
	}

}