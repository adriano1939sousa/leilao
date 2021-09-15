package br.com.alura.leilao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

class FinalizarLeilaoServiceTest {

	private FinalizarLeilaoService service;
	
	@Mock
	private LeilaoDao leilaoDAO;
	
	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.initMocks(this);
		this.service = new FinalizarLeilaoService(leilaoDAO);
	}
	

	@Test
	void finalizarLeilar() throws InterruptedException {
		List<Leilao> leiloes = leiloes();
		
		Mockito.when(leilaoDAO.buscarLeiloesExpirados()).thenReturn(leiloes);
		
		service.finalizarLeiloesExpirados();
		
		Leilao leilao = leiloes.get(0);
		
		Assert.assertTrue(leilao.getFechado());
		Assert.assertEquals(new BigDecimal("900"), leilao.getLanceVencedor().getValor());

		
		//Internamente ja faz o Assert 
		Mockito.verify(leilaoDAO).salvar(leilao);
	}
	
	private List<Leilao> leiloes(){
		List<Leilao> lista = new ArrayList<Leilao>();
		Leilao leilao = new Leilao("Celular", new BigDecimal(500), new Usuario("Adriano"));
		Lance primeiro = new Lance(new Usuario("Sousa"), new BigDecimal("600"));
		Lance segundo = new Lance(new Usuario("Andrade"), new BigDecimal("900"));
		
		leilao.propoe(primeiro);
		leilao.propoe(segundo);
		
		lista.add(leilao);
		
		return lista;
		
	}

}
