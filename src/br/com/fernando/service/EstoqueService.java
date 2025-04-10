package br.com.fernando.service;

import br.com.fernando.model.Produto;

import java.io.*;
import java.util.List;

// Classe de serviço com métodos utilitários para salvar e ler o estoque
public class EstoqueService {

    // Metodo que grava a lista de produtos em um arquivo CSV
    public static void gravarEstoque(String caminho, List<Produto> produtos){
        try (
                // Cria uma conexão para gravar no arquivo
                FileWriter fw = new FileWriter(caminho);
                // Permite escrever com mais facilidade no arquivo
                PrintWriter pw = new PrintWriter(fw)
        ) {
            // Escreve o cabeçalho do arquivo CSV
            pw.println("Produto; Quantidade; Valor da Unidade;");

            // Para cada produto da lista, escreve no arquivo usando o toString formatado
            for (Produto p : produtos) {
                pw.println(p.toString());
            }

            // Confirmação no terminal
            System.out.println("Arquivo salvo com sucesso em: " + caminho);

        } catch (IOException e) {
            // Em caso de erro na gravação, mostra detalhes
            e.printStackTrace();
        }
    }

    // Metodo que lê e imprime o conteúdo do estoque a partir do arquivo CSV
    public static void lerEstoque(String caminho) {
        try (
                // Abre o arquivo para leitura
                FileReader fr = new FileReader(caminho);
                // Permite leitura linha a linha
                BufferedReader br = new BufferedReader(fr)
        ) {
            String linha;

            // Lê até encontrar uma linha nula (fim do arquivo)
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }

        } catch (IOException e) {
            // Em caso de erro na leitura, mostra detalhes
            e.printStackTrace();
        }
    }
}
