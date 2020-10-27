package br.com.caelum.leilao.dominio;

import br.com.caelum.leilao.dominio.servico.Leilao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    Leilao leilao;
    Usuario steveJobs;
    Usuario billGates;

    @Before
    public void setup() {
        mockarItemLeilao();
        mockarDoisUsuarios();
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    @BeforeClass
    public static void testandoBeforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        System.out.println("after class");
    }

    private void mockarItemLeilao() {
        leilao = new Leilao("Macbook Pro 15'");
    }

    private void mockarDoisUsuarios() {
        steveJobs = new Usuario("Steve Jobs");
        billGates = new Usuario("Bill Gates");
    }

    @Test
   public void deveReceberUmLance() {
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(steveJobs, 2000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(steveJobs, 2000));

        assertEquals(1, leilao.getLances().size());
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        leilao.propoe(new Lance(steveJobs, 2000.0));
        leilao.propoe(new Lance(billGates, 3000.0));

        leilao.propoe(new Lance(steveJobs, 4000.0));
        leilao.propoe(new Lance(billGates, 5000.0));

        leilao.propoe(new Lance(steveJobs, 6000.0));
        leilao.propoe(new Lance(billGates, 7000.0));

        leilao.propoe(new Lance(steveJobs, 8000.0));
        leilao.propoe(new Lance(billGates, 9000.0));

        leilao.propoe(new Lance(steveJobs, 10000.0));
        leilao.propoe(new Lance(billGates, 11000.0));

        // deve ser ignorado
        leilao.propoe(new Lance(steveJobs, 12000));

        assertEquals(10, leilao.getLances().size());
        int ultimo = leilao.getLances().size() - 1;
        assertEquals(11000, leilao.getLances().get(ultimo).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLance() {
        leilao.propoe(new Lance(steveJobs,2000));
        leilao.propoe(new Lance(billGates,3000));

        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }
}
