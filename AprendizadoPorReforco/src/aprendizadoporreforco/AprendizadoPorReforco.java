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

public class AprendizadoPorReforco {

    public static Estado estados[] = new Estado[36];
    public static int acoes[] = new int[8];
    public static Dado tabela[][] = new Dado[36][8];
    public static ArrayList decisao = new ArrayList<Integer>();

    public static int TotalExplorar = 0;
    public static int TotalUsufruir = 0;

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 36; i++) {
            System.out.println("Estou em" + i);
            estadosAcoes(i);
            aprendizado(5000, i); //quantas vezes vai rodar e quem é o destino
        }
        //mostrarTabela();
        System.out.println("LIIIII");
        System.out.println("MOSTRANDO CAMINHO: " + caminho(5, 30));
        mostrarTabela();
    }

    public static String caminho(int origem, int destino) throws IOException {
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
            System.out.println("" + origem);
            caminho = caminho + " " + estados[origem].getNome() + "->";
            origem = estadoAux;
        }
        return caminho + " OBJETIVO" + destino;
    }

    public static void estadosAcoes(int destino) {
        int x;
        for (x = 0; x < 36; x++) {
            if (x == destino) { //se for meu estado destino eu dou uma recompensa de 10 para ele
                estados[x] = new Estado("S" + x, x, 10);
            } else {
                estados[x] = new Estado("S" + x, x, 0);
            }
        }
        acoes[0] = 0; //esquerda
        acoes[1] = 1; //direita
        acoes[2] = 2; //cima
        acoes[3] = 3; //baixo

        acoes[4] = 4; //LateralEsquerdaCima
        acoes[5] = 5; //LateralEsquerdaBaixo
        acoes[6] = 6; //LateralDireitaCima
        acoes[7] = 7; //LateralDireitaBaixo

        //for pras 4 primeira acoes
        for (int i = 0; i < 36; i++) {
            for (int j = 0; j < 4; j++) {
                boolean ok = true;

                if (j == 2 && i < 6) {//não tem pra cima  
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }

                if (j == 3 && i > 29) { //não tem pra baixo
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }

                if (j == 0 && (i % 6) == 0) { //não tem pra esquerda
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }

                if (j == 1 && (i % 6) == 5) {//não tem pra direita
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }
                if (ok) {
                    if (j == 0) { //esquerda
                        tabela[i][j] = new Dado((i - 1), 0);
                    } else if (j == 1) { //direita
                        tabela[i][j] = new Dado((i + 1), 0);
                    } else if (j == 2) {//cima
                        tabela[i][j] = new Dado((i - 6), 0);
                    } else {//baixo
                        tabela[i][j] = new Dado((i + 6), 0);
                    }
                }
            }
        }

        //for para as outras 4 acoes
        for (int i = 0; i < 36; i++) {
            for (int j = 4; j < 8; j++) {
                boolean ok = true;

                if ((j == 4 || j == 6) && i < 6) {//não tem  pra lateral esquerda/direita pra cima  
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }
                if ((j == 5 || j == 7) && i > 29) { //não tem  pra lateral esquerda/direita pra baixo
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }
                if ((j == 4 || j == 5) && (i % 6) == 0) { //não tem para lateral esquerda cima/baixo
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }

                if ((j == 6 || j == 7) && (i % 6) == 5) { //não tem para lateral direita cima/baixo
                    tabela[i][j] = new Dado(-1, 0);
                    ok = false;
                }

                if (ok) {
                    if (j == 4) { // lateral esquerda cima
                        tabela[i][j] = new Dado((i - 7), 0);
                    } else if (j == 5) { // lateral esquerda baixo
                        tabela[i][j] = new Dado((i + 5), 0);
                    } else if (j == 6) {//cima
                        tabela[i][j] = new Dado((i - 5), 0);
                    } else {//baixo
                        tabela[i][j] = new Dado((i + 7), 0);
                    }
                }
            }
        }
    }

    public static void aprendizado(int totalVezes, int destino) throws IOException {
        TotalUsufruir = 0;
        TotalExplorar = 0;
        int x = 0;
        Random seleciona = new Random();
        int estadoAtual;
        while (x < totalVezes) { //para parar quando chegar ao fim no totalVezes
            do {
                estadoAtual = seleciona.nextInt(36); //Escolhe o estado aleatoriamente
                int proximoEstado = -1; //os pra inicializar              
                int decisao = escolha(); //escolher se vai usufruir ou explorar
                int acao;
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
                double recAt = estados[proximoEstado].getRecompensa() + (0.9 * (maiorValor(proximoEstado))); //Q-Learning                
                tabela[estadoAtual][acao].setValorAcao(recAt);
                estadoAtual = proximoEstado;
            } while (estadoAtual != destino); //estado final 35
            estadoAtual = seleciona.nextInt(36); //escolhe outro ponto de partida aleatorio entre os 3 estados
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
                System.out.printf("%-10s%-10s%-10s", j, tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
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
}
