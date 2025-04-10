package br.com.fernando.service;

import br.com.fernando.model.Produto;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
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
    public static List<Produto> lerEstoque(String caminho) {
        List<Produto> produtos = new ArrayList<>();

        try (
                FileReader fr = new FileReader(caminho);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula o cabeçalho
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length >= 3) {
                    String nome = partes[0].trim();
                    int quantidade = Integer.parseInt(partes[1].trim());
                    double valor = Double.parseDouble(partes[2].trim());

                    Produto produto = new Produto(nome, quantidade, valor);
                    produtos.add(produto);
                }
            }

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o estoque: " + e.getMessage());
        }

        return produtos;
    }
}
