package br.com.caelum.leilao.dominio;

import br.com.caelum.leilao.dominio.servico.Leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double mediaDeTodos = 0;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {

        if(leilao.getLances().size() == 0) {
            throw new RuntimeException("Não é possivel avaliar um leilão sem lances!");
        }

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
        maiores.sort((o1, o2) -> Double.compare(o2.getValor(), o1.getValor()));
        maiores = maiores.subList(0, Math.min(maiores.size(), 3));
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
