package br.com.fernando.main;

// Importa as classes necessárias
import br.com.fernando.model.Produto;
import br.com.fernando.service.EstoqueService;

import javax.swing.JOptionPane; // Para caixas de entrada e mensagens gráficas
import java.util.ArrayList;
import java.util.List;

// Classe principal da aplicação
public class Main {
    public static void main(String[] args) {

        // Nome do arquivo onde o estoque será salvo
        String nomeDoArquivo = "estoque.csv";

        // Obtém o diretório atual do projeto (onde está rodando o programa)
        String diretorio = System.getProperty("user.dir");

        // Cria o caminho completo do arquivo
        String caminho = diretorio + "\\" + nomeDoArquivo;

        // Lista de produtos em memória
        List<Produto> produtos = new ArrayList<>();
        int opcao = 0; // Variável que controlará o menu

        // Laço principal do menu (só para quando o usuário digitar 4)
        while (opcao != 4) {
            // Menu de opções
            String menu = """
                        Escolha uma opção:
                        1 - Adicionar produto
                        2 - Remover produto
                        3 - Listar produtos
                        4 - Sair
                    """;

            // Mostra o menu e lê a opção digitada
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
                continue;
            }

            // Verifica qual opção o usuário escolheu
            switch (opcao) {

                case 1 -> {
                    // Atualiza a lista lendo do arquivo CSV
                    produtos = EstoqueService.lerEstoque(caminho);

                    // Solicita o nome do produto
                    String nome = JOptionPane.showInputDialog("Digite o nome do produto:");

                    // Procura o produto na lista (caso já exista)
                    Produto produtoExistente = null;
                    for (Produto p : produtos) {
                        if (p.getNome().equalsIgnoreCase(nome)) {
                            produtoExistente = p;
                            break;
                        }
                    }

                    // Se o produto já existe, apenas atualiza a quantidade
                    if (produtoExistente != null) {
                        String qtdTexto = JOptionPane.showInputDialog("Produto já existe. Digite a quantidade a adicionar:");
                        try {
                            int qtd = Integer.parseInt(qtdTexto);
                            if (qtd > 0) {
                                produtoExistente.adicionarQuantidade(qtd); // Atualiza a quantidade
                                JOptionPane.showMessageDialog(null, "✅ Quantidade atualizada com sucesso!");
                                EstoqueService.gravarEstoque(caminho, produtos); // Salva no CSV
                            } else {
                                JOptionPane.showMessageDialog(null, "❌ Quantidade inválida.");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "❌ Digite um número válido.");
                        }

                    } else {
                        // Se for novo, pede todos os dados
                        String quantidadeTexto = JOptionPane.showInputDialog("Digite a quantidade do produto:");
                        try {
                            int quantidade = Integer.parseInt(quantidadeTexto);
                            String precoTexto = JOptionPane.showInputDialog("Digite o preço do produto:");
                            try {
                                double preco = Double.parseDouble(precoTexto);

                                // Adiciona novo produto à lista
                                produtos.add(new Produto(nome, quantidade, preco));

                                JOptionPane.showMessageDialog(null, "✅ Produto adicionado com sucesso!");
                                EstoqueService.gravarEstoque(caminho, produtos); // Salva no CSV
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "❌ Preço inválido!");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "❌ Quantidade inválida!");
                        }
                    }
                }

                case 2 -> {
                    // Lê os produtos do arquivo
                    produtos = EstoqueService.lerEstoque(caminho);

                    // Se não houver produtos cadastrados
                    if (produtos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado para remover.");
                    } else {
                        // Solicita o nome do produto a ser removido
                        String nomeParaRemover = JOptionPane.showInputDialog("Digite o nome do produto que deseja remover:");

                        // Procura o produto na lista
                        Produto produtoEncontrado = null;
                        for (Produto p : produtos) {
                            if (p.getNome().equalsIgnoreCase(nomeParaRemover)) {
                                produtoEncontrado = p;
                                break;
                            }
                        }

                        // Se o produto foi encontrado
                        if (produtoEncontrado != null) {
                            // Solicita a quantidade a ser removida
                            String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade que deseja remover:");
                            try {
                                int qtdParaRemover = Integer.parseInt(quantidadeStr);

                                if (qtdParaRemover <= 0) {
                                    JOptionPane.showMessageDialog(null, "Digite uma quantidade maior que zero.");
                                } else if (qtdParaRemover >= produtoEncontrado.getQuantidade()) {
                                    // Remove o produto completamente se a quantidade for maior ou igual
                                    produtos.remove(produtoEncontrado);
                                    JOptionPane.showMessageDialog(null, "Produto removido completamente do estoque.");
                                } else {
                                    // Apenas reduz a quantidade
                                    produtoEncontrado.removerQuantidade(qtdParaRemover);
                                    JOptionPane.showMessageDialog(null, "Quantidade atualizada com sucesso!");
                                }

                                // Salva alterações no CSV
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
                    // Lê o estoque novamente do arquivo
                    produtos = EstoqueService.lerEstoque(caminho);

                    // Se não houver produtos cadastrados
                    if (produtos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado.");
                    } else {
                        // Monta a string com todos os produtos
                        StringBuilder lista = new StringBuilder("Produtos cadastrados:\n\n");
                        for (Produto produto : produtos) {
                            lista.append(produto.getNome())
                                    .append(" - Quantidade: ").append(produto.getQuantidade())
                                    .append(" - Preço: R$ ").append(String.format("%.2f", produto.getValorUnitario()))
                                    .append("\n");
                        }

                        // Mostra os produtos na tela
                        JOptionPane.showMessageDialog(null, lista.toString());
                    }
                }

                // Caso o usuário digite 4, o laço termina e o programa encerra
            }
        }
    }
}
