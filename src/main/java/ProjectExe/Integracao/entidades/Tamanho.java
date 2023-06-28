//package ProjectExe.Integracao.entidades;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//import java.io.Serializable;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "tamanho")
//public class Tamanho implements Serializable {
//    private static final long SerialVersionUID = 1L;
//
//    private Set<String> tamanhosLetras;
//    private Set<Integer> tamanhosNumeros;
//
//    public Tamanho(){
//        tamanhosLetras = new HashSet<>(Arrays.asList("U", "PP", "P", "M", "G", "GG"));
//        tamanhosNumeros = new HashSet<>(Arrays.asList(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44));
//    }
//
//    public Set<String> getTamanhosLetras() {
//        return tamanhosLetras;
//    }
//
//    public Set<Integer> getTamanhosNumeros() {
//        return tamanhosNumeros;
//    }
//}
