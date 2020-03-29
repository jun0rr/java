package br.com.bb.sso.bean;

import java.io.Serializable;
import java.util.Objects;



/**
 * Bean para armazenar as informações do usuário autenticado.
 * @author Juno Roesler - F6036477
 */
public class User implements Serializable {
	
	private String nome;
	
	private String acessos;
	
	private int prefixoDepe;
	
	private int codigoPilar;
	
	private String telefone;
	
	private String cpf;
	
	private int respFuncional;
	
	private int tipoDepe;
	
	private int prefixoSuper;
	
	private int grupamento;
	
	private int uorEquipe;
	
	private int codigoIOR;
	
	private String celular;
	
	private String nomeGuerra;
	
	private String refOrgc;
	
	private String UF;
	
	private String chaveNome;
	
	private int uorDepe;
	
	private String telefoneCasa;
	
	private int codigoComissao;
	
	private String chave;
	
	private int codigoInstituicao;
	
	private long mci;
	
	private String email;
	
	private int prefixoDiretoria;
	
	private String comissao;
	
	private String tokenID;
	
	
	/**
	 * Construtor padrão sem argumentos.
	 */
	public User() {}


	/**
	 * Construtor que recebe todos as informações do usuário.
	 * @param nome String
	 * @param acessos String
	 * @param prefixoDepe int
	 * @param codigoPilar int
	 * @param telefone String
	 * @param cpf String
	 * @param respFuncional int
	 * @param tipoDepe int
	 * @param prefixoSuper int 
	 * @param grupamento int
	 * @param uorEquipe int
	 * @param codigoIOR int
	 * @param celular String
	 * @param nomeGuerra String
	 * @param refOrgc String
	 * @param UF String
	 * @param chaveNome String
	 * @param uorDepe int
	 * @param telefoneCasa String
	 * @param codigoComissao int
	 * @param chave String
	 * @param codigoInstituicao int
	 * @param mci long
	 * @param email String
	 * @param prefixoDiretoria int
	 * @param comissao String
	 * @param tokenID String
	 */
	public User(
			String nome, 
			String acessos, 
			int prefixoDepe, 
			int codigoPilar, 
			String telefone, 
			String cpf, 
			int respFuncional, 
			int tipoDepe, 
			int prefixoSuper, 
			int grupamento, 
			int uorEquipe, 
			int codigoIOR, 
			String celular, 
			String nomeGuerra, 
			String refOrgc, 
			String UF, 
			String chaveNome, 
			int uorDepe, 
			String telefoneCasa, 
			int codigoComissao, 
			String chave, 
			int codigoInstituicao, 
			long mci, 
			String email, 
			int prefixoDiretoria, 
			String comissao, 
			String tokenID
	) {
		this.nome = nome;
		this.acessos = acessos;
		this.prefixoDepe = prefixoDepe;
		this.codigoPilar = codigoPilar;
		this.telefone = telefone;
		this.cpf = cpf;
		this.respFuncional = respFuncional;
		this.tipoDepe = tipoDepe;
		this.prefixoSuper = prefixoSuper;
		this.grupamento = grupamento;
		this.uorEquipe = uorEquipe;
		this.codigoIOR = codigoIOR;
		this.celular = celular;
		this.nomeGuerra = nomeGuerra;
		this.refOrgc = refOrgc;
		this.UF = UF;
		this.chaveNome = chaveNome;
		this.uorDepe = uorDepe;
		this.telefoneCasa = telefoneCasa;
		this.codigoComissao = codigoComissao;
		this.chave = chave;
		this.codigoInstituicao = codigoInstituicao;
		this.mci = mci;
		this.email = email;
		this.prefixoDiretoria = prefixoDiretoria;
		this.comissao = comissao;
		this.tokenID = tokenID;
	}
  
  
  @Override
  public User clone() {
    return new User()
        .setAcessos(acessos)
        .setCelular(celular)
        .setChave(chave)
        .setChaveNome(chaveNome)
        .setCodigoComissao(codigoComissao)
        .setCodigoIOR(codigoIOR)
        .setCodigoInstituicao(codigoInstituicao)
        .setCodigoPilar(codigoPilar)
        .setComissao(comissao)
        .setCpf(cpf)
        .setEmail(email)
        .setGrupamento(grupamento)
        .setMci(mci)
        .setNome(nome)
        .setNomeGuerra(nomeGuerra)
        .setPrefixoDepe(prefixoDepe)
        .setPrefixoDepe(prefixoDepe)
        .setPrefixoDiretoria(prefixoDiretoria)
        .setPrefixoSuper(prefixoSuper)
        .setRefOrgc(refOrgc)
        .setRespFuncional(respFuncional)
        .setTelefone(telefone)
        .setTelefoneCasa(telefoneCasa)
        .setTipoDepe(tipoDepe)
        .setTokenID(tokenID)
        .setUF(UF)
        .setUorDepe(uorDepe)
        .setUorEquipe(uorEquipe);
  }
	

	/**
	 * Retorna o nome do usuário.
	 * @return String
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * Retorna os acessos do usuário.
	 * @return String
	 */
	public String getAcessos() {
		return acessos;
	}


	/**
	 * Retorna o prefixo da dependência do usuário.
	 * @return int
	 */
	public int getPrefixoDepe() {
		return prefixoDepe;
	}


	/**
	 * Retorna o código pilar do usuário.
	 * @return int
	 */
	public int getCodigoPilar() {
		return codigoPilar;
	}


	/**
	 * Retorna o telefone do usuário.
	 * @return String
	 */
	public String getTelefone() {
		return telefone;
	}


	/**
	 * Retorna o CPF do usuário.
	 * @return String
	 */
	public String getCpf() {
		return cpf;
	}


	/**
	 * Retorna a responsabilidade funcional do usuário.
	 * @return int
	 */
	public int getRespFuncional() {
		return respFuncional;
	}


	/**
	 * Retorna o tipo de dependência do usuário.
	 * @return String
	 */
	public int getTipoDepe() {
		return tipoDepe;
	}


	/**
	 * Retorna o prefixo da super do usuário.
	 * @return int
	 */
	public int getPrefixoSuper() {
		return prefixoSuper;
	}


	/**
	 * Retorna o código do grupamento do usuário.
	 * @return int
	 */
	public int getGrupamento() {
		return grupamento;
	}


	/**
	 * Retorna a UOR de equipe do usuário.
	 * @return UOR de equipe.
	 */
	public int getUorEquipe() {
		return uorEquipe;
	}


	/**
	 * Retorna o código IOR do usuário.
	 * @return int
	 */
	public int getCodigoIOR() {
		return codigoIOR;
	}


	/**
	 * Retorna o celular do usuário.
	 * @return String.
	 */
	public String getCelular() {
		return celular;
	}


	/**
	 * Retorna o nome de guerra do usuário.
	 * @return String
	 */
	public String getNomeGuerra() {
		return nomeGuerra;
	}


	/**
	 * Retorna a referência organizacional do usuário.
	 * @return String
	 */
	public String getRefOrgc() {
		return refOrgc;
	}


	/**
	 * Retorna a UF do usuário.
	 * @return String
	 */
	public String getUF() {
		return UF;
	}


	/**
	 * Retorna a chave + nome do usuário.
	 * @return String
	 */
	public String getChaveNome() {
		return chaveNome;
	}


	/**
	 * Retorna a UOR da dependência do usuário.
	 * @return int
	 */
	public int getUorDepe() {
		return uorDepe;
	}


	/**
	 * Retorna o telefone de casa do usuário.
	 * @return String
	 */
	public String getTelefoneCasa() {
		return telefoneCasa;
	}


	/**
	 * Retorna o código da comissão do usuário.
	 * @return int
	 */
	public int getCodigoComissao() {
		return codigoComissao;
	}


	/**
	 * Retorna a chave do usuário.
	 * @return String
	 */
	public String getChave() {
		return chave;
	}


	/**
	 * Retorna o código da instituição do usuário.
	 * @return int
	 */
	public int getCodigoInstituicao() {
		return codigoInstituicao;
	}


	/**
	 * Retorna o código MCI do usuário.
	 * @return long
	 */
	public long getMci() {
		return mci;
	}


	/**
	 * Retorna o endereço de e-mail do usuário.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Retorna o prefixo da diretoria do usuário.
	 * @return int
	 */
	public int getPrefixoDiretoria() {
		return prefixoDiretoria;
	}


	/**
	 * Retorna o nome da comissão do usuário.
	 * @return String
	 */
	public String getComissao() {
		return comissao;
	}


	/**
	 * Retorna o token de autenticação SSO.
	 * @return String
	 */
	public String getTokenID() {
		return tokenID;
	}


	/**
	 * Define o nome do usuário.
	 * @param nome String
	 * @return Esta instãncia modificada de User.
	 */
	public User setNome(String nome) {
		this.nome = nome;
		return this;
	}


	/**
	 * Define os acessos do usuário.
	 * @param acessos String
	 * @return Esta instãncia modificada de User.
	 */
	public User setAcessos(String acessos) {
		this.acessos = acessos;
		return this;
	}


	/**
	 * Define o prefixo da dependência do usuário.
	 * @param prefixoDepe int
	 * @return Esta instãncia modificada de User.
	 */
	public User setPrefixoDepe(int prefixoDepe) {
		this.prefixoDepe = prefixoDepe;
		return this;
	}


	/**
	 * Define o código pilar do usuário.
	 * @param codigoPilar int
	 * @return Esta instãncia modificada de User.
	 */
	public User setCodigoPilar(int codigoPilar) {
		this.codigoPilar = codigoPilar;
		return this;
	}


	/**
	 * Define o telefone do usuário.
	 * @param telefone String
	 * @return Esta instãncia modificada de User.
	 */
	public User setTelefone(String telefone) {
		this.telefone = telefone;
		return this;
	}


	/**
	 * Define o CPF do usuário.
	 * @param cpf String
	 * @return Esta instãncia modificada de User.
	 */
	public User setCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}


	/**
	 * Define o código de responsabilidade funcional do usuário.
	 * @param respFuncional int
	 * @return Esta instãncia modificada de User.
	 */
	public User setRespFuncional(int respFuncional) {
		this.respFuncional = respFuncional;
		return this;
	}


	/**
	 * Define o tipo de dependência do usuário.
	 * @param tipoDepe int
	 * @return Esta instãncia modificada de User.
	 */
	public User setTipoDepe(int tipoDepe) {
		this.tipoDepe = tipoDepe;
		return this;
	}


	/**
	 * Define o prefixo da super do usuário.
	 * @param prefixoSuper int
	 * @return Esta instãncia modificada de User.
	 */
	public User setPrefixoSuper(int prefixoSuper) {
		this.prefixoSuper = prefixoSuper;
		return this;
	}


	/**
	 * Define o código de grupamento do usuário.
	 * @param grupamento int
	 * @return Esta instãncia modificada de User.
	 */
	public User setGrupamento(int grupamento) {
		this.grupamento = grupamento;
		return this;
	}


	/**
	 * Define o UOR de equipe do usuário.
	 * @param uorEquipe int
	 * @return Esta instãncia modificada de User.
	 */
	public User setUorEquipe(int uorEquipe) {
		this.uorEquipe = uorEquipe;
		return this;
	}


	/**
	 * Define o código IOR do usuário.
	 * @param codigoIOR int
	 * @return Esta instãncia modificada de User.
	 */
	public User setCodigoIOR(int codigoIOR) {
		this.codigoIOR = codigoIOR;
		return this;
	}


	/**
	 * Define o número de celular do usuário.
	 * @param celular String
	 * @return Esta instãncia modificada de User.
	 */
	public User setCelular(String celular) {
		this.celular = celular;
		return this;
	}


	/**
	 * Define o nome de guerra do usuário.
	 * @param nomeGuerra String
	 * @return Esta instãncia modificada de User.
	 */
	public User setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
		return this;
	}


	/**
	 * Define a referência organizacional do usuário.
	 * @param refOrgc String
	 * @return Esta instãncia modificada de User.
	 */
	public User setRefOrgc(String refOrgc) {
		this.refOrgc = refOrgc;
		return this;
	}


	/**
	 * Define a UF do usuário.
	 * @param UF String
	 * @return Esta instãncia modificada de User.
	 */
	public User setUF(String UF) {
		this.UF = UF;
		return this;
	}


	/**
	 * Define chave + nome do usuário.
	 * @param chaveNome String
	 * @return Esta instãncia modificada de User.
	 */
	public User setChaveNome(String chaveNome) {
		this.chaveNome = chaveNome;
		return this;
	}


	/**
	 * Define a UOR da dependência do usuário.
	 * @param uorDepe int
	 * @return Esta instãncia modificada de User.
	 */
	public User setUorDepe(int uorDepe) {
		this.uorDepe = uorDepe;
		return this;
	}


	/**
	 * Define o telefone de casa do usuário.
	 * @param telefoneCasa String
	 * @return Esta instãncia modificada de User.
	 */
	public User setTelefoneCasa(String telefoneCasa) {
		this.telefoneCasa = telefoneCasa;
		return this;
	}


	/**
	 * Define o código de comissão do usuário.
	 * @param codigoComissao String
	 * @return Esta instãncia modificada de User.
	 */
	public User setCodigoComissao(int codigoComissao) {
		this.codigoComissao = codigoComissao;
		return this;
	}


	/**
	 * Define a chave do usuário.
	 * @param chave String
	 * @return Esta instãncia modificada de User.
	 */
	public User setChave(String chave) {
		this.chave = chave;
		return this;
	}


	/**
	 * Define o código da instituição do usuário.
	 * @param codigoInstituicao String
	 * @return Esta instãncia modificada de User.
	 */
	public User setCodigoInstituicao(int codigoInstituicao) {
		this.codigoInstituicao = codigoInstituicao;
		return this;
	}


	/**
	 * Define o mci do usuário.
	 * @param mci long
	 * @return Esta instãncia modificada de User.
	 */
	public User setMci(long mci) {
		this.mci = mci;
		return this;
	}


	/**
	 * Define o endereço de e-mail do usuário.
	 * @param email String
	 * @return Esta instãncia modificada de User.
	 */
	public User setEmail(String email) {
		this.email = email;
		return this;
	}


	/**
	 * Define o prefixo da diretoria do usuário.
	 * @param prefixoDiretoria String
	 * @return Esta instãncia modificada de User.
	 */
	public User setPrefixoDiretoria(int prefixoDiretoria) {
		this.prefixoDiretoria = prefixoDiretoria;
		return this;
	}


	/**
	 * Define a comissão do usuário.
	 * @param comissao String
	 * @return Esta instãncia modificada de User.
	 */
	public User setComissao(String comissao) {
		this.comissao = comissao;
		return this;
	}


	/**
	 * Define o token SSO de autenticação do usuário.
	 * @param tokenID String
	 * @return Esta instãncia modificada de User.
	 */
	public User setTokenID(String tokenID) {
		this.tokenID = tokenID;
		return this;
	}
	
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 19 * hash + Objects.hashCode(this.nome);
		hash = 19 * hash + Objects.hashCode(this.cpf);
		hash = 19 * hash + this.uorEquipe;
		hash = 19 * hash + this.codigoComissao;
		hash = 19 * hash + Objects.hashCode(this.chave);
		return hash;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if(this.uorEquipe != other.uorEquipe) {
			return false;
		}
		if(this.codigoComissao != other.codigoComissao) {
			return false;
		}
		if(!Objects.equals(this.nome, other.nome)) {
			return false;
		}
		if(!Objects.equals(this.cpf, other.cpf)) {
			return false;
		}
		if(!Objects.equals(this.chave, other.chave)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "User{chave=" + chave 
				+ ", nome=" + nome
				+ ", prefixoDepe=" + prefixoDepe 
				+ ", uorEquipe=" + uorEquipe + '}';
	}
	
}
