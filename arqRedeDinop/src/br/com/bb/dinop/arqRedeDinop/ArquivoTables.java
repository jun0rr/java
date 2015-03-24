package br.com.bb.dinop.arqRedeDinop;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.bb.dinop.action.ActionRouter;
import br.com.bb.dinop.action.RequestParameters;
import br.com.bb.dinop.arqRedeDinop.beans.ArquivoBean;
import br.com.bb.dinop.arqRedeDinop.persistencia.ArquivoPersistencia;
import br.com.bb.dinop.base.BaseCmdo;
import br.com.bb.sso.api.bean.Usuario;

public class ArquivoTables extends BaseCmdo {
	
	public ArquivoTables() {
		super();
		this.transacaoAcesso = "arqRedeDinop.conteudo";
	}
	
	
	@Override
	public ActionRouter perform(final HttpServlet servlet, final HttpServletRequest req, final HttpServletResponse res) throws ServletException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
		try {
			String uorstr = usuario.getCodigoUorDependencia();
			long uor = -1;
			try {
				uor = Long.parseLong(uorstr);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				throw new ServletException("Cod UOR inv�lido (uor="+ uorstr+ ")");
			}

			RequestParameters rp = getParameterMap(req);
			String label = rp.getStringParm("label");
			
			ArquivoPersistencia ap = new ArquivoPersistencia(label);
			List<ArquivoBean> aux = ap.getArquivosAuxiliares();
			List<ArquivoBean> arq = ap.getArquivos(uor);
			arq = ap.getArquivosJurisdicao(uor, arq);
			
			String uf = ap.getUfIncluded(uor);
			if(uf != null) {
				arq = ap.getArquivosUF(uf, arq);
			}
			
			session.setAttribute("auxiliares", aux);
			session.setAttribute("arquivos", arq);
			session.setAttribute("usuario", usuario);
			session.setAttribute("label", label);
			Boolean displayUploadForm = ("atb".equals(label) && uor == 19982);
			session.setAttribute("displayUploadForm", displayUploadForm);
			
			this.destPage = "arqRedeDinop.filetables.jsp";
		} 
		catch (final Exception exception) {
			exception.printStackTrace();
			throw new ServletException(exception.getMessage());
		}
		return new ActionRouter(this.destPage, true, true, this.idPagina, usuario);
	}

}
