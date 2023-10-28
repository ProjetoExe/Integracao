package ProjectExe.Integracao.servicos.utilitarios;

public class Formatador {

    public static String formatarCPF(String cpf) {
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        return (cpfNumerico.length() != 11) ? "CPF inválido"
                : cpfNumerico.substring(0, 3) + "." +
                cpfNumerico.substring(3, 6) + "." +
                cpfNumerico.substring(6, 9) + "-" +
                cpfNumerico.substring(9);
    }

    public static String formatarCelular(String numero) {
        String numerico = numero.replaceAll("[^0-9]", "");
        return (numerico.length() == 11) ? "(" + numerico.substring(0, 2) + ")" +
                numerico.substring(2, 7) + "-" + numerico.substring(7)
                : (numerico.length() == 10) ? "(" + numerico.substring(0, 2) + ")" +
                numerico.substring(2, 6) + "-" + numerico.substring(6)
                : "Número inválido";
    }
}
