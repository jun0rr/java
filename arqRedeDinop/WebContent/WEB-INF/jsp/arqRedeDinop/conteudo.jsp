<%@page trimDirectiveWhitespaces="true" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/tld/fn.tld" prefix="fn"%>

<fmt:setLocale value="pt_BR" />
<!-- TOPO -->
<div class="grid_24">
	<div id="extraContent" class="extraContent grid_24 ui-widget-content">
		<div class="ui-widget-header header">
			<img class="simbBBTabs" src="/header2012/images/iconsNew/simb_BB.png">
			<span id="statusConfigFavoritos"></span>
			<div id="nmTitle"></div>
			<button
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only salvarFavoritos"
				title="Salvar" aria-disabled="false" role="button"
				style="display: none;">
				<span class="ui-button-icon-primary ui-icon ui-icon-disk"></span> <span
					class="ui-button-text">Filtrar</span>
			</button>
			<button
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only print"
				title="Imprimir" aria-disabled="false" role="button"
				style="display: none;">
				<span class="ui-button-icon-primary ui-icon ui-icon-print"></span> <span
					class="ui-button-text">Filtrar</span>
			</button>

			<button
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only closeExtraContent"
				title="Fechar" aria-disabled="false" role="button">
				<span class="ui-button-icon-primary ui-icon ui-icon-circle-close"></span>
				<span class="ui-button-text">Filtrar</span>
			</button>

		</div>
		<div class="content ui-widget"></div>
	</div>

	<div id="configPrefs" class="extraContent grid_24 ui-widget-content"
		style="top: 0 !important"></div>
</div>
<div class="content grid_24">
	<!-- CABE�ALHO -->
	<div id="tabs1" class="">
		<img src="/header2012/images/iconsNew/simb_BB.png" class="simbBBTabs">
		<ul class="first">
			<!-- <li><a href="#body1" id="subNivel">SubNivel</a></li> -->
			
			<!-- T�TULO DA ABA -->
			<li><a href="#body_arh" id="tabArh">Arquivos ARH</a></li>
			<li><a href="#body_sislog" id="tabSislog">Arquivos Sislog</a></li>
			<li><a href="#body_atb" id="tabAtb">Arquivos ATB</a></li>
		</ul>
		<!-- <div id="body1" class="wait">
			<img id="linkSubNivel" style="cursor: pointer; border: 0; width: 70px; height: auto;" src="/arhControl/images/ballons03.gif" title="ToolTip"/>
		</div> -->
	
		
		<!-- SUBSTITUIR O SEU CONTEUDO AQUI -->
		<div id="body_arh" class="wait">
			<div id="tabelasArh" style="width: 95%; margin-left: auto; margin-right: auto;"></div>
		</div>
		<div id="body_sislog" class="wait">
			<div id="tabelasSislog" style="width: 95%; margin-left: auto; margin-right: auto;"></div>
		</div>
		<div id="body_atb" class="wait">
			<div id="tabelaAtb" style="width: 95%; margin-left: auto; margin-right: auto;"></div>
		</div>
	</div>
	<!-- FIM SUBSTITUIR O SEU CONTEUDO AQUI -->
	
	
</div>

<!-- CONTROLE -->
<div id="controle" style="display: none;">
	<table>
		<tr>
			<td><c:out value="${controle.fonte}"></c:out></td>
			<td><c:out value="${controle.nomeResponsavel}"></c:out></td>
			<td>@<fmt:formatDate value="${controle.dataAtualizacao}"
					type="date"></fmt:formatDate></td>
		</tr>
	</table>
</div>
<div id="urlCrumb" style="display: none;">
	<table>
		<thead>
			<tr>
				<th>Chave</th>
				<th>Nome</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${beanCrumb}" var='beanCrumb'>
				<tr>
					<td><c:out value="${beanCrumb.chave}"></c:out></td>
					<td><c:out value="${beanCrumb.desc}"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="display: none" id="loading">
	<span class="loading"> <img
		src="/header2012/images/imagesPortal2012/carregando.gif" alt="" />Carregando,
		aguarde...
	</span>
</div>
<!-- FIM CONTROLE -->
 
<script>
var sislogLoaded = false;
var atbLoaded = false;

$(document).ready( function(){
	$("#tabs1").tabs({
		cookie: {
			expires: 1
		}
	});
	
	$("#carregando").modal({});
	$("#tabelasArh").empty();
	$("#tabelasArh").load('/arqRedeDinop/exiba?cmdo=arqRedeDinop.filetables&label=arh', 
		function() {
			$.modal.close();
		}
	);
	
	$("#tabSislog").click(function() {
		if(!sislogLoaded) {
			$("#carregando").modal({});
			$("#tabelasSislog").empty();
			$("#tabelasSislog").load('/arqRedeDinop/exiba?cmdo=arqRedeDinop.filetables&label=sislog', 
				function() {
					$.modal.close();
				}
			);
			sislogLoaded = true;
		}
	});
	
	$("#tabAtb").click(function() {
		if(!atbLoaded) {
			$("#carregando").modal({});
			$("#tabelaAtb").empty();
			$("#tabelaAtb").load('/arqRedeDinop/exiba?cmdo=arqRedeDinop.filetables&label=atb', 
				function() {
					$.modal.close();
				}
			);
			atbLoaded = true;
		}
	});
	
});
</script>