package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spc;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		System.out.println("Iniciando 3 ....");
		CalculadoraTest.ordem.append(3);
	}
	@After
	public void tearDown() {
		System.out.println("Finalizando 3...");
	}
	
	@AfterClass
	public static void tearDownClass(){
		System.out.println(CalculadoraTest.ordem.toString());
	}
	
	private static Filme filme1 = FilmeBuilder.umFilme().agora();
	private static Filme filme2 = FilmeBuilder.umFilme().agora();
	private static Filme filme3= FilmeBuilder.umFilme().agora();
	private static Filme filme4= FilmeBuilder.umFilme().agora();
	private static Filme filme5= FilmeBuilder.umFilme().agora();
	private static Filme filme6= FilmeBuilder.umFilme().agora();
	private static Filme filme7= FilmeBuilder.umFilme().agora();
	
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1,filme2), 8.0, "2 filmes :Sem Descontos"},
			{Arrays.asList(filme1,filme2, filme3), 11.0, "3 filmes :25%"},
			{Arrays.asList(filme1,filme2, filme3,filme4),  13.0, "4 filmes :50%"},
			{Arrays.asList(filme1,filme2, filme3,filme4,filme5), 14.0, "5 filmes :75%"},
			{Arrays.asList(filme1,filme2, filme3,filme4,filme5, filme6, filme7), 18.0, "7 filmes :Sem Descontos"}
			
		});
		
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException, InterruptedException {
	Usuario usuario = new Usuario("Usuario 1 ");

	Thread.sleep(5000); 
	
	Locacao resultado = service.alugarFilme(usuario, filmes);
	
	assertThat(resultado.getValor(), is(valorLocacao));
	

	}	
}
