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
            headerRow.createCell(6).setCellValue("Preço-Custo");
            headerRow.createCell(7).setCellValue("Preço-Venda");
            headerRow.createCell(8).setCellValue("Preço-Promo");
            headerRow.createCell(9).setCellValue("EAN");
            headerRow.createCell(10).setCellValue("Estoque");
            headerRow.createCell(11).setCellValue("Marca");
            headerRow.createCell(12).setCellValue("Modelo");
            headerRow.createCell(13).setCellValue("Categoria");
            headerRow.createCell(14).setCellValue("SubCategorias");

            for (Produto produto : produtos) {
                List<ProdutoGrade> produtoGrades = produtoGradeRepositorio.buscarPorProdutoId(produto.getProdutoId());
                if (produtoGrades.isEmpty()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(produto.getProdutoId());
                    row.createCell(1).setCellValue(produto.getNomeProd());
                    row.createCell(2).setCellValue(produto.getReferencia() != null ? produto.getReferencia() : "");
                    LocalDate localDate = produto.getDataCadastro().atZone(ZoneId.systemDefault()).toLocalDate();
                    row.createCell(3).setCellValue(localDate != null ? localDate.format(data) : "");
                    row.createCell(4).setCellValue(produto.getOptAtivo());
                    row.createCell(6).setCellValue(produto.getPrecoCusto() != null ? produto.getPrecoCusto().doubleValue() : 0);
                    row.createCell(7).setCellValue(produto.getPrecoVenda() != null ? produto.getPrecoVenda().doubleValue() : 0);
                    row.createCell(8).setCellValue(produto.getPrecoProm() != null ? produto.getPrecoProm().doubleValue() : 0);
                    row.createCell(9).setCellValue(produto.getEan() != null ? String.valueOf(produto.getEan()) : "");
                    row.createCell(10).setCellValue(produto.getQtdEstoque());
                    row.createCell(11).setCellValue(produto.getMarca().getNomeMarca());
                    row.createCell(12).setCellValue(produto.getModelo() != null ? produto.getModelo() : "");
                    row.createCell(13).setCellValue(produto.getCategoria().getNomeCat());
                    row.createCell(14).setCellValue(produto.getSubCategorias().toString());
                } else {
                    for (ProdutoGrade produtoGrade : produtoGrades) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(produto.getProdutoId());
                        row.createCell(1).setCellValue(produto.getNomeProd());
                        row.createCell(2).setCellValue(produto.getReferencia() != null ? produto.getReferencia() : "");
                        LocalDate localDate = produto.getDataCadastro().atZone(ZoneId.systemDefault()).toLocalDate();
                        row.createCell(3).setCellValue(localDate != null ? localDate.format(data) : "");
                        row.createCell(4).setCellValue(produto.getOptAtivo());
                        row.createCell(5).setCellValue(produtoGrade.getVariacao());
                        row.createCell(5).setCellValue(produtoGrade.getVariacaoDupla());
                        row.createCell(6).setCellValue(produtoGrade.getPrecoCusto() != null ? produtoGrade.getPrecoCusto().doubleValue() : 0);
                        row.createCell(7).setCellValue(produtoGrade.getPrecoVenda() != null ? produtoGrade.getPrecoVenda().doubleValue() : 0);
                        row.createCell(8).setCellValue(produtoGrade.getPrecoProm() != null ? produtoGrade.getPrecoProm().doubleValue() : 0);
                        row.createCell(9).setCellValue(produtoGrade.getEan() != null ? String.valueOf(produtoGrade.getEan()) : "");
                        row.createCell(10).setCellValue(produtoGrade.getQtdEstoque());
                        row.createCell(11).setCellValue(produto.getMarca().getNomeMarca());
                        row.createCell(12).setCellValue(produto.getModelo() != null ? produto.getModelo() : "");
                        row.createCell(13).setCellValue(produto.getCategoria().getNomeCat());
                        row.createCell(14).setCellValue(produto.getSubCategorias().toString());
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