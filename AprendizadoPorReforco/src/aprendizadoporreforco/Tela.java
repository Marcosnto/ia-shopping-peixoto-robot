package aprendizadoporreforco;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

public class Tela extends JFrame implements ActionListener{
    JButton iniciar = new JButton("Iniciar");
    JButton parar = new JButton ("Parar");
    JButton mostrar = new JButton ("Mostrar Aprendizagem");
    static JLabel M1;
    
    public static void subir(int yInicial, int yFinal) {
        Thread t1 = new Thread() {
            public void run() {
                for (int i = yInicial; i > yFinal; i--) {
                    M1.setBounds(M1.getX(), i, 20, 19);
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
                for (int i = yInicial; i < yFinal; i++) {
                    M1.setBounds(M1.getX(), i, 20, 19);
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
                for (int i = xInicial; i < xFinal; i++) {
                    M1.setBounds(i, M1.getY(), 20, 19);
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
                for (int i = xInicial; i > xFinal; i--) {
                    M1.setBounds(i, M1.getY(), 20, 19);
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
        Thread T1 = new Thread() {
            public void run() {
                for (int x = 0; rota.length > x; x++) {
                    try {
                        //MOVIMENTAÇÃO (1)
                        if (rota[x][0] == 1 && rota[x][1] == 2) {
                            descer(3, 125); 
                            Thread.sleep(1000);
                        } else if (rota[x][0] == 1 && rota[x][1] == 16){
                            direita(1,209);
                            Thread.sleep(1000);  
                        //MOVIMENTAÇÃO (2)    
                        }else if (rota[x][0] == 2 && rota[x][1] == 1){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 2 && rota[x][1] == 3){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 2 && rota[x][1] == 4){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (3)
                        }else if (rota[x][0] == 3 && rota[x][1] == 2){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 3 && rota[x][1] == 6){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (4)
                        }else if (rota[x][0] == 4 && rota[x][1] == 2){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 4 && rota[x][1] == 5){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (5)
                        }else if (rota[x][0] == 5 && rota[x][1] == 4){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 5 && rota[x][1] == 6){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (6)
                        }else if (rota[x][0] == 6 && rota[x][1] == 5){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 6 && rota[x][1] == 3){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 6 && rota[x][1] == 7){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 6 && rota[x][1] == 9){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (7)
                        }else if (rota[x][0] == 7 && rota[x][1] == 6){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 7 && rota[x][1] == 8){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (8)
                        }else if (rota[x][0] == 8 && rota[x][1] == 7){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 8 && rota[x][1] == 9){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 8 && rota[x][1] == 10){
                            direita(1,209);
                            Thread.sleep(1000);
                         }else if (rota[x][0] == 8 && rota[x][1] == 21){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (9)
                        }else if (rota[x][0] == 9 && rota[x][1] == 6){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 9 && rota[x][1] == 8){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 9 && rota[x][1] == 12){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (10)
                        }else if (rota[x][0] == 10 && rota[x][1] == 8){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 10 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 10 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 10 && rota[x][1] == 21){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (11)
                        }else if (rota[x][0] == 11 && rota[x][1] == 10){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 11 && rota[x][1] == 12){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 11 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 11 && rota[x][1] == 38){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 11 && rota[x][1] == 39){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (12)
                        }else if (rota[x][0] == 12 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 12 && rota[x][1] == 13){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 12 && rota[x][1] == 39){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (13)
                        }else if (rota[x][0] == 13 && rota[x][1] == 12){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 13 && rota[x][1] == 14){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 13 && rota[x][1] == 16){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (14)
                        }else if (rota[x][0] == 14 && rota[x][1] == 13){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 14 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (15)
                        }else if (rota[x][0] == 15 && rota[x][1] == 14){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 15 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 15 && rota[x][1] == 17){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 15 && rota[x][1] == 39){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (16)
                        }else if (rota[x][0] == 16 && rota[x][1] == 1){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 16 && rota[x][1] == 13){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (17)
                        }else if (rota[x][0] == 17 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 17 && rota[x][1] == 18){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 17 && rota[x][1] == 40){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (18)
                        }else if (rota[x][0] == 18 && rota[x][1] == 17){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 18 && rota[x][1] == 19){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 18 && rota[x][1] == 20){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 18 && rota[x][1] == 40){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (19)
                        }else if (rota[x][0] == 19 && rota[x][1] == 18){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 19 && rota[x][1] == 32){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 19 && rota[x][1] == 23){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 19 && rota[x][1] == 41){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (20)
                        }else if (rota[x][0] == 20 && rota[x][1] == 18){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 20 && rota[x][1] == 21){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 20 && rota[x][1] == 10){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (21)
                        }else if (rota[x][0] == 21 && rota[x][1] == 8){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 21 && rota[x][1] == 20){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 21 && rota[x][1] == 22){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 21 && rota[x][1] == 41){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (22)
                        }else if (rota[x][0] == 22 && rota[x][1] == 21){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 22 && rota[x][1] == 23){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 22 && rota[x][1] == 24){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (23)
                        }else if (rota[x][0] == 23 && rota[x][1] == 19){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 23 && rota[x][1] == 22){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 23 && rota[x][1] == 41){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (24)
                        }else if (rota[x][0] == 24 && rota[x][1] == 22){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 24 && rota[x][1] == 25){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 24 && rota[x][1] == 26){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (25)
                        }else if (rota[x][0] == 25 && rota[x][1] == 24){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 25 && rota[x][1] == 27){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (26)
                        }else if (rota[x][0] == 26 && rota[x][1] == 24){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 26 && rota[x][1] == 27){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (27)
                        }else if (rota[x][0] == 27 && rota[x][1] == 25){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 27 && rota[x][1] == 26){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 27 && rota[x][1] == 29){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (28)
                        }else if (rota[x][0] == 28 && rota[x][1] == 29){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 28 && rota[x][1] == 31){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (29)
                        }else if (rota[x][0] == 29 && rota[x][1] == 27){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 29 && rota[x][1] == 28){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 29 && rota[x][1] == 30){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (30)
                        }else if (rota[x][0] == 30 && rota[x][1] == 29){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 30 && rota[x][1] == 31){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (31)
                        }else if (rota[x][0] == 31 && rota[x][1] == 30){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 31 && rota[x][1] == 28){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (32)
                        }else if (rota[x][0] == 32 && rota[x][1] == 19){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 32 && rota[x][1] == 33){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 32 && rota[x][1] == 37){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (33)
                        }else if (rota[x][0] == 33 && rota[x][1] == 32){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 33 && rota[x][1] == 34){
                            direita(1,209);
                            Thread.sleep(1000);   
                        //MOVIMENTAÇÃO (34)
                        }else if (rota[x][0] == 34 && rota[x][1] == 33){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 34 && rota[x][1] == 35){
                            direita(1,209);
                            Thread.sleep(1000); 
                        }else if (rota[x][0] == 34 && rota[x][1] == 36){
                            direita(1,209);
                            Thread.sleep(1000);
                        //MOVIMENTAÇÃO (35)
                        }else if (rota[x][0] == 35 && rota[x][1] == 34){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (36)
                        }else if (rota[x][0] == 36 && rota[x][1] == 34){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 36 && rota[x][1] == 37){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (37)
                        }else if (rota[x][0] == 37 && rota[x][1] == 36){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 37 && rota[x][1] == 32){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (38)
                        }else if (rota[x][0] == 38 && rota[x][1] == 40){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 38 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (39)
                        }else if (rota[x][0] == 39 && rota[x][1] == 15){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 39 && rota[x][1] == 11){
                            direita(1,209);
                            Thread.sleep(1000); 
                        }else if (rota[x][0] == 39 && rota[x][1] == 12){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (40)
                        }else if (rota[x][0] == 40 && rota[x][1] == 17){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 40 && rota[x][1] == 38){
                            direita(1,209);
                            Thread.sleep(1000); 
                        }else if (rota[x][0] == 40 && rota[x][1] == 18){
                            direita(1,209);
                            Thread.sleep(1000); 
                        //MOVIMENTAÇÃO (41)
                        }else if (rota[x][0] == 41 && rota[x][1] == 19){
                            direita(1,209);
                            Thread.sleep(1000);
                        }else if (rota[x][0] == 41 && rota[x][1] == 23){
                            direita(1,209);
                            Thread.sleep(1000); 
                        }else if (rota[x][0] == 41 && rota[x][1] == 21){
                            direita(1,209);
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
     
    public Tela() {
        setTitle("RobôShopping");
        setSize(625, 682);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        URL url = this.getClass().getResource("");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        
        setContentPane(new JLabel(new ImageIcon(ClassLoader.getSystemResource("Imagens/Mapa2.png"))));
        setLayout(null);
        
        M1 = new JLabel(new ImageIcon(ClassLoader.getSystemResource("Imagens/Quadrado.png")));
        
        setVisible(true);
        add(M1);
        add(iniciar).setBounds(420, 20, 165, 30);
        add(parar).setBounds(420, 55, 165, 30);
        add(mostrar).setBounds(420, 90, 165, 30);
        
        iniciar.addActionListener(this);
    }
    
    public static void main(String[] args) {
        Tela tela = new Tela();
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciar){
            M1.setBounds(167,218,20,19);
        }
    }
    
    
    
}
