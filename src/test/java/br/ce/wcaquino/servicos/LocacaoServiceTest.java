package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.eHojeComDiferencaDias;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		System.out.println("Before");
		service = new LocacaoService();
	}

	@Test
	public void deveAlugarFilme() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		Locacao locacao = service.alugarFilme(usuario, filmes);

		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		error.checkThat(locacao.getDataLocacao(), eHojeComDiferencaDias(1));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 1, 5.0));

		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio!"));

			System.out.println("Forma robusta!");
		}
	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 2");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio!");

		service.alugarFilme(usuario, null);
		System.out.println("Forma nova!");
	}

	@Test
	public void DevePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 1 ");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0));

		Locacao resultado = service.alugarFilme(usuario, filmes);

		assertThat(resultado.getValor(), is(11.0));

	}

	@Test
	public void DevePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 1 ");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));

		Locacao resultado = service.alugarFilme(usuario, filmes);

		assertThat(resultado.getValor(), is(13.0));

	}

	@Test
	public void DevePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 1 ");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		Locacao resultado = service.alugarFilme(usuario, filmes);

		assertThat(resultado.getValor(), is(14.0));

	}

	@Test
	public void DevePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario("Usuario 1 ");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0),
				new Filme("Filme 6", 2, 4.0));

		Locacao resultado = service.alugarFilme(usuario, filmes);

		assertThat(resultado.getValor(), is(14.0));

	}

	@Test
	public void deveDevolverNaSegundaAoLugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		Locacao retorno = service.alugarFilme(usuario, filmes);

		assertThat(retorno.getDataRetorno(), caiEm(Calendar.SUNDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
}
