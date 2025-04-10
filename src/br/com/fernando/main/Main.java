package br.com.fernando.main;

import br.com.fernando.model.Produto;
import br.com.fernando.service.EstoqueService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;

// Classe principal que executa a aplicação
public class Main {
    public static void main(String[] args) {

        // Nome do arquivo de estoque
        String nomeDoArquivo = "estoque.csv";

        // Obtém o diretório atual do projeto
        String diretorio = System.getProperty("user.dir");

        // Monta o caminho completo do arquivo
        String caminho = diretorio + "\\" + nomeDoArquivo;

        // Cria uma lista com os produtos do estoque
        List<Produto> produtos = new ArrayList<>();
        int opcao = 0;

        // Laço principal do menu
        while (opcao != 4) {
            // Menu com opções
            String menu = """
                        Escolha uma opção:
                        1 - Adicionar produto
                        2 - Remover produto
                        3 - Listar produtos
                        4 - Sair
                    """;
            // Mostra a caixa de entrada e lê a opção escolhida
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
                continue;
            }

        }

    }

}