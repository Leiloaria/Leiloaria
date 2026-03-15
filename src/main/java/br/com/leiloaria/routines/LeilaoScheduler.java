package br.com.leiloaria.routines;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.enums.StatusLeilao;

@Service
public class LeilaoScheduler {
	@Autowired
    private Facade facade;

	@Scheduled(cron = "0 */1 * * * *")
    public void encerrarLeiloesAbertos() {
        System.out.println("Verificando leilões expirados...");
        
        List<Leilao> leiloes = facade.listarLeiloesParaFinalizar();
        
        for (Leilao leilao : leiloes) {
			facade.encerrarLeilao(leilao.getId());
		}
    }
	
	@Scheduled(cron = "0 */1 * * * *")
    public void cancelarLeiloesPagamentoVencido() {
        System.out.println("Verificando leilões com pagamentos vencidos...");
        
        List<Leilao> leiloes = facade.listarLeiloesParaCancelar();
        
        for (Leilao leilao : leiloes) {
			facade.atualizarStatusLeilao(leilao.getId(), StatusLeilao.CANCELADO);
		}
    }
}
