package br.ce.wcaquino.servicos;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

@RunWith(ParallelRunner.class)
public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
		System.out.println("Iniciando...");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando...");
	}
	@Test
	public void deveSomarDoisValores() {
	int a = 5;
	int b = 3;
	Calculadora calc = new Calculadora();
	
	int resultado = calc.somar(a, b);
	
	Assert.assertEquals(8, resultado);
	}
	@Test
	public void deveSubtrairDoisValores() {
	
		int a = 8;
		int b = 5;
		Calculadora calc = new Calculadora();
		
		int resultado = calc.subtrair(a, b);
		
		Assert.assertEquals(3, resultado);
				
	}
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
	
		int a = 6;
		int b = 3;
		Calculadora calc = new Calculadora();
		
		int resultado = calc.dividir(a, b);
		
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExecaoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10;
		int b = 0;
		Calculadora calc = new Calculadora();
		
		calc.dividir(b, b);
	}
	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";
		
		int resultado = calc.divide(a, b);
		
		Assert.assertEquals(2, resultado);
	}
	
}
