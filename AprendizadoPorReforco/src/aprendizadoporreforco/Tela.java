package aprendizadoporreforco;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Tela extends JFrame implements ActionListener  {

    JButton iniciar = new JButton("Iniciar");
    JButton parar = new JButton("Parar");
    JButton mostrar = new JButton("Mostrar Aprendizagem");
    JButton aprender = new JButton("Realizar Aprendizagem");
    static JLabel M1;
    MetodosPrincipais metodos = new MetodosPrincipais();
    JFrame log = new JFrame();
    JTextArea showLog = new JTextArea();
    JScrollPane saidaTXT = new JScrollPane();
    public static Thread T1 = new Thread();
    
    public Tela() {
        setTitle("RobôPeixoto");
        setSize(625, 682);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        URL url = this.getClass().getResource("");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);

        setContentPane(new JLabel(new ImageIcon(ClassLoader.getSystemResource("Imagens/ImagemF.png"))));
        setLayout(null);

        M1 = new JLabel(new ImageIcon(ClassLoader.getSystemResource("Imagens/Quadrado.png")));

        setVisible(true);
        add(M1);
        add(iniciar).setBounds(420, 20, 165, 30);
        add(parar).setBounds(420, 55, 165, 30);
        add(aprender).setBounds(420, 90, 165, 30);
        add(mostrar).setBounds(420, 125, 165, 30);
        
        log.setSize(625, 682);
        showLog.setEditable(false);
        log.add(saidaTXT);
        saidaTXT.setViewportView(showLog);        
        log.setResizable(false);

        iniciar.addActionListener(this);
        parar.addActionListener(this);
        aprender.addActionListener(this);
        mostrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciar) {
            try {
                FileReader fr = new FileReader("Caminho.txt");
                BufferedReader br = new BufferedReader(fr);
                String linha;
                int[][] matriz = new int[500][2];
                int L = 0;

                while ((linha = br.readLine()) != null) {
                    String parte[] = linha.split(";");
                    matriz[L][0] = Integer.parseInt(parte[0]);
                    matriz[L][1] = Integer.parseInt(parte[1]);
                    L++;
                }
                matriz[L][0] = -1;
                matriz[L][1] = -1;
                fr.close();
                movimentacao(matriz);
            } catch (IOException f) {
                f.printStackTrace();
            }
        }

        if (e.getSource() == aprender) {
            try {              
                JOptionPane.showMessageDialog(null, "Aprendizado inicializado. Por favor, aguarde.!");
                metodos.aprender();                
                JOptionPane.showMessageDialog(null, "Aprendizado concluido com sucesso!");
                metodos.gerarCaminho();
            } catch (IOException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == mostrar) {
            log.setVisible(true);
            try {
                JOptionPane.showMessageDialog(null, "Lendo arquivo, por favor, aguarde.");
                FileReader fr = new FileReader("Log.txt");
                BufferedReader br = new BufferedReader(fr);
                String linha;
                while ((linha = br.readLine()) != null) {
                    showLog.setText(showLog.getText() + linha + "\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == parar) {
            T1.stop();
            setVisible(false);
            Tela.main(new String[0]);
            JOptionPane.showMessageDialog(null, "Operação cancelada." );
                    
        }
    }
    
    public static void subir(int yInicial, int yFinal) {
        Thread t1 = new Thread() {
            public void run() {
                for (int i = yInicial; i >= yFinal; i--) {
                    M1.setBounds(M1.getX(), i, 10, 10);
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
        t1.start();
    }

    public static void descer(int yInicial, int yFinal) {
        Thread t1 = new Thread() {
            public void run() {
                for (int i = yInicial; i <= yFinal; i++) {
                    M1.setBounds(M1.getX(), i, 10, 10);
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
        t1.start();
    }

    public static void direita(int xInicial, int xFinal) {

        Thread t1 = new Thread() {
            public void run() {
                for (int i = xInicial; i <= xFinal; i++) {
                    M1.setBounds(i, M1.getY(), 10, 10);
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
        t1.start();
    }

    public static void esquerda(int xInicial, int xFinal) {

        Thread t1 = new Thread() {
            public void run() {
                for (int i = xInicial; i >= xFinal; i--) {
                    M1.setBounds(i, M1.getY(), 10, 10);
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
        t1.start();
    }  
   
    public static void movimentacao(int[][] rota) {
        T1 = new Thread() {
            public void run() {
                for (int x = 0; rota.length > x; x++) {
                    try {
                        //MOVIMENTAÇÃO (1)
                        if (rota[x][0] == 1 && rota[x][1] == 2) {
                            subir(613, 474);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == -1 && rota[x][1] == -1) {
                            break;
                        } else if (rota[x][0] == 1 && rota[x][1] == 16) {
                            direita(120, 372);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (2)    
                        } else if (rota[x][0] == 2 && rota[x][1] == 1) {
                            descer(474, 613);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 2 && rota[x][1] == 3) {
                            direita(100, 137);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 2 && rota[x][1] == 4) {
                            esquerda(120, 100);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (3)
                        } else if (rota[x][0] == 3 && rota[x][1] == 2) {
                            esquerda(137, 120);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 3 && rota[x][1] == 6) {
                            subir(474, 373);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (4)
                        } else if (rota[x][0] == 4 && rota[x][1] == 2) {
                            direita(100, 120);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 4 && rota[x][1] == 5) {
                            subir(474, 370);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (5)
                        } else if (rota[x][0] == 5 && rota[x][1] == 4) {
                            descer(370, 474);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 5 && rota[x][1] == 6) {
                            direita(100, 142);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (6)
                        } else if (rota[x][0] == 6 && rota[x][1] == 5) {
                            esquerda(142, 100);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 6 && rota[x][1] == 3) {
                            descer(373, 474);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 6 && rota[x][1] == 7) {
                            subir(373, 344);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 6 && rota[x][1] == 9) {
                            direita(142, 225);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (7)
                        } else if (rota[x][0] == 7 && rota[x][1] == 6) {
                            descer(344, 373);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 7 && rota[x][1] == 8) {
                            direita(142, 225);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (8)
                        } else if (rota[x][0] == 8 && rota[x][1] == 7) {
                            esquerda(225, 142);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 8 && rota[x][1] == 9) {
                            descer(344, 371);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 8 && rota[x][1] == 10) {
                            direita(225, 263);
                            Thread.sleep(100);
                        } else if (rota[x][0] == 8 && rota[x][1] == 21) {
                            subir(344, 218);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (9)
                        } else if (rota[x][0] == 9 && rota[x][1] == 6) {
                            esquerda(225, 142);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 9 && rota[x][1] == 8) {
                            subir(371, 344);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 9 && rota[x][1] == 12) {
                            direita(225, 372);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (10)
                        } else if (rota[x][0] == 10 && rota[x][1] == 8) {
                            esquerda(263, 225);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 10 && rota[x][1] == 11) {
                            direita(263, 372);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 10 && rota[x][1] == 20) {
                            subir(344, 218);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (11)
                        } else if (rota[x][0] == 11 && rota[x][1] == 10) {
                            esquerda(372, 263);
                            Thread.sleep(1000);
                        }/*else if (rota[x][0] == 11 && rota[x][1] == 12){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 11 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        }*/ else if (rota[x][0] == 11 && rota[x][1] == 38) {
                            direita(372, 496);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 11 && rota[x][1] == 39) {
                            descer(344, 358);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (12)
                        } else if (rota[x][0] == 12 && rota[x][1] == 9) {
                            esquerda(372, 225);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 12 && rota[x][1] == 13) {
                            descer(370, 450);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 12 && rota[x][1] == 39) {
                            subir(370, 358);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (13)
                        } else if (rota[x][0] == 13 && rota[x][1] == 12) {
                            subir(450, 370);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 13 && rota[x][1] == 14) {
                            direita(372, 520);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 13 && rota[x][1] == 16) {
                            descer(450, 611);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (14)
                        } else if (rota[x][0] == 14 && rota[x][1] == 13) {
                            esquerda(520, 372);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 14 && rota[x][1] == 15) {
                            subir(450, 358);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (15)
                        } else if (rota[x][0] == 15 && rota[x][1] == 14) {
                            descer(358, 450);
                            Thread.sleep(1000);
                        }/*else if (rota[x][0] == 15 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000);
                        }*/ else if (rota[x][0] == 15 && rota[x][1] == 17) {
                            subir(358, 218);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 15 && rota[x][1] == 38) {
                            esquerda(520, 496);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (16)
                        } else if (rota[x][0] == 16 && rota[x][1] == 1) {
                            esquerda(372, 120);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 16 && rota[x][1] == 13) {
                            subir(611, 450);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (17)
                        } else if (rota[x][0] == 17 && rota[x][1] == 15) {
                            descer(218, 358);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 17 && rota[x][1] == 40) {
                            esquerda(518, 496);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (18)
                        }else if (rota[x][0] == 18 && rota[x][1] == 19) {
                            subir(218, 198);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 18 && rota[x][1] == 20) {
                            esquerda(314, 263);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 18 && rota[x][1] == 40) {
                            direita(314, 496);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (19)
                        } else if (rota[x][0] == 19 && rota[x][1] == 18) {
                            descer(198, 218);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 19 && rota[x][1] == 32) {
                            subir(198, 143);
                            Thread.sleep(1000);
                        }/*else if (rota[x][0] == 19 && rota[x][1] == 23){
                            direita(1,209);
                            Thread.sleep(1000);
                        }*/ else if (rota[x][0] == 19 && rota[x][1] == 41) {
                            esquerda(314, 225);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (20)
                        } else if (rota[x][0] == 20 && rota[x][1] == 18) {
                            direita(263, 314);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 20 && rota[x][1] == 21) {
                            esquerda(263, 225);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 20 && rota[x][1] == 10) {
                            descer(218, 344);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (21)
                        } else if (rota[x][0] == 21 && rota[x][1] == 8) {
                            descer(218, 344);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 21 && rota[x][1] == 20) {
                            esquerda(225, 263);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 21 && rota[x][1] == 22) {
                            esquerda(225, 165);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 21 && rota[x][1] == 41) {
                            subir(218, 198);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (22)
                        } else if (rota[x][0] == 22 && rota[x][1] == 21) {
                            direita(165, 225);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 22 && rota[x][1] == 23) {
                            subir(217, 198);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 22 && rota[x][1] == 24) {
                            esquerda(165, 122);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (23)
                        }/*else if (rota[x][0] == 23 && rota[x][1] == 19){
                            direita(1,209);
                            Thread.sleep(1000);
                        }*/ else if (rota[x][0] == 23 && rota[x][1] == 22) {
                            descer(198, 217);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 23 && rota[x][1] == 41) {
                            esquerda(165, 225);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (24)
                        } else if (rota[x][0] == 24 && rota[x][1] == 22) {
                            esquerda(122, 165);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 24 && rota[x][1] == 25) {
                            subir(210, 192);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 24 && rota[x][1] == 26) {
                            esquerda(122, 82);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (25)
                        } else if (rota[x][0] == 25 && rota[x][1] == 24) {
                            descer(192, 210);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 25 && rota[x][1] == 27) {
                            esquerda(122, 82);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (26)
                        } else if (rota[x][0] == 26 && rota[x][1] == 24) {
                            direita(82, 122);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 26 && rota[x][1] == 28) {
                            subir(206, 204);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 26 && rota[x][1] == 27) {
                            subir(206, 192);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (27)
                        } else if (rota[x][0] == 27 && rota[x][1] == 25) {
                            direita(82, 122);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 27 && rota[x][1] == 26) {
                            descer(192, 206);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 27 && rota[x][1] == 29) {
                            esquerda(82, 50);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (28)
                        } else if (rota[x][0] == 28 && rota[x][1] == 29) {
                            subir(204, 185);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 28 && rota[x][1] == 26) {
                            direita(50, 82);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 28 && rota[x][1] == 31) {
                            esquerda(50, 1);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (29)
                        } else if (rota[x][0] == 29 && rota[x][1] == 27) {
                            direita(50, 82);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 29 && rota[x][1] == 28) {
                            descer(185, 204);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 29 && rota[x][1] == 30) {
                            esquerda(50, 1);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (30)
                        } else if (rota[x][0] == 30 && rota[x][1] == 29) {
                            direita(1, 50);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 30 && rota[x][1] == 31) {
                            descer(185, 204);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (31)
                        } else if (rota[x][0] == 31 && rota[x][1] == 30) {
                            subir(204, 185);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 31 && rota[x][1] == 28) {
                            direita(1, 204);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (32)
                        } else if (rota[x][0] == 32 && rota[x][1] == 19) {
                            descer(143, 198);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 32 && rota[x][1] == 33) {
                            esquerda(314, 175);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 32 && rota[x][1] == 37) {
                            subir(143, 31);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (33)
                        } else if (rota[x][0] == 33 && rota[x][1] == 32) {
                            direita(175, 314);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 33 && rota[x][1] == 34) {
                            subir(143, 55);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (34)
                        } else if (rota[x][0] == 34 && rota[x][1] == 33) {
                            descer(55, 143);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 34 && rota[x][1] == 35) {
                            esquerda(175, 120);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 34 && rota[x][1] == 36) {
                            subir(55, 31);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (35)
                        } else if (rota[x][0] == 35 && rota[x][1] == 34) {
                            direita(120, 175);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (36)
                        } else if (rota[x][0] == 36 && rota[x][1] == 34) {
                            descer(31, 55);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 36 && rota[x][1] == 37) {
                            direita(175, 313);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (37)
                        } else if (rota[x][0] == 37 && rota[x][1] == 36) {
                            esquerda(313, 175);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 37 && rota[x][1] == 32) {
                            descer(31, 143);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (38)
                        } else if (rota[x][0] == 38 && rota[x][1] == 40) {
                            subir(344, 218);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 38 && rota[x][1] == 15) {
                            direita(496, 520);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 38 && rota[x][1] == 11) {
                            esquerda(496, 372);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (39)
                        }/*else if (rota[x][0] == 39 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        }*/ else if (rota[x][0] == 39 && rota[x][1] == 11) {
                            subir(358, 344);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 39 && rota[x][1] == 12) {
                            descer(358, 370);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (40)
                        } else if (rota[x][0] == 40 && rota[x][1] == 17) {
                            direita(496, 518);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 40 && rota[x][1] == 38) {
                            descer(218, 344);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 40 && rota[x][1] == 18) {
                            esquerda(496, 314);
                            Thread.sleep(1000);
                            //MOVIMENTAÇÃO (41)
                        } else if (rota[x][0] == 41 && rota[x][1] == 19) {
                            direita(225, 314);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 41 && rota[x][1] == 23) {
                            esquerda(225, 165);
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 41 && rota[x][1] == 21) {
                            descer(198, 218);
                            Thread.sleep(1000);
                        }
                        Thread.sleep(1300);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };
        T1.start();
    }

    public static void main(String[] args) {
        Tela tela = new Tela();
        M1.setBounds(120, 613, 20, 19);
    }
}
