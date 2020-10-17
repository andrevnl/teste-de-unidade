package br.com.caelum.leilao.dominio;

import br.com.caelum.leilao.dominio.servico.Leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double mediaDeTodos = 0;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {
        double total = 0;
        for(Lance lance : leilao.getLances()) {
            if (lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
            if (lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
            total += lance.getValor();
        }
        calculaMedia(leilao, total);
        getMaiores(leilao);
    }

    private void getMaiores(Leilao leilao) {
        maiores = new ArrayList<>(leilao.getLances());
        Collections.sort(maiores, (o1, o2) -> {
            if(o1.getValor() < o2.getValor()) return 1;
            if(o1.getValor() > o2.getValor()) return -1;
            return 0;
        });
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
    }

    private void calculaMedia(Leilao leilao, double total) {
        if(total == 0) {
            mediaDeTodos = 0;
            return;
        }
        mediaDeTodos = total / leilao.getLances().size();
    }

    public List<Lance> getTresMaiores() {
        return maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }

    public double getMediaDeTodos() {
        return mediaDeTodos;
    }
}
