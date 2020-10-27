package br.com.caelum.leilao.dominio.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    private Usuario joao;
    private Usuario jose;
    private Usuario maria;
    private Leilao leilao;
    private Avaliador leiloeiro;

    @Before
    public void setup() {
        mockarUmItemLeilao();
        mockarTresUsuarios();
        mockarAvaliador();
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    private Usuario instanciaUsuario(String nome) {
        return new Usuario(nome);
    }

    private Lance instanciaLance(Usuario usuario, double valor) {
        return new Lance(usuario, valor);
    }

    private Leilao instanciaItemLeilao(String item) {
        return new Leilao(item);
    }

    private void mockarTresUsuarios() {
        joao = instanciaUsuario("João");
        jose = instanciaUsuario("Jose");
        maria = instanciaUsuario("Maria");
    }

    private void mockarUmItemLeilao() {
        leilao = instanciaItemLeilao("Playstation 3 Novo");
    }

    private Avaliador getAvalia(Leilao leilao) {
        leiloeiro.avalia(leilao);
        return leiloeiro;
    }

    private void mockarAvaliador() {
        leiloeiro = new Avaliador();
    }

    @Test
    public void deveEntenderLancesEmModoCrescente() {
        leilao.propoe(instanciaLance(joao, 300.0));
        leilao.propoe(instanciaLance(jose, 400.0));
        leilao.propoe(instanciaLance(maria, 250.0));

        // parte 2: acao
        getAvalia(leilao);

        // parte 3: validacao
        double maiorEsperado = 400;
        double menorEsperado = 250;
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveCalcularAMedia() {
        leilao.propoe(instanciaLance(maria, 300.0));
        leilao.propoe(instanciaLance(joao, 400.0));
        leilao.propoe(instanciaLance(jose, 500.0));

        // executando a acao
        getAvalia(leilao);

        // comparando a saida com o esperado
        assertEquals(400, leiloeiro.getMediaDeTodos(), 0.0001);
    }


    @Test
    public void testaMediaDeZeroLance(){
        // acao
        getAvalia(leilao);

        //validacao
        assertEquals(0, leiloeiro.getMediaDeTodos(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        leilao.propoe(instanciaLance(joao, 1000.0));

        // parte 2: acao
        getAvalia(leilao);

        // parte 3: validacao
        double maiorEsperado = 1000.0;
        double menorEsperado = 1000.0;
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        leilao = new CriadorDeLeilao().para("Playstantion 3")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(jose, 300.0)
                .lance(joao, 400.0)
                .lance(maria, 500.0)
                .lance(jose, 600.0)
                .constroi();

        getAvalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        assertEquals(600.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(500.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(400.0, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveRetornarOsDoisMaioresLances() {
        leilao.propoe(instanciaLance(joao, 100.0));
        leilao.propoe(instanciaLance(maria, 200.0));

        getAvalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(2, maiores.size());
        assertEquals(200.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(100.0, maiores.get(1).getValor(), 0.00001);
    }

    @Test
    public void deveRetornarListaVaziaPoisNaoFoiDadoLances() {
        getAvalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(0, maiores.size());
    }
}
