ALTER TABLE tb_product ADD COLUMN image_url VARCHAR(255);
INSERT INTO tb_product (description, brand, model, currency, price, stock) VALUES
  ('Tênis Nike Edição Especial da Alemanha', 'Nike', 'Air Max Germany', 'USD', 160.00, 10),
  ('Bola de Futebol Da Alemanha', 'Adidas', 'Al Rihla Germany', 'USD', 35.00, 25),
  ('Camisa Do Brasil "Brasil com s de show de bola"', 'Nike', 'Seleção Canarinho V1', 'USD', 65.00, 50),
  ('Moletom oficial da seleção Brasileira', 'Nike', 'Moletom CBF Windrunner', 'USD', 85.00, 15),
  ('Tênis Nike Edição Especial da Seleção Brasileira', 'Nike', 'Shox Brazil Edition', 'USD', 180.00, 8),
  ('Bola de Futebol Do Brasil', 'Nike', 'CBF Strike', 'USD', 32.00, 30),
  ('Cachecol de Lã dos Estados Unidos', 'Nike', 'USA Scarf Classic', 'USD', 25.00, 12),
  ('Tênis Nike Edição Especial da Seleção Americana', 'Nike', 'Air Force 1 USA', 'USD', 145.00, 14),
  ('Boné Tematico da Argentina', 'Adidas', 'AFA Cap 3 Stars', 'USD', 28.00, 20);