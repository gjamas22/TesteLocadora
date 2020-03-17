package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception{
		
		LocacaoService service = new LocacaoService(); 
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1",0,5.0);
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		error.checkThat(locacao.getValor(),is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	
	}
	
	@Test(expected=Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		LocacaoService service = new LocacaoService(); 
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1",0,5.0);
		
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_filmeSemEstoque2() throws Exception {
		LocacaoService service = new LocacaoService(); 
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1",2,5.0);
		
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lan�ado uma exce��o!");
		}catch(Exception e) {
			assertThat(e.getMessage(),is("Filme sem estoque!"));
		}
	}
	@Test(expected=Exception.class)
	public void testLocacao_filmeSemEstoque3() throws Exception {
		LocacaoService service = new LocacaoService(); 
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1",0,5.0);
		
		service.alugarFilme(usuario, filme);
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque!");
	}
}
