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
            switch (opcao) {
                case 1 -> {
                    // Atualiza a lista com os produtos do arquivo
                    produtos = EstoqueService.lerEstoque(caminho);

                    String nome = JOptionPane.showInputDialog("Digite o nome do produto:");

                    // Procura se o produto já existe
                    Produto produtoExistente = null;
                    for (Produto p : produtos) {
                        if (p.getNome().equalsIgnoreCase(nome)) {
                            produtoExistente = p;
                            break;
                        }
                    }

                    if (produtoExistente != null) {
                        // Produto já existe — pedir só a quantidade para somar
                        String qtdTexto = JOptionPane.showInputDialog("Produto já existe. Digite a quantidade a adicionar:");
                        try {
                            int qtd = Integer.parseInt(qtdTexto);
                            if (qtd > 0) {
                                produtoExistente.adicionarQuantidade(qtd);
                                JOptionPane.showMessageDialog(null, "✅ Quantidade atualizada com sucesso!");
                                EstoqueService.gravarEstoque(caminho, produtos);
                            } else {
                                JOptionPane.showMessageDialog(null, "❌ Quantidade inválida.");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "❌ Digite um número válido.");
                        }
                    } else {
                        // Produto novo — pede nome, quantidade e preço
                        String quantidadeTexto = JOptionPane.showInputDialog("Digite a quantidade do produto:");
                        try {
                            int quantidade = Integer.parseInt(quantidadeTexto);
                            String precoTexto = JOptionPane.showInputDialog("Digite o preço do produto:");
                            try {
                                double preco = Double.parseDouble(precoTexto);
                                produtos.add(new Produto(nome, quantidade, preco));
                                JOptionPane.showMessageDialog(null, "✅ Produto adicionado com sucesso!");
                                EstoqueService.gravarEstoque(caminho, produtos);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "❌ Preço inválido!");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "❌ Quantidade inválida!");
                        }
                    }
                }

                case 2 -> {
                    produtos = EstoqueService.lerEstoque(caminho);

                    if (produtos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado para remover.");
                    } else {
                        String nomeParaRemover = JOptionPane.showInputDialog("Digite o nome do produto que deseja remover:");

                        Produto produtoEncontrado = null;
                        for (Produto p : produtos) {
                            if (p.getNome().equalsIgnoreCase(nomeParaRemover)) {
                                produtoEncontrado = p;
                                break;
                            }
                        }

                        if (produtoEncontrado != null) {
                            String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade que deseja remover:");
                            try {
                                int qtdParaRemover = Integer.parseInt(quantidadeStr);

                                if (qtdParaRemover <= 0) {
                                    JOptionPane.showMessageDialog(null, "Digite uma quantidade maior que zero.");
                                } else if (qtdParaRemover >= produtoEncontrado.getQuantidade()) {
                                    produtos.remove(produtoEncontrado);
                                    JOptionPane.showMessageDialog(null, "Produto removido completamente do estoque.");
                                } else {
                                    produtoEncontrado.removerQuantidade(qtdParaRemover);
                                    JOptionPane.showMessageDialog(null, "Quantidade atualizada com sucesso!");
                                }

                                // Salva o novo estoque
                                EstoqueService.gravarEstoque(caminho, produtos);

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Digite uma quantidade válida.");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                        }
                    }
                }



                case 3 -> {
                    produtos = EstoqueService.lerEstoque(caminho);

                    if (produtos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado.");
                    } else {
                        StringBuilder lista = new StringBuilder("Produtos cadastrados:\n\n");
                        for (Produto produto : produtos) {
                            lista.append(produto.getNome())
                                    .append(" - Quantidade: ").append(produto.getQuantidade())
                                    .append(" - Preço: R$ ").append(String.format("%.2f", produto.getValorUnitario()))
                                    .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, lista.toString());
                    }
                }

            }

        }
    }
}