package br.com.bb.disec.micro.sso;

import br.com.bb.sso.bean.User;
import java.util.Map;



/**
 * Classe para criação de objetos Usuario e User a
 * partir de um mapa com as informações do usuário 
 * recuperadas do servidor de autenticação SSO.
 * @author Juno Roesler - F6036477
 */
public class MappedUser {
	
	private final Map<String,String> map;
	
	
	/**
	 * Construtor padrão, recebe um mapa contendo
	 * as informações do usuário, recuperadas do
	 * servidor de autenticação SSO.
	 * @param map Mapa com as informações do usuário.
	 */
	public MappedUser(Map<String,String> map) {
		if(map == null || map.isEmpty()) {
			throw new IllegalArgumentException(
					"Mapa de informações inválido: "
					+ map == null ? "null" : "empty map"
			);
		}
		this.map = map;
	}
	
	
	/**
	 * Recupera uma informações armazenada no mapa sob
	 * o nome do SSOField informado.
	 * @param f Chave para recuperar informação do mapa.
	 * @return Valor armazenado sob a chave informada.
	 */
	private String get(SSOField f) {
		return map.get(f.getName());
	}
	
	
	/**
	 * Recupera um número (int) armazenado no mapa sob
	 * o nome do SSOField informado.
	 * @param f Chave para recuperar informação do mapa.
	 * @return Valor armazenado sob a chave informada.
	 */
	private int getInt(SSOField f) {
		try {
			return Integer.parseInt(map.get(f.getName()));
		} catch(Exception e) {
			return -1;
		}
	}
	
	
	/**
	 * Recupera um número (long) armazenado no mapa sob
	 * o nome do SSOField informado.
	 * @param f Chave para recuperar informação do mapa.
	 * @return Valor armazenado sob a chave informada.
	 */
	private long getLong(SSOField f) {
		try {
			return Integer.parseInt(map.get(f.getName()));
		} catch(Exception e) {
			return -1;
		}
	}
	
	
	/**
	 * Cria um novo objeto User com as informações do mapa.
	 * @return Novo objeto User.
	 */
	public User createUser() {
		return new User()
				.setAcessos(get(SSOField.ACESSOS))
				.setCelular(get(SSOField.MOBILE))
				.setChave(get(SSOField.CHAVE))
				.setChaveNome(get(SSOField.CHAVE_NOME))
				.setCodigoComissao(getInt(SSOField.COD_COMISSAO))
				.setCodigoIOR(getInt(SSOField.COD_IOR))
				.setCodigoInstituicao(getInt(SSOField.COD_INST))
				.setCodigoPilar(getInt(SSOField.COD_PILAR))
				.setComissao(get(SSOField.NOME_COMISSAO))
				.setCpf(get(SSOField.CPF))
				.setEmail(get(SSOField.EMAIL))
				.setGrupamento(getInt(SSOField.GRUPAMENTO))
				.setMci(getLong(SSOField.MCI))
				.setNome(get(SSOField.NOME))
				.setNomeGuerra(get(SSOField.NOME_GUERRA))
				.setPrefixoDepe(getInt(SSOField.PRF_DEPE))
				.setPrefixoDiretoria(getInt(SSOField.PRF_DIRETORIA))
				.setPrefixoSuper(getInt(SSOField.PRF_SUPER))
				.setRefOrgc(get(SSOField.REF_ORGC))
				.setRespFuncional(getInt(SSOField.RESP_FUN))
				.setTelefone(get(SSOField.PHONE))
				.setTelefoneCasa(get(SSOField.HOME_PHONE))
				.setTipoDepe(getInt(SSOField.TIPO_DEPE))
				.setTokenID(get(SSOField.TOKENID))
				.setUF(get(SSOField.UF))
				.setUorDepe(getInt(SSOField.UOR_DEPE))
				.setUorEquipe(getInt(SSOField.UOR_EQP));
	}
	
}
