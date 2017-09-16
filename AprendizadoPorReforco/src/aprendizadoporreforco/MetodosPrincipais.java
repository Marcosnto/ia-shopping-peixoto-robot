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

public class MetodosPrincipais {

 
    //utilizada para saber a quantidade de estados 
    public static int TOTALESTADOS = 13;    
    //utilizada para saber a quantidade de estados 
    public static int TOTALACOES = 4;
    //utilizada para salvar os estados : nome, valor, e Recompensa.
    public static Estado estados[] = new Estado[TOTALESTADOS];
    
    //utilizada para salvar os valores das acoes de cada estado
    public static Dado tabela[][] = new Dado[TOTALESTADOS][TOTALACOES ];    
    /*as ações são respectivamente:  0 = Esquerda; 1= Direita; 2= Cima; 3=Baixo */

    //utilizada para decidir entre Usufruir ou Explorar
    public static ArrayList decisao = new ArrayList<Integer>();
    //utilizada para saber o Total de vezes que Houve de Exploração
    public static int totalExplorar = 0;
    //utilizada para saber o Total de vezes que Usufruiu
    public static int totalUsufruir = 0;
    //utilizada para saber o Total de vezes que o estado so tinha uma unica acao
    public static int unicaEscolha = 0;

    //método utilizado para executar os metodos necessarios para o aprendizado
    public static void aprender() throws IOException {
        lerEstados();
        lerAcoes();
        System.out.println("INICIALIZADO APRENDIZADO");
        for (int i = 0; i < TOTALESTADOS; i++) {
            atualizarRecompensa(i);
            aprendizado(i); //o objetivo  
            System.out.println(".");
        }
        System.out.println("FINALIZADO");
    }

    //método utilizado para mostrar o caminho de um ponto origem até um ponto de destino
    public static String caminho(int origem,int destino) throws IOException {
        //ler o arquivo correspondente ao ponto de destino
        lerArquivo(destino);
        String caminho = "";
        while (origem != destino) {
            int estadoAux = -1;
            double maior = -1;
            for (int i = 0; i < TOTALACOES; i++) {
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

    //método para zerar os valores das aoes
    public static void zerarTabela() {
        for (int l = 0; l < TOTALESTADOS; l++) {
            for (int c = 0; c < TOTALACOES ; c++) {
                tabela[l][c].setValorAcao(0);
            }
        }
    }

    //método para alocar o valor da recompensa do estado destino e atualizar para 0
    //o valor da recompensa do estado destino anterior
    public static void atualizarRecompensa(int destino) {
        estados[destino].setRecompensa(10);
        if (destino > 0) {
            estados[destino - 1].setRecompensa(0); //o estado anterior deixa de ter recompensa
        }
        zerarTabela(); //para limpar os dados adiquiridos anteriormente
    }

    //método que realiza o aprendizado de todos os pontos para um ponto de destino especifico
    //e chama ao final o método para gravar o resultado em um arquivo
    public static void aprendizado(int destino) throws IOException {
        totalUsufruir = 0;
        totalExplorar = 0;
        int x = 0;
        Random seleciona = new Random();
        int estadoAtual;
        while (x < 50000) { //enquanto eu não chegar o total de vezes ao destino            
            estadoAtual = seleciona.nextInt(TOTALESTADOS); //Escolhe o estado aleatoriamente
            while (estadoAtual != destino) {
                int proximoEstado = -1;          
                int acao;
                if (totalAcao(estadoAtual) == 1) { //verifica se pra chegar/sair desse estado se só tem uma acao
                    acao = retornarAcao(estadoAtual);
                    proximoEstado = tabela[estadoAtual][acao].getEstado();
                    unicaEscolha++;
                } else {
                    int decisao = escolha(); //escolher se vai usufruir ou explorar
                    if (decisao < 30) {  // se o valor for menor vai usufruir
                        acao = maiorValorAcao(estadoAtual);
                        proximoEstado = tabela[estadoAtual][acao].getEstado();
                        totalUsufruir++;
                    } else { // se for maior igual a 30 vai explorar
                        totalExplorar++;
                        acao = seleciona.nextInt(TOTALACOES ); //escolhe a ação aleatoriamente 
                        boolean ok = true;
                        while (ok) {
                            Dado d = tabela[estadoAtual][acao];
                            if (d.getEstado() != -1) { //se existe estado pra essa acao a partir do estado atual 
                                ok = false;
                                proximoEstado = d.getEstado();
                            } else {
                                acao = seleciona.nextInt(TOTALACOES ); //escolhe a ação aleatoriamente  
                            }
                        }
                    }
                }
                double recAt = estados[proximoEstado].getRecompensa() + (0.9 * (maiorValor(proximoEstado))); //Q-Learning                
                tabela[estadoAtual][acao].setValorAcao(recAt);
                estadoAtual = proximoEstado;
            }
            x++;
        }
        gravarArquivo(destino);
    }

    //método para a escolher qual a dcisao q será tomada: usufruir ou explorarar
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

    //método para retornar o maior valor dentre os valores das acoes de um determinado estado
    public static double maiorValor(int estado) {
        double maior = tabela[estado][0].valorAcao;
        for (int i = 1; i < TOTALACOES ;i++) {
            if (tabela[estado][i].getValorAcao() >= maior) {
                maior = tabela[estado][i].getValorAcao();
            }
        }
        return maior;
    }

    //método utilizado pra saber qual a única acao que um determinado estado apresenta
    public static int retornarAcao(int estado) {
        for (int i = 0; i < TOTALACOES ;i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                return i;
            }
        }
        return 0;
    }

    //método para saber o total de acoes de um estado, ou seja,
    //quantas acoes tenho a partir de um dado estado
    public static int totalAcao(int estado) {
        int total = 0;
        for (int i = 0; i < TOTALACOES; i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                total++;
            }
        }
        return total;
    }

    //método utilizado para retornar a acao que apresenta maior valor
    public static int maiorValorAcao(int estado) {
        int acao = -1;
        double maior = -1;
        for (int i = 0; i < TOTALACOES;i++) {
            if (tabela[estado][i].getEstado() != -1) { // se tem essa acao pro estado atual o proximo estado é diferente de -1
                if (tabela[estado][i].getValorAcao() > maior) {
                    maior = tabela[estado][i].getValorAcao();
                    acao = i;
                }
            }
        }
        return acao;
    }

    //método para exibir a tabela, uma matriz que apresenta os estados (linhas) 
    //e suas respectivas acoes juntamente qual o estado é direcionado com a mesma 
    //e o valor dela
    public static void mostrarTabela() {
        System.out.printf("%-10s%-10s%-10s%-10s", "Estado", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.printf("%-10s%-10s%-10s", "Acao", "Destino", "Valor ");
        System.out.println("");
        for (int i = 0; i < TOTALESTADOS; i++) {
            System.out.printf("%-10s", estados[i].getNome());
            for (int j = 0; j < TOTALACOES ; j++) {
                if (j == 0) {
                    System.out.printf("%-10s%-10s%-10s", "Esquerda", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 1) {
                    System.out.printf("%-10s%-10s%-10s", "Direita", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 2) {
                    System.out.printf("%-10s%-10s%-10s", "Cima", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                } else if (j == 3) {
                    System.out.printf("%-10s%-10s%-10s", "Baixo", tabela[i][j].getEstado(), String.format("%.5f", tabela[i][j].getValorAcao()));
                }
            }
            System.out.println("");
        }
    }

    //método que irá gravar os dados do aprendizado de todos os estados para um estado(destino) 
    //informacoes adquiridas em tabela
    public static void gravarArquivo(int estado) throws FileNotFoundException, IOException {
        String arquivo = "Arquivo" + estado + ".txt";
        File f = new File(arquivo);
        try {
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < TOTALESTADOS; i++) {
                for (int j = 0; j < TOTALACOES ; j++) {
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

    //método utilizado para ler o arquivo de um estado e passar as informacoes para a tabela
    //que será utilizada para encontrar o caminho de um estado qualquer para esse especifico(destino).
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

    //método para carregar o arquivo de estados que serão utilizados no aprendizado
    public static void lerEstados() throws FileNotFoundException, IOException {
        try {
            FileReader fr = new FileReader("Estados.txt");
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

    //método para carregar o arquivo de acoes especifico dos estados 
    //que serão utilizados no aprendizado
    public static void lerAcoes() throws FileNotFoundException, IOException {
        try {
            FileReader fr = new FileReader("Acoes.txt");
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

    //método para retornar valor do estado, o identificador dele
    public static int retornarValorEstado(String nome) throws IOException {
        for (int i = 0; i < TOTALESTADOS; i++) {
            if (estados[i].getNome().equalsIgnoreCase(nome)) {
                return estados[i].getValor();
            }
        }
        return -1;
    }

    //método utilizado para excolher o percurso
    public static void realizarVigilancia() throws IOException {
        lerEstados(); 
        ArrayList percorrer = new ArrayList<Integer>();
        for (int i = 0; i < estados.length; i++) {
            percorrer.add(i);
        }
        int ponto = TOTALESTADOS - 1;
        String percurso = "";
        Collections.shuffle(percorrer); //embaralha        
        //Escolhe o inicio : origem
        int origem = (int) percorrer.get(ponto);
        ponto--;
        //Escolhe o objetivo : destino
        int destino = (int) percorrer.get(ponto);
        ponto--;
        percurso = percurso + " " + caminho( origem , destino);  
        while (ponto !=-1) {
            origem = destino;
            destino = (int) percorrer.get(ponto);             
            Collections.shuffle(percorrer); //embaralha 
            ponto--;
            percurso = percurso + " " + caminho(origem, destino);
        }
        System.out.println("Percurso Gerado : " + percurso);
    }
}
