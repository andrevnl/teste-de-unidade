package br.com.caelum.leilao.dominio.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

public class LanceTest {

    @Test(expected=IllegalArgumentException.class)
    public void deveRecusarLancesComValorDeZero() {
        new Lance(new Usuario("John Doe"), 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void deveRecusarLancesComValorNegativo() {
        new Lance(new Usuario("John Doe"), -10);
    }
}