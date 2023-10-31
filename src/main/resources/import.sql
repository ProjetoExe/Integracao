INSERT INTO lojas (razao_Social, nome_Fantasia, cnpj, inscricao_Estadual, email, celular, telefone, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('PROJETO EXE LTDA', 'PROJETO EXE', '20214754000151', '529971505286', 'projeto.exe123@gmail.com', '16 99723-4150', '16 9723-4150', '14804-072','Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO categoria (nome) VALUES ('Hardware');
INSERT INTO categoria (nome) VALUES ('Perifericos');
INSERT INTO categoria (nome) VALUES ('Computadores');
INSERT INTO categoria (nome) VALUES ('Games');
INSERT INTO categoria (nome) VALUES ('Monitor');

INSERT INTO marca (nome) VALUES ('GALAX');
INSERT INTO marca (nome) VALUES ('LOGITECH');
INSERT INTO marca (nome) VALUES ('ASUS');
INSERT INTO marca (nome) VALUES ('GIGABYTE');
INSERT INTO marca (nome) VALUES ('ZOTAC');
INSERT INTO marca (nome) VALUES ('KINGSTON');
INSERT INTO marca (nome) VALUES ('MSI');

INSERT INTO classe (nome) VALUES ('LETRA')
INSERT INTO classe (nome) VALUES ('NUMERO')
INSERT INTO classe (nome) VALUES ('KILO')

INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'U')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'P')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'PP')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'M')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'G')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'GG')

INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '33')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '34')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '35')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '36')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '37')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '38')

INSERT INTO produto (nome, referencia, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id, classe_id) VALUES ('Geforce RTX 3060', 'RTX30', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '2023-05-25T18:21:47.741', 0 ,'S', 1, 1);
INSERT INTO produto (nome, referencia, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id, classe_id) VALUES ('Mouse Logitech G403', 'G400', 'Mouse', 'Sensor profissional', '2023-05-26T19:21:47.741', 0, 'S', 2, 1);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Promocional, tempo_Garantia, msg_Garantia, ativo, marca_id) VALUES ('Placa Mãe ASUS', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 2, 0, 1100.00, 1000.00, '12 meses', 'Troca mediante a apresentação de Nota Fiscal', 'S', 3);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('Memória RAM Kingston 16GB', 'Memória RAM', 'C16, 3666MHz', '2023-05-27T20:21:47.741', 0, 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('SDD Kingston 256GB', 'SSD', 'SSD 500MB/s Leitura / 450MB/s Escrita', '2023-05-27T20:21:47.741', 0, 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('Monitor ', 'Monitor Gamer', '144hz 1MS', '2023-05-27T20:21:47.741', 0, 'N', 7);

INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (1, 'U', 7897897897899, 2400.00, 2900.00, 1);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (1, 'U2', 7895553397999, 2500.00, 3000.00, 3);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (2, 'U', 7897897897888, 235.50, 280.00, 3);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (3, 'U', 7897897897888, 1100.00, 1000.00, 2);

INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (1,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2,2);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (3,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (4,3);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (4,5);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (5,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (6,1);

INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste1.jpg', 'Imagem do Produto tamanho U')
INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste2.jpg', 'Imagem do Produto tamanho U2')

INSERT INTO venda (data_Venda, venda_Status, nome_cliente, cpf, celular, email, frete, desconto, sub_total, total) VALUES ('2023-08-05T20:50:15Z', 1, 'GUSTAVO PARIZZATO', '000.000.000-00', '(00) 00000-0000', 'gustavo@gmail.com', 10.00, 5.00, 2645.50, 2640.50);
INSERT INTO venda_itens (venda_id, produto_id, nome_Produto, quantidade, preco, desconto, total) VALUES (1, 1, 'Geforce RTX 3060', 1, 2400.00, 0.00, 2400.00);
INSERT INTO venda_itens (venda_id, produto_id, nome_Produto, quantidade, preco, desconto, total) VALUES (1, 2, 'Mouse Logitech G403', 1, 235.50, 10.00, 225.50);
INSERT INTO endereco(venda_id, cep, endereco, numero, complemento, bairro, cidade, estado, pais) VALUES (1, '10123-987', 'Rua teste', '100', 'Casa', 'Bairro Teste', 'Cidade Teste', 'SP', 'BRA')

INSERT INTO venda (data_Venda, venda_Status, nome_cliente, cpf, celular, email, frete, desconto, sub_total, total) VALUES ('2023-07-05T20:50:15Z', 3, 'GUSTAVO PARIZZATO', '000.000.000-00', '(00) 00000-0000', 'gustavo@gmail.com', 0.00, 50.00, 1000.00, 950.00);
INSERT INTO venda_itens (venda_id, produto_id, nome_Produto, quantidade, preco, desconto, total) VALUES (2, 3, 'TUF GAMING XXX', 1, 1000.00, 0.00, 1000.00);
INSERT INTO pagamento (venda_id, data, tipo, valor, qtd_parcelas) VALUES (2, '2023-07-05T21:01:27Z', 'DÉBITO', 200.00, 1)
INSERT INTO pagamento (venda_id, data, tipo, valor, qtd_parcelas) VALUES (2, '2023-07-05T21:02:27Z', 'CRÉDITO', 800.00, 4)
INSERT INTO endereco(venda_id, cep, endereco, numero, complemento, bairro, cidade, estado, pais) VALUES (2, '10123-987', 'Rua teste', '100', 'Casa', 'Bairro Teste', 'Cidade Teste', 'SP', 'BRA')
INSERT INTO endereco(venda_id, cep, endereco, numero, complemento, bairro, cidade, estado, pais) VALUES (2, '11999-888', 'Rua teste1', '200', 'AP', 'Bairro Teste1', 'Cidade Teste1', 'SP', 'BRA')


