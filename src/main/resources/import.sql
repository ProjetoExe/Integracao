INSERT INTO lojas (razao_Social, nome_Fantasia, cnpj, inscricao_Estadual, email, celular, telefone, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('PROJETO EXE LTDA', 'PROJETO EXE', '20214754000151', '529971505286', 'projeto.exe123@gmail.com', '16 99723-4150', '16 9723-4150', '14804-072','Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO categoria (nome_cat) VALUES ('Hardware');
INSERT INTO categoria (nome_cat) VALUES ('Perifericos');
INSERT INTO categoria (nome_cat) VALUES ('Computadores');
INSERT INTO categoria (nome_cat) VALUES ('Games');
INSERT INTO categoria (nome_cat) VALUES ('Monitor');
INSERT INTO categoria (nome_cat) VALUES ('Acessorios');
INSERT INTO categoria (nome_cat) VALUES ('Eletronicos');
INSERT INTO categoria (nome_cat) VALUES ('Roupas');
INSERT INTO categoria (nome_cat) VALUES ('Calcados');

INSERT INTO marca (nome_marca) VALUES ('Galax');
INSERT INTO marca (nome_marca) VALUES ('Logitech');
INSERT INTO marca (nome_marca) VALUES ('Asus');
INSERT INTO marca (nome_marca) VALUES ('Gigabyte');
INSERT INTO marca (nome_marca) VALUES ('Zotac');
INSERT INTO marca (nome_marca) VALUES ('Kingston');
INSERT INTO marca (nome_marca) VALUES ('MSI');
INSERT INTO marca (nome_marca) VALUES ('AMD');
INSERT INTO marca (nome_marca) VALUES ('Corsair');
INSERT INTO marca (nome_marca) VALUES ('Samsung');
INSERT INTO marca (nome_marca) VALUES ('Sony');
INSERT INTO marca (nome_marca) VALUES ('Nike');
INSERT INTO marca (nome_marca) VALUES ('Adidas');
INSERT INTO marca (nome_marca) VALUES ('Puma');

INSERT INTO classe (nome_classe, opt_ativo) VALUES ('TAMANHO LETRA', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('TAMANHO NUMERO', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('PESO', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('MEDIDA', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('QUANTIDADE', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('VOLTAGEM', 1)
INSERT INTO classe (nome_classe, opt_ativo) VALUES ('CORES', 1)

INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'U')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'PP')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'P')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'M')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'G')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'GG')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'XG')
INSERT INTO classe_grade (classe_id, variacao) VALUES (1, 'XGG')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '23')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '25')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '26')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '27')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '28')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '29')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '30')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '31')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '33')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '34')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '35')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '36')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '37')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '38')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '39')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '40')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '41')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '42')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '43')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '44')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '45')
INSERT INTO classe_grade (classe_id, variacao) VALUES (2, '46')
INSERT INTO classe_grade (classe_id, variacao) VALUES (5, '110V')
INSERT INTO classe_grade (classe_id, variacao) VALUES (5, '220V')
INSERT INTO classe_grade (classe_id, variacao) VALUES (5, 'BIVOLT')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'MARROM')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'AMARELO')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'AZUL')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'VERMELHO')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'PRETO')
INSERT INTO classe_grade (classe_id, variacao) VALUES (6, 'BRANCO')

INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Geforce RTX 3060', 7897897897899, 'RTX30', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '2023-05-25T18:21:47.741', 0, 2900.00, 1, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 1);
INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Mouse Logitech G403', 7897897897888, 'G400', 'Mouse', 'Sensor profissional', '2023-05-26T19:21:47.741', 0, 280.00, 1, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2);
INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, tempo_Garantia, msg_Garantia, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Placa Mãe ASUS', 7897897897887, 'PLM', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 0, 1100.00, '12 meses', 'Troca mediante a apresentação de Nota Fiscal', 1, 0, 0, 0, 0, 1, 1, 1, 3, 3, 3, 1);
INSERT INTO produto (nome_prod, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Calça Moletom Puma Preta', 'CMP24', 'Calça', 'Calça Moletom Preta de Algodão', '2023-05-27T20:21:47.741', 0, 249.99, 1, 0, 0, 0, 0, 1, 2, 1, 14, null, 2, 8);
INSERT INTO produto (nome_prod, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Camiseta Algodão Puma Branca', 'CA24', 'Camiseta', 'Camiseta de Algodão Branca Verão', '2023-05-27T20:21:47.741', 0, 169.99, 1, 0, 0, 0, 0, 1, 2, 1, 14, null, 4, 8);
INSERT INTO produto (nome_prod, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Bermuda Moletom Puma Azul', 'BJ24', 'Bermuda', 'Bermuda Jeans Verão', '2023-05-27T20:21:47.741', 0, 139.99, 0, 0, 0, 0, 0, 1, 2, 1, 14, null, 2, 8);
INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Placa de Vídeo GALAX GeForce RTX 3080', 7897897897898, 'RTX30', 'Placa de Vídeo', '10GB de RAM 256 Bits 3 Display Port e 1 HDMI', '2023-05-28T18:21:47.741', 0, 4000.00, 0, 0, 0, 0, 0, 1, 1, 1, 1, 4, 4, 1);
INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Teclado Gamer Logitech G Pro X', 7897897897897, 'GPROX', 'Teclado Mecânico', 'Teclado mecânico RGB com switches GX Blue', '2023-05-29T19:21:47.741', 0, 350.00, 1, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2);
INSERT INTO produto (nome_prod, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Monitor ASUS TUF Gaming VG27AQ', 7897897897896, 'VG27AQ', 'Monitor LED', 'Monitor de 27 polegadas com resolução Quad HD', '2023-05-30T20:21:47.741', 0, 1000.00, 1, 0, 0, 0, 0, 1, 1, 1, 3, 1, 1, 5);
INSERT INTO produto (nome_prod, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Camiseta Nike Dry Fit Preta', 'CNK01', 'Camiseta', 'Camiseta esportiva com tecnologia Dry Fit', '2023-05-31T20:21:47.741', 0, 99.99, 1, 0, 0, 0, 0, 1, 2, 1, 12, null, 10, 8);
INSERT INTO produto (nome_prod, referencia, desc_Curta, desc_Longa, data_Cadastro, qtd_vendida, preco_venda, opt_ativo, opt_lancamento, opt_promocao, opt_frete_gratis, opt_prod_virtual, opt_disponivel, opt_variacao, classe_id, marca_id, qtd_estoque, estoque_total, categoria_principal_id) VALUES ('Tênis Adidas Ultra Boost', 'TADB01', 'Tênis', 'Tênis esportivo com tecnologia Boost', '2023-06-01T20:21:47.741', 0, 299.99, 1, 0, 0, 0, 0, 1, 2, 2, 13, null, 9, 9);

INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (4, 'P', 7897897890001, 249.99, 1);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (4, 'M', 7897897890002, 249.99, 1);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (5, 'PP', 7897897890003, 169.99, 1);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (5, 'G', 7897897890004, 169.99, 3);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (6, 'P', 7897897890005, 139.99, 2);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (10, 'P', 7897897890007, 99.99, 3);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (10, 'M', 7897897890008, 99.99, 3);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (10, 'G', 7897897890009, 109.99, 2);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (10, 'GG', 7897897890010, 109.99, 2);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (11, '38', 7897897890011, 299.99, 1);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (11, '39', 7897897890012, 299.99, 3);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (11, '40', 7897897890013, 299.99, 4);
INSERT INTO produto_grade (produto_id, variacao, ean, preco_venda, qtd_estoque) VALUES (11, '41', 7897897890014, 299.99, 1);

INSERT INTO produto_sub_categorias (produto_id, categoria_id) VALUES (1,3);
INSERT INTO produto_sub_categorias (produto_id, categoria_id) VALUES (1,7);

INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste1.jpg', 'Imagem do Produto 1 - Geforce RTX 3060')
INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste2.jpg', 'Imagem do Produto 1 - Geforce RTX 3060 v2')
INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (2, 'http:://www.site.com.br/teste3.jpg', 'Imagem do Produto 2 - Mouse Logitech')

INSERT INTO cliente (nome_cli, documento, data_nascimento, celular, total_pedidos, opt_ativo) VALUES ('Gustavo', '48336604820', '2001-10-04', '16 99723 4150', 0, 1)
INSERT INTO endereco (cep, endereco, numero, complemento, bairro, cidade, estado, pais, cliente_id, opt_principal) VALUES ('14800111', 'Rua Teste', '100', 'Teste', 'Bairro Teste', 'Arapica', 'SP', 'BR', 1, 1)