package ProjectExe.Integracao.servicos.utilitarios;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.ProdutoGrade;
import ProjectExe.Integracao.repositorios.ProdutoGradeRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Arquivos {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private ProdutoGradeRepositorio produtoGradeRepositorio;

    DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] exportarProdutosParaExcel() {
        List<Produto> produtos = produtoRepositorio.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Produtos");
            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Código");
            headerRow.createCell(1).setCellValue("Nome");
            headerRow.createCell(2).setCellValue("Referência");
            headerRow.createCell(3).setCellValue("Data-Cadastro");
            headerRow.createCell(4).setCellValue("Ativo");
            headerRow.createCell(5).setCellValue("Tamanho");
            headerRow.createCell(6).setCellValue("Preço");
            headerRow.createCell(7).setCellValue("Preço-Promo");
            headerRow.createCell(8).setCellValue("EAN");
            headerRow.createCell(9).setCellValue("Estoque");
            headerRow.createCell(10).setCellValue("Marca");
            headerRow.createCell(11).setCellValue("Categorias");

            for (Produto produto : produtos) {
                List<ProdutoGrade> produtoGrades = produtoGradeRepositorio.buscarPorProdutoId(produto.getProdutoId());
                if (produtoGrades.isEmpty()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(produto.getProdutoId());
                    row.createCell(1).setCellValue(produto.getNome());
                    row.createCell(2).setCellValue(produto.getReferencia());
                    LocalDate localDate = produto.getDataCadastro().atZone(ZoneId.systemDefault()).toLocalDate();
                    row.createCell(3).setCellValue(localDate.format(data));
                    row.createCell(4).setCellValue(String.valueOf(produto.getOptAtivo()));
                    row.createCell(5).setCellValue("U"); // Defina o tamanho como "U" ou como desejar
                    row.createCell(6).setCellValue(produto.getPreco().doubleValue());
                    row.createCell(7).setCellValue(produto.getPrecoProm().doubleValue());
                    row.createCell(8).setCellValue(String.valueOf(produto.getEan()));
                    row.createCell(9).setCellValue(produto.getEstoqueTotal());
                    row.createCell(10).setCellValue(produto.getMarca().getNome());
                    row.createCell(11).setCellValue(produto.getCategorias().toString());
                } else {
                    for (ProdutoGrade produtoGrade : produtoGrades) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(produto.getProdutoId());
                        row.createCell(1).setCellValue(produto.getNome());
                        row.createCell(2).setCellValue(produto.getReferencia());
                        LocalDate localDate = produto.getDataCadastro().atZone(ZoneId.systemDefault()).toLocalDate();
                        row.createCell(3).setCellValue(localDate.format(data));
                        row.createCell(4).setCellValue(String.valueOf(produto.getOptAtivo()));
                        row.createCell(5).setCellValue(produtoGrade.getTamanho());
                        row.createCell(6).setCellValue(produtoGrade.getPreco().doubleValue());
                        row.createCell(7).setCellValue(produtoGrade.getPrecoPromocional().doubleValue());
                        row.createCell(8).setCellValue(String.valueOf(produtoGrade.getEan()));
                        row.createCell(9).setCellValue(produtoGrade.getQuantidadeEstoque());
                        row.createCell(10).setCellValue(produto.getMarca().getNome());
                        row.createCell(11).setCellValue(produto.getCategorias().toString());
                    }
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}