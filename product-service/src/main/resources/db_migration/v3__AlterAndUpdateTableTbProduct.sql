ALTER TABLE tb_product ADD COLUMN image_url VARCHAR(255);

UPDATE tb_product SET image_url ='';

UPDATE tb_product
    SET image_url = ''
    WHERE description = 'iPhone 15 128GB';

    SET image_url = ''
    WHERE description = 'iPhone 15 Pro 256GB';

    SET image_url = ''
    WHERE description = 'Galaxy S24 256GB';

    SET image_url = ''
    WHERE description = 'Galaxy S24 Ultra 512GB';

    SET image_url = ''
    WHERE description = 'Moto G84 256GB';

    SET image_url = ''
    WHERE description = 'Moto Edge 40 256GB';

    SET image_url = ''
    WHERE description = 'Redmi Note 13 Pro 256GB';

    SET image_url = ''
    WHERE description = 'Redmi 13C 128GB';

    SET image_url = ''
    WHERE description = 'Pixel 8 128GB';


