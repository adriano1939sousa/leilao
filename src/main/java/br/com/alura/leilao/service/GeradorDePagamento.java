package br.com.alura.leilao.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Pagamento;

@Service
public class GeradorDePagamento {

	private PagamentoDao pagamentoDAO;
	private Clock clock;

	@Autowired
	public GeradorDePagamento(PagamentoDao pagamentoDAO, Clock clock) {
		this.pagamentoDAO = pagamentoDAO;
		this.clock = clock;
	}

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now(clock).plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, proximoDiaUtil(vencimento));
		this.pagamentoDAO.salvar(pagamento);
	}

	private LocalDate proximoDiaUtil(LocalDate vencimento) {
		DayOfWeek diaDaSemana = vencimento.getDayOfWeek();
		if(diaDaSemana.equals(DayOfWeek.SATURDAY)) {
			return vencimento.plusDays(2);
		}else if(diaDaSemana.equals(DayOfWeek.SUNDAY)) {
			return vencimento.plusDays(1);
		}

		return vencimento;
	}

}
