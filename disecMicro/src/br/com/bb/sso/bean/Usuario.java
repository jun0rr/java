package br.com.bb.sso.bean;
 
import java.io.Serializable;
import java.util.Objects;

/**
 * A classe <code>Usuario</code> foi criada para mapear e manter os atributos 
 * de identidade do usu�rio recebidos do openAM implementado pelo Banco do Brasil.
 * <p>
 * Foi utilizado o <a href=http://www.ietf.org/rfc/rfc2256.txt>RFC 2256</a> 
 * que trata do <a href=http://en.wikipedia.org/wiki/LDAP>LDAP</a> para 
 * distinguir as abrevia��es. As abreviacoes foram "desabreviadas" e traduzidas
 * para o portugu�s.
 * <p>
 * Para as abrevia��es da Arquitetura de Dados do Banco foi consultado
 * a <a href=ad.intranet.bb.com.br>
 * Consulta a termos por Abreviatura do Gloss�rio</a>
 * 
 * @author Diges/Ditec
 * @author F0411579
 * @author F8369006
 * @version 1.1, 06/09/12
 * 
 */
 
public class Usuario implements Serializable {
	/* Identificador da Sess�o / Valor do cookie BBSSOToken */
	private String tokenId;
 
	/* Acessos separados por v�rgula. 
	 * Ver: getAtributosUsuario() na classe br.com.bb.sso.api.util.SSOUtil para customiza��o */
	private String acessos;
 
	/* Demais Atributos do Usu�rio*/
	private String chave;
	private String chaveCripto; 
	private String codigoCliente; // cd-cli (MCI)
	private String codigoComissao; 
	private String codigoComissaoUsuario; //cd-cmss-usu
	private String codigoDivisao; // cd-dvs
	private String codigoIdentificacaoDigitalUsuario; // cd-idgl-usu
	private String codigoInstituicao; 
	private String codigoInstituicaoOrganizacional; // cd-ior
	private String codigoNucleo; // cd-ncl
	private String codigoPilar;
	private String codigoPrefixoDependencia; // cd-pref-depe
	private String codigoReferenciaOrganizacional; // cd-ref-orgc
	private String codigoTipoDependencia; // cd-tip-dep
	private String codigoTipoIdentificacaoDigital; // cd-tip-idgl
	private String codigoUorDependencia; // cd-uor-dep
	private String diretorioHome; // homedirectory
	private String email; // mail
	private String enderecoResidencial; //homepostaladdress
	private String grupamento;
	private String identificadorUnico; // uid
	private String idNativoIBM; // ibm-nativeid
	private String idSessao;
	private String loginShell; // loginshell
	private String nome; // nomeFuncionario (todas mai�sculas)
	private String nomeCamelCase; // nm-idgl
	private String nomeComum; // cn
	private String nomeDistinto; //DN
	private String nomeGuerra; // (todas mai�sculas)	
	private String numeroCPF; // nr-cpf
	private String numeroIdentificadorDeGrupo; //gidnumber
	private String numeroIdentificadorUnico; // uidnumber
	private String prefixoDependencia; 
	private String prefixoDiretoria; 
	private String prefixoSuperEstadual;
	private String responsabilidadeFuncional; 
	private String senhaCripto; // pwd
	private String siglaUF; // nomeUF
	private String sobrenome; // sn
	private String telefoneCelular; // mobile
	private String telefoneComercial; // telephonenumber
	private String telefoneResidencial; // homephone
	private String textoComissaoUsuario; // tx-cmss-usu
	private String tipoDependencia;
	private String cdUorEqp; // Codigo uor da divis�o do funcion�rio.
 
	public Usuario(){
		// Construtor
	}
 
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getAcessos() {
		return acessos;
	}
	public void setAcessos(String acessos) {
		this.acessos = acessos;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getChaveCripto() {
		return chaveCripto;
	}
	public void setChaveCripto(String chaveCripto) {
		this.chaveCripto = chaveCripto;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	
	public int getIntCodCliente() {
		try {
			return Integer.parseInt(codigoCliente);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getCodigoComissao() {
		return codigoComissao;
	}
	
	public int getIntCodComissao() {
		try {
			return Integer.parseInt(codigoComissao);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoComissao(String codigoComissao) {
		this.codigoComissao = codigoComissao;
	}
	public String getCodigoComissaoUsuario() {
		return codigoComissaoUsuario;
	}
	
	public int getIntCodComissaoUsuario() {
		try {
			return Integer.parseInt(codigoComissaoUsuario);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoComissaoUsuario(String codigoComissaoUsuario) {
		this.codigoComissaoUsuario = codigoComissaoUsuario;
	}
	public String getCodigoDivisao() {
		return codigoDivisao;
	}
	
	public int getIntCodDivisao() {
		try {
			return Integer.parseInt(codigoDivisao);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoDivisao(String codigoDivisao) {
		this.codigoDivisao = codigoDivisao;
	}
	public String getCodigoIdentificacaoDigitalUsuario() {
		return codigoIdentificacaoDigitalUsuario;
	}
	public void setCodigoIdentificacaoDigitalUsuario(
			String codigoIdentificacaoDigitalUsuario) {
		this.codigoIdentificacaoDigitalUsuario = codigoIdentificacaoDigitalUsuario;
	}
	public String getCodigoInstituicao() {
		return codigoInstituicao;
	}
	public void setCodigoInstituicao(String codigoInstituicao) {
		this.codigoInstituicao = codigoInstituicao;
	}
	public String getCodigoInstituicaoOrganizacional() {
		return codigoInstituicaoOrganizacional;
	}
	public void setCodigoInstituicaoOrganizacional(
			String codigoInstituicaoOrganizacional) {
		this.codigoInstituicaoOrganizacional = codigoInstituicaoOrganizacional;
	}
	public String getCodigoNucleo() {
		return codigoNucleo;
	}
	public void setCodigoNucleo(String codigoNucleo) {
		this.codigoNucleo = codigoNucleo;
	}
	public String getCodigoPilar() {
		return codigoPilar;
	}
	public void setCodigoPilar(String codigoPilar) {
		this.codigoPilar = codigoPilar;
	}
	public String getCodigoPrefixoDependencia() {
		return codigoPrefixoDependencia;
	}
	
	public int getIntCodPrefixoDepe() {
		try {
			return Integer.parseInt(codigoPrefixoDependencia);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoPrefixoDependencia(String codigoPrefixoDependencia) {
		this.codigoPrefixoDependencia = codigoPrefixoDependencia;
	}
	public String getCodigoReferenciaOrganizacional() {
		return codigoReferenciaOrganizacional;
	}
	public void setCodigoReferenciaOrganizacional(
			String codigoReferenciaOrganizacional) {
		this.codigoReferenciaOrganizacional = codigoReferenciaOrganizacional;
	}
	public String getCodigoTipoDependencia() {
		return codigoTipoDependencia;
	}
	public void setCodigoTipoDependencia(String codigoTipoDependencia) {
		this.codigoTipoDependencia = codigoTipoDependencia;
	}
	public String getCodigoTipoIdentificacaoDigital() {
		return codigoTipoIdentificacaoDigital;
	}
	public void setCodigoTipoIdentificacaoDigital(
			String codigoTipoIdentificacaoDigital) {
		this.codigoTipoIdentificacaoDigital = codigoTipoIdentificacaoDigital;
	}
	public String getCodigoUorDependencia() {
		return codigoUorDependencia;
	}
	
	public int getIntUORDepe() {
		try {
			return Integer.parseInt(codigoUorDependencia);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setCodigoUorDependencia(String codigoUorDependencia) {
		this.codigoUorDependencia = codigoUorDependencia;
	}
	public String getDiretorioHome() {
		return diretorioHome;
	}
	public void setDiretorioHome(String diretorioHome) {
		this.diretorioHome = diretorioHome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnderecoResidencial() {
		return enderecoResidencial;
	}
	public void setEnderecoResidencial(String enderecoResidencial) {
		this.enderecoResidencial = enderecoResidencial;
	}
	public String getGrupamento() {
		return grupamento;
	}
	public void setGrupamento(String grupamento) {
		this.grupamento = grupamento;
	}
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}
	public String getIdNativoIBM() {
		return idNativoIBM;
	}
	public void setIdNativoIBM(String idNativoIBM) {
		this.idNativoIBM = idNativoIBM;
	}
	public String getIdSessao() {
		return idSessao;
	}
	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}
	public String getLoginShell() {
		return loginShell;
	}
	public void setLoginShell(String loginShell) {
		this.loginShell = loginShell;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeCamelCase() {
		return nomeCamelCase;
	}
	public void setNomeCamelCase(String nomeCamelCase) {
		this.nomeCamelCase = nomeCamelCase;
	}
	public String getNomeComum() {
		return nomeComum;
	}
	public void setNomeComum(String nomeComum) {
		this.nomeComum = nomeComum;
	}
	public String getNomeDistinto() {
		return nomeDistinto;
	}
	public void setNomeDistinto(String nomeDistinto) {
		this.nomeDistinto = nomeDistinto;
	}
	public String getNomeGuerra() {
		return nomeGuerra;
	}
	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}
	public String getSiglaUF() {
		return siglaUF;
	}
	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}
	public String getNumeroCPF() {
		return numeroCPF;
	}
	
	public int getIntCPF() {
		try {
			return Integer.parseInt(numeroCPF);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setNumeroCPF(String numeroCPF) {
		this.numeroCPF = numeroCPF;
	}
	public String getNumeroIdentificadorDeGrupo() {
		return numeroIdentificadorDeGrupo;
	}
	public void setNumeroIdentificadorDeGrupo(String numeroIdentificadorDeGrupo) {
		this.numeroIdentificadorDeGrupo = numeroIdentificadorDeGrupo;
	}
	public String getNumeroIdentificadorUnico() {
		return numeroIdentificadorUnico;
	}
	public void setNumeroIdentificadorUnico(String numeroIdentificadorUnico) {
		this.numeroIdentificadorUnico = numeroIdentificadorUnico;
	}
	public String getPrefixoDependencia() {
		return prefixoDependencia;
	}
	
	public int getIntPrefixoDepe() {
		try {
			return Integer.parseInt(prefixoDependencia);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setPrefixoDependencia(String prefixoDependencia) {
		this.prefixoDependencia = prefixoDependencia;
	}
	public String getPrefixoDiretoria() {
		return prefixoDiretoria;
	}
	
	public int getIntPrefixoDiretoria() {
		try {
			return Integer.parseInt(prefixoDiretoria);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setPrefixoDiretoria(String prefixoDiretoria) {
		this.prefixoDiretoria = prefixoDiretoria;
	}
	public String getPrefixoSuperEstadual() {
		return prefixoSuperEstadual;
	}
	
	public int getIntPrefixoSuperEstadual() {
		try {
			return Integer.parseInt(codigoUorDependencia);
		} catch(Exception e) {
			return -1;
		}
	}
	
	public void setPrefixoSuperEstadual(String prefixoSuperEstadual) {
		this.prefixoSuperEstadual = prefixoSuperEstadual;
	}
	public String getResponsabilidadeFuncional() {
		return responsabilidadeFuncional;
	}
	public void setResponsabilidadeFuncional(String responsabilidadeFuncional) {
		this.responsabilidadeFuncional = responsabilidadeFuncional;
	}
	public String getSenhaCripto() {
		return senhaCripto;
	}
	public void setSenhaCripto(String senhaCripto) {
		this.senhaCripto = senhaCripto;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getTelefoneCelular() {
		return telefoneCelular;
	}
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	public String getTelefoneComercial() {
		return telefoneComercial;
	}
	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}
	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}
	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}
	public String getTextoComissaoUsuario() {
		return textoComissaoUsuario;
	}
	public void setTextoComissaoUsuario(String textoComissaoUsuario) {
		this.textoComissaoUsuario = textoComissaoUsuario;
	}
	public String getTipoDependencia() {
		return tipoDependencia;
	}
	public void setTipoDependencia(String tipoDependencia) {
		this.tipoDependencia = tipoDependencia;
	}
	public String getCdUorEqp() {
		return cdUorEqp;
	}
	public void setCdUorEqp(String cdUorEqp) {
		this.cdUorEqp = cdUorEqp;
	}
  
  
  
  private static String str(Object obj) {
    String val = null;
    if(obj != null) {
      val = Objects.toString(obj);
    }
    return val;
  }
  
  
  
  public static Usuario from(User usr) {
    Usuario usu = new Usuario();
    usu.setAcessos(usr.getAcessos());
    usu.setCdUorEqp(str(usr.getUorEquipe()));
    usu.setChave(usr.getChave());
    usu.setCodigoCliente(str(usr.getMci()));
    usu.setCodigoComissao(str(usr.getCodigoComissao()));
    usu.setCodigoComissaoUsuario(str(usr.getCodigoComissao()));
    usu.setCodigoDivisao(str(usr.getUorEquipe()));
    usu.setCodigoInstituicao(str(usr.getCodigoInstituicao()));
    usu.setCodigoInstituicaoOrganizacional(str(usr.getCodigoIOR()));
    usu.setCodigoPilar(str(usr.getCodigoPilar()));
    usu.setCodigoPrefixoDependencia(str(usr.getPrefixoDepe()));
    usu.setCodigoReferenciaOrganizacional(str(usr.getRefOrgc()));
    usu.setCodigoTipoDependencia(str(usr.getTipoDepe()));
    usu.setCodigoUorDependencia(str(usr.getUorDepe()));
    usu.setEmail(usr.getEmail());
    usu.setIdSessao(usr.getTokenID());
    usu.setNome(usr.getNome());
    usu.setNomeCamelCase(usr.getNome());
    usu.setNomeComum(usr.getNomeGuerra());
    usu.setNomeDistinto(usr.getNomeGuerra());
    usu.setNomeGuerra(usr.getNomeGuerra());
    usu.setNumeroCPF(usr.getCpf());
    usu.setPrefixoDependencia(str(usr.getPrefixoDepe()));
    usu.setPrefixoDiretoria(str(usr.getPrefixoDiretoria()));
    usu.setPrefixoSuperEstadual(str(usr.getPrefixoSuper()));
    usu.setResponsabilidadeFuncional(str(usr.getRespFuncional()));
    usu.setSiglaUF(usr.getUF());
    int i = usr.getNome().lastIndexOf(" ");
    usu.setSobrenome(usr.getNome().substring(i+1));
    usu.setTelefoneCelular(usr.getCelular());
    usu.setTelefoneComercial(usr.getTelefone());
    usu.setTelefoneResidencial(usr.getTelefoneCasa());
    usu.setTextoComissaoUsuario(usr.getComissao());
    usu.setTipoDependencia(str(usr.getTipoDepe()));
    usu.setTokenId(usr.getTokenID());
    return usu;
  }
  
}