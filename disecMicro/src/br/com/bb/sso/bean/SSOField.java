/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.sso.bean;


/**
 *
 * @author juno
 */
public enum SSOField {
	
	PRF_DEPE(SSOField.STR_PRF_DEPE),
	COD_PILAR(SSOField.STR_COD_PILAR),
	PHONE(SSOField.STR_PHONE),
	NOME(SSOField.STR_NOME),
	CPF(SSOField.STR_CPF),
	RESP_FUN(SSOField.STR_RESP_FUN),
	TIPO_DEPE(SSOField.STR_TIPO_DEPE),
	PRF_SUPER(SSOField.STR_PRF_SUPER),
	GRUPAMENTO(SSOField.STR_GRUPAMENTO),
	UOR_EQP(SSOField.STR_UOR_EQP),
	COD_IOR(SSOField.STR_COD_IOR),
	MOBILE(SSOField.STR_MOBILE),
	NOME_GUERRA(SSOField.STR_NOME_GUERRA),
	REF_ORGC(SSOField.STR_REF_ORGC),
	UF(SSOField.STR_UF),
	CHAVE_NOME(SSOField.STR_CHAVE_NOME),
	UOR_DEPE(SSOField.STR_UOR_DEPE),
	HOME_PHONE(SSOField.STR_HOME_PHONE),
	COD_COMISSAO(SSOField.STR_COD_COMISSAO),
	CHAVE(SSOField.STR_CHAVE),
	COD_INST(SSOField.STR_COD_INST),
	MCI(SSOField.STR_MCI),
	EMAIL(SSOField.STR_EMAIL),
	PRF_DIRETORIA(SSOField.STR_PRF_DIRETORIA),
	NOME_COMISSAO(SSOField.STR_NOME_COMISSAO),
	TOKENID(SSOField.STR_TOKENID),
	ACESSOS(SSOField.STR_ACESSOS);
	
	
	private SSOField(String name) {
		this.field = name;
	}
	
	
	private final String field;
	
	
	public String field() {
		return field;
	}
	
	
	public static final String
			STR_PRF_DEPE = "prefixoDependencia",
			STR_COD_PILAR = "codigoPilar",
			STR_PHONE = "telephonenumber",
			STR_NOME = "nm-idgl",
			STR_CPF = "nr-cpf",
			STR_RESP_FUN = "responsabilidadeFuncional",
			STR_TIPO_DEPE = "tipoDependencia",
			STR_PRF_SUPER = "prefixoSuperEstadual",
			STR_GRUPAMENTO = "grupamento",
			STR_UOR_EQP = "cd-eqp",
			STR_COD_IOR = "cd-ior",
			STR_MOBILE = "mobile",
			STR_NOME_GUERRA = "nomeGuerra",
			STR_REF_ORGC = "cd-ref-orgc",
			STR_UF = "nomeUF",
			STR_CHAVE_NOME = "cn",
			STR_UOR_DEPE = "cd-uor-dep",
			STR_HOME_PHONE = "homephone",
			STR_COD_COMISSAO = "codigoComissao",
			STR_CHAVE = "chaveFuncionario",
			STR_COD_INST = "codigoInstituicao",
			STR_MCI = "cd-cli",
			STR_EMAIL = "mail",
			STR_PRF_DIRETORIA = "prefixoDiretoria",
			STR_NOME_COMISSAO = "tx-cmss-usu",
			STR_TOKENID = "tokenId",
			STR_ACESSOS = "acessosUsu";
	
	
	public static SSOField fromName(String name) {
		SSOField field = null;
		switch(name) {
			case STR_ACESSOS:
				field = ACESSOS;
				break;
			case STR_CHAVE:
				field = CHAVE;
				break;
			case STR_CHAVE_NOME:
				field = CHAVE_NOME;
				break;
			case STR_COD_COMISSAO:
				field = COD_COMISSAO;
				break;
			case STR_COD_INST:
				field = COD_INST;
				break;
			case STR_COD_IOR:
				field = COD_IOR;
				break;
			case STR_COD_PILAR:
				field = COD_PILAR;
				break;
			case STR_CPF:
				field = CPF;
				break;
			case STR_EMAIL:
				field = EMAIL;
				break;
			case STR_GRUPAMENTO:
				field = GRUPAMENTO;
				break;
			case STR_HOME_PHONE:
				field = HOME_PHONE;
				break;
			case STR_MCI:
				field = MCI;
				break;
			case STR_MOBILE:
				field = MOBILE;
				break;
			case STR_NOME:
				field = NOME;
				break;
			case STR_NOME_COMISSAO:
				field = NOME_COMISSAO;
				break;
			case STR_NOME_GUERRA:
				field = NOME_GUERRA;
				break;
			case STR_PHONE:
				field = PHONE;
				break;
			case STR_PRF_DEPE:
				field = PRF_DEPE;
				break;
			case STR_PRF_DIRETORIA:
				field = PRF_DIRETORIA;
				break;
			case STR_PRF_SUPER:
				field = PRF_SUPER;
				break;
			case STR_REF_ORGC:
				field = REF_ORGC;
				break;
			case STR_RESP_FUN:
				field = RESP_FUN;
				break;
			case STR_TIPO_DEPE:
				field = TIPO_DEPE;
				break;
			case STR_TOKENID:
				field = TOKENID;
				break;
			case STR_UF:
				field = UF;
				break;
			case STR_UOR_DEPE:
				field = UOR_DEPE;
				break;
			case STR_UOR_EQP:
				field = UOR_EQP;
				break;
			default:
				throw new IllegalArgumentException(
						"Nome SSOField Inválido: "+ name
				);
		}
		return field;
	}
	
}
