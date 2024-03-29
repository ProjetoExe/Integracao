package ProjectExe.Integracao.servicos.utilitarios;

public class Formatador {

    public static String formatarCPF(String cpf) {
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        return cpfNumerico.substring(0, 3) + "." +
                cpfNumerico.substring(3, 6) + "." +
                cpfNumerico.substring(6, 9) + "-" +
                cpfNumerico.substring(9);
    }

    public static String formatarCNPJ(String cnpj) {
        String cnpjNumerico = cnpj.replaceAll("[^0-9]", "");
        return cnpjNumerico.substring(0,2) + "." +
                cnpjNumerico.substring(2,5) + "." +
                cnpjNumerico.substring(5,8) + "/" +
                cnpjNumerico.substring(8,12) + "." +
                cnpjNumerico.substring(12);
    }

    public static String formatarCelular(String numero) {
        String numerico = numero.replaceAll("[^0-9]", "");
        return (numerico.length() == 10) ? "(" + numerico.substring(0, 2) + ")" +
                numerico.substring(2, 6) + "-" + numerico.substring(6)
                : "(" + numerico.substring(0, 2) + ")" +
                numerico.substring(2, 7) + "-" + numerico.substring(7);
    }

    public static String formatarCep(String cep) {
        String numerico = cep.replaceAll("[^0-9]", "");
        return numerico.substring(0, 5) + "-" +
                numerico.substring(6);
    }

    public static String formatarNCM(String ncm) {
        String numerico = ncm.replaceAll("[^0-9]", "");
        return numerico.substring(0, 4) + "." +
                numerico.substring(4, 6) + "." +
                numerico.substring(6);
    }
}
