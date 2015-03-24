package br.com.bb.dinop.arqRedeDinop.beans;

import java.io.Serializable;
import java.util.Date;


public class ArquivoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String nome;
	
	private String label;
	
	private long uor;
	
	private Date atl;
	
	private String link;
	
	private String desc;
	
	
	public ArquivoBean() {
		nome = null;
		label = null;
		uor = -1;
		id = -1;
		atl = null;
		link = null;
		desc = null;
	}
	
	
	public ArquivoBean(int id, String nome, String lbl, long uor, Date atl, String link, String desc) {
		this.id = id;
		this.nome = nome;
		this.label = lbl;
		this.uor = uor;
		this.atl = atl;
		this.link = link;
		this.desc = desc;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public long getUor() {
		return uor;
	}


	public void setUor(long uor) {
		this.uor = uor;
	}


	public Date getAtualizacao() {
		return atl;
	}


	public void setAtualizacao(Date atl) {
		this.atl = atl;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getDescricao() {
		return desc;
	}


	public void setDescricao(String desc) {
		this.desc = desc;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (int) (uor ^ (uor >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoBean other = (ArquivoBean) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (uor != other.uor)
			return false;
		return true;
	}
	
}
