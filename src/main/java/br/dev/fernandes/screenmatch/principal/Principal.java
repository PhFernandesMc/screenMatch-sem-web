package br.dev.fernandes.screenmatch.principal;

import br.dev.fernandes.screenmatch.model.DadosEpisodio;
import br.dev.fernandes.screenmatch.model.DadosSerie;
import br.dev.fernandes.screenmatch.model.DadosTemporada;
import br.dev.fernandes.screenmatch.service.ConsumoApi;
import br.dev.fernandes.screenmatch.service.ConverteDados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leiture = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=49744a85";

    public void exibeMenu() throws IOException, InterruptedException {
        System.out.println("Digite o nome da série para a busca!");
        var nomeSerie = leiture.nextLine().replaceAll(" ", "+");
        var json = consumo.obterDados(ENDERECO + nomeSerie + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i +  API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (DadosEpisodio dadosEpisodio : episodiosTemporada) {
//                System.out.println(dadosEpisodio.titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
