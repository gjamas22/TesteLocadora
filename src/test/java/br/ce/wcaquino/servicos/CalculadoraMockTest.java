package br.ce.wcaquino.servicos;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;



public class CalculadoraMockTest {

	@Test
	public void test() {
		Calculadora calc = mock(Calculadora.class);
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 20));
	}
}
