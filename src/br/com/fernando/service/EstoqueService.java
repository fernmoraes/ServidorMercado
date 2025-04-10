package br.com.fernando.service;

// Importa a classe Produto que será usada na lista
import br.com.fernando.model.Produto;

import javax.swing.*; // Para exibir mensagens gráficas em caso de erro
import java.io.*;      // Para leitura e gravação de arquivos
import java.util.ArrayList;
import java.util.List;

// Classe utilitária com métodos para salvar e carregar o estoque
public class EstoqueService {

    // Método para gravar a lista de produtos em um arquivo CSV
    public static void gravarEstoque(String caminho, List<Produto> produtos){
        try (
                // Cria o objeto FileWriter que permite escrever em arquivos
                FileWriter fw = new FileWriter(caminho);

                // PrintWriter facilita a escrita de linhas no arquivo
                PrintWriter pw = new PrintWriter(fw)
        ) {
            // Escreve o cabeçalho do CSV (linha inicial)
            pw.println("Produto; Quantidade; Valor da Unidade;");

            // Percorre todos os produtos e escreve cada um no arquivo
            for (Produto p : produtos) {
                pw.println(p.toString()); // Usa o toString da classe Produto para formatar
            }

            // Exibe no console que o arquivo foi salvo
            System.out.println("Arquivo salvo com sucesso em: " + caminho);

        } catch (IOException e) {
            // Se ocorrer algum erro ao salvar o arquivo, imprime no console
            e.printStackTrace();
        }
    }

    // Método para ler os produtos de um arquivo CSV e devolver uma lista
    public static List<Produto> lerEstoque(String caminho) {
        // Cria uma lista vazia para armazenar os produtos lidos do arquivo
        List<Produto> produtos = new ArrayList<>();

        try (
                // Abre o arquivo para leitura
                FileReader fr = new FileReader(caminho);

                // Permite ler o arquivo linha por linha
                BufferedReader br = new BufferedReader(fr)
        ) {
            String linha;
            boolean primeiraLinha = true; // Usado para ignorar o cabeçalho

            // Lê todas as linhas do arquivo
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula o cabeçalho
                    continue;
                }

                // Divide a linha em partes usando o delimitador ";"
                String[] partes = linha.split(";");

                // Garante que há pelo menos 3 campos na linha (nome, quantidade, valor)
                if (partes.length >= 3) {
                    // Remove espaços e converte os dados
                    String nome = partes[0].trim();
                    int quantidade = Integer.parseInt(partes[1].trim());
                    double valor = Double.parseDouble(partes[2].trim());

                    // Cria um novo objeto Produto com os dados lidos
                    Produto produto = new Produto(nome, quantidade, valor);

                    // Adiciona o produto à lista
                    produtos.add(produto);
                }
            }

        } catch (IOException | NumberFormatException e) {
            // Se der erro ao ler o arquivo ou converter dados, mostra no JOptionPane
            JOptionPane.showMessageDialog(null, "Erro ao ler o estoque: " + e.getMessage());
        }

        // Retorna a lista de produtos lida do arquivo
        return produtos;
    }
}
