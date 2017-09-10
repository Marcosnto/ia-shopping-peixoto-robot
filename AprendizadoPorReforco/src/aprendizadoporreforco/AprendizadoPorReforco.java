/*==========================================================//
 * UNIVERSIDADE FEDERAL DE SERGIPE                         ||
 * CAMPUS ALBERTO DE CARVALHO - ITABAIANA/SE               ||
 * CURSO: SISTEMAS DE INFORMAÇÃO                           ||
 * DISCIPLINA: INTELIGÊNCIA ARTIFICIAL                     ||
 * TURMA: 2017.01                                          ||
 * DOCENTE: DR. ALCIDES XAVIER BENICASA                    ||
 * DISCENTE: EDNA DE CARVALHO ANDRADE                      ||
 *           MARCOS NETO SANTOS                            ||
 *           KAIC DE OLIVEIRA BARROS                       ||
 */
package aprendizadoporreforco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class AprendizadoPorReforco {

    //Constantes
    public static int TAMANHO = 23;
    public static String ESTADOS = "Estados2.txt";
    public static String ACOES = "Acoes2.txt";
    
    public static Estado estados[] = new Estado[TAMANHO];
    public static int acoes[] = new int[8];
    public static Dado tabela[][] = new Dado[TAMANHO][8];
    public static ArrayList decisao = new ArrayList<Integer>();

    public static int TotalExplorar = 0;
    public static int TotalUsufruir = 0;

    public static void main(String[] args) throws IOException {
        lerEstados();
        lerAcoes();
        System.out.println("INICIALIZADO APRENDIZADO");
        for (int i = 0; i < estados.length; i++) {
            estadosAcoes(i);
            aprendizado(5000, i); //quantas vezes vai rodar e quem é o destino            
        }
        System.out.println("FINALIZADO");
        int operador;
        Scanner entrada = new Scanner(System.in);
        String opcao;
        do {
            System.out.println("\n|---------------------------------------------|");
            System.out.println("|___________ 1- ESCOLHA O CAMINHOS CAMINHO_____|");
            System.out.println("|________________ 0 - SAIR _____________________|");
            System.out.println("|-----------------------------------------------|\n");
            System.out.println("Execulte a sua escolha: ");
            operador = entrada.nextInt();
            switch (operador) {
                case 1:
                    System.out.println("Digite a origem:(0 a 12): ");
                    int origem = entrada.nextInt();
                    System.out.println("Digite o destino :(0 a 12): ");
                    int destino = entrada.nextInt();
                    System.out.println("MOSTRANDO CAMINHO: " + caminho(destino,origem));
                    break;
                case 0:// está dando erro nessa parte
                    System.out.println("TEM CERTEZA QUE DESEJA SAIR? (S/N)");
                    opcao = entrada.next();
                    if (opcao.equalsIgnoreCase("S")) {
                        System.out.println("OBRIGADO POR ACESSAR NOSSO SISTEMA...");
                    } else {
                        operador = -1;
                    }
                    break;
                default:
                    System.out.println("OPERADOR INVÁLIDO!");
            }
        } while (operador != 0);
    }

    public static String caminho(int destino, int origem) throws IOException {
        lerArquivo(destino);
        String caminho = "";
        while (origem != destino) {
            int estadoAux = -1;
            double maior = -1;
            for (int i = 0; i < acoes.length; i++) {
                if (tabela[origem][i].getValorAcao() > maior && tabela[origem][i].getEstado() != -1) {
                    maior = tabela[origem][i].getValorAcao();
                    estadoAux = tabela[origem][i].getEstado();
                }
            }
            caminho = caminho + " " + estados[origem].getNome() + "->";
            origem = estadoAux;
        }
        return caminho + estados[destino].getNome();
    }

    public static void estadosAcoes(int destino) {
        estados[destino].setRecompensa(10);
        if (destino > 0) {
            estados[destino - 1].setRecompensa(0); //o estado anterior deixa de ter recompensa
        }
        //pra nao esquecer
        /*
        acoes[0] = 0; //esquerda
        acoes[1] = 1; //direita
        acoes[2] = 2; //cima
        acoes[3] = 3; //baixo

        acoes[4] = 4; //LateralEsquerdaCima
        acoes[5] = 5; //LateralEsquerdaBaixo
        acoes[6] = 6; //LateralDireitaCima
        acoes[7] = 7; //LateralDireitaBaixo*/
    }

    public static void aprendizado(int totalVezes, int destino) throws IOException {
        TotalUsufruir = 0;
        TotalExplorar = 0;
        int x = 0;
        Random seleciona = new Random();
        int estadoAtual;
        while (x < totalVezes) { //para parar quando chegar ao fim no totalVezes
            do {
                estadoAtual = seleciona.nextInt(TAMANHO); //Escolhe o estado aleatoriamente
                int proximoEstado = -1; //os pra inicializar           
                int acao;
                if (totalAcao(estadoAtual) == 1) { //pra chegar/sair so tem uma acao
                    acao = valorAcao(estadoAtual);
                    proximoEstado = tabela[estadoAtual][acao].getEstado();
                } else {
                    int decisao = escolha(); //escolher se vai usufruir ou explorar
                    if (decisao < 30) {  // vai usufruir
                        acao = maiorAcao(estadoAtual);
                        proximoEstado = tabela[estadoAtual][acao].getEstado();
                        TotalUsufruir++;
                    } else { //Vai explorar
                        TotalExplorar++;
                        acao = seleciona.nextInt(8);
                        boolean ok = true;
                        while (ok) {
                            Dado d = tabela[estadoAtual][acao];
                            if (d.getEstado() != -1) { //se existe estado pra essa acao a partir deste estado 
                                ok = false;
                                proximoEstado = d.getEstado();
                            } else {
                                acao = seleciona.nextInt(8); //Escolhe a Ação  aleatoria  
                            }
                        }
                    }
                }
                double recAt = estados[proximoEstado].getRecompensa() + (0.9 * (maiorValor(proximoEstado))); //Q-Learning                
                tabela[estadoAtual][acao].setValorAcao(recAt);
                estadoAtual = proximoEstado;
            } while (estadoAtual != destino); //estado final
            estadoAtual = seleciona.nextInt(TAMANHO); //escolhe outro ponto de partida aleatorio entre os 3 estados
            x++;
        }
        gravarArquivo(destino);
    }

    public static int escolha() {
        if (decisao.isEmpty()) {
            for (int i = 0; i < 100; i++) {
                decisao.add(i);
            }
        }
        Collections.shuffle(decisao); //embaralha
        int tam = decisao.size();
        int retor = (int) decisao.get(tam - 1);
        decisao.remove(tam - 1);
        return retor;
    }

    public static double maiorValor(int estado) {
        double maior = tabela[estado][0].valorAcao;
        for (int i = 1; i < acoes.length; i++) {
            if (tabela[estado][i].getValorAcao() >= maior) {
                maior = tabela[estado][i].getValorAcao();
            }
        }
        return maior;
    }

    public static int valorAcao(int estado) {
        for (int i = 0; i < acoes.length; i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                return i;
            }
        }
        return 0;
    }

    public static int totalAcao(int estado) {
        int total = 0;
        for (int i = 0; i < acoes.length; i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                total++;
            }
        }
        return total;
    }

    public static int maiorAcao(int estado) {
        int acao = -1;
        double maior = -1;
        for (int i = 0; i < acoes.length; i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                if (tabela[estado][i].getValorAcao() > maior) {
                    maior = tabela[estado][i].getValorAcao();
                    acao = i;
                }
            }
        }
        return acao;
    }

    public static void mostrarTabela() {
        System.out.printf("%-10s%-10s%-10s%-10s", "Estado", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.println("");
        for (int i = 0; i < tabela.length; i++) {
            System.out.printf("%-10s", i);
            for (int j = 0; j < tabela[0].length; j++) {
                if (j == 0) {
                    System.out.printf("%-10s%-10s%-10s", "Esquerda", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 1) {
                    System.out.printf("%-10s%-10s%-10s", "Direita", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 2) {
                    System.out.printf("%-10s%-10s%-10s", "Cima", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 3) {
                    System.out.printf("%-10s%-10s%-10s", "Baixo", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 4) {
                    System.out.printf("%-10s%-10s%-10s", "L.E.C.", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 5) {
                    System.out.printf("%-10s%-10s%-10s", "L.E.B.", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 6) {
                    System.out.printf("%-10s%-10s%-10s", "L.D.C.", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else {
                    System.out.printf("%-10s%-10s%-10s", "L.D.B.", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                }
            }
            System.out.println("");
        }
    }

    public static void gravarArquivo(int estado) throws FileNotFoundException, IOException {
        String arquivo = "Arquivo" + estado + ".txt";
        File f = new File(arquivo);
        try {
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < tabela.length; i++) {
                for (int j = 0; j < tabela[0].length; j++) {
                    pw.print(tabela[i][j].getEstado() + ";");//estado para o qual  vai
                    pw.print(tabela[i][j].getValorAcao() + ";");//valor da acao
                }
                pw.println();
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lerArquivo(int destino) throws FileNotFoundException, IOException {
        String arquivo = "Arquivo" + destino + ".txt";
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            String ExpressaoRegular = "[;|]";
            String linha;
            int estado = 0;
            int acao = 0;

            while ((linha = br.readLine()) != null) {
                String parte[] = linha.split(ExpressaoRegular);

                for (int i = 0; i < parte.length; i = i + 2) {
                    int est = Integer.parseInt(parte[i]);
                    double valor = Double.parseDouble(parte[i + 1]);
                    tabela[estado][acao].setEstado(est);
                    tabela[estado][acao].setValorAcao(valor);
                    acao++;
                }
                estado++;
                acao = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lerEstados() throws FileNotFoundException, IOException {
        try {
            FileReader fr = new FileReader(ESTADOS);
            BufferedReader br = new BufferedReader(fr);
            String ExpressaoRegular = "[;|]";
            String linha;
            int x = 0;
            while ((linha = br.readLine()) != null) {
                String parte[] = linha.split(ExpressaoRegular);
                estados[x] = new Estado(parte[0], x, 0);
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lerAcoes() throws FileNotFoundException, IOException {
        try {
            FileReader fr = new FileReader(ACOES);
            BufferedReader br = new BufferedReader(fr);
            String ExpressaoRegular = "[;|]";
            String linha;
            int estado = 0;
            int acao = 0;
            int proximo;

            while ((linha = br.readLine()) != null) {
                String parte[] = linha.split(ExpressaoRegular);
                for (int i = 0; i < parte.length; i++) {
                    proximo = retornarValorEstado(parte[i]);
                    tabela[estado][acao] = new Dado(proximo, 0);
                    acao++;
                }
                estado++;
                acao = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int retornarValorEstado(String nome) throws IOException {
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].getNome().equalsIgnoreCase(nome)) {
                return estados[i].getValor();
            }
        }
        return -1;
    }

    public static void mostrarEstados() throws IOException {
        for (int i = 0; i < estados.length; i++) {
            System.out.println("Estado " + estados[i].getNome() + ":  " + estados[i].getRecompensa());
        }
    }
}
