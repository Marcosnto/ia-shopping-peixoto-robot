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


public class Estado {
     public String nome;
    public  int valor ;
    public double recompensa ;
    
    Estado(String nome, int valor, double recompensa){   
        this.nome=nome;
        this.valor=valor;
        this.recompensa = recompensa;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(double recompensa) {
        this.recompensa = recompensa;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
}
