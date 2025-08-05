use revmarketsales;
create table Branch (
						branch_id int auto_increment primary key,
                        branch_code varchar(255) unique not null,
                        city varchar(255) not null
					);
                    
create table Customers (
						customer_id int auto_increment primary key,
                        customer_type varchar(255) not null,
                        gender varchar(255) not null
					);

create table Products (
						product_id int auto_increment primary key,
                        product_line varchar(255) not null,
                        unit_price decimal(10, 2) not null,
                        unique(product_line, unit_price)
					);
                    
create table Sales (
						invoice_id varchar(255) primary key,
                        branch_id int,
                        customer_id int,
                        product_id int,
                        quantity int not null,
                        tax decimal(10, 2) not null,
                        total_sales decimal(10, 2) not null,
                        transaction_datetime datetime not null,
                        payment_method varchar(255) not null,
                        cogs decimal(10, 2),
                        gross_margin_percent decimal(5,2),
                        gross_income decimal(10, 2),
                        product_rating decimal(3, 1),
                        foreign key (branch_id) references Branch(branch_id),
                        foreign key (customer_id) references Customers(customer_id),
                        foreign key (product_id) references Products(product_id)
					);
                    
create index idx_transaction_date on Sales (transaction_datetime);

create index idx_branch_id on Sales (branch_id);

create index idx_product_id on Sales (product_id);

select * from sales;

SELECT SUM(total_sales) FROM Sales;

SELECT AVG(product_rating) AS avg_rating FROM Sales;

SELECT DATE_FORMAT(transaction_datetime, '%Y-%m') AS month, SUM(total_sales) AS total_sales FROM sales GROUP BY month ORDER BY month;

SELECT HOUR(transaction_datetime) AS hour, SUM(total_sales) AS total_sales FROM sales GROUP BY hour ORDER BY hour;

SELECT DAYNAME(transaction_datetime) AS day, SUM(total_sales) AS total_sales FROM sales
GROUP BY day ORDER BY FIELD(day, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

SELECT SUM(gross_income) AS total_gross_income FROM Sales;

select * from products;

select * from branch;

select * from customers;

SELECT DATE_FORMAT(transaction_datetime, '%Y-%u') AS week, SUM(total_sales) AS weekly_sales FROM Sales GROUP BY week ORDER BY week;

SELECT HOUR(transaction_datetime) AS Hour, COUNT(*) AS transactions, SUM(total_sales) AS total_sales
FROM Sales GROUP BY Hour ORDER BY total_sales DESC;

-- views for branch/city and product_line performances 
-- 1
CREATE OR REPLACE VIEW view_sales_by_product_line AS 
SELECT 
		p.product_line, 
        SUM(s.total_sales) AS total_sales
FROM
		Sales AS s
JOIN
		Products AS p ON s.product_id = p.product_id
GROUP BY
		p.product_line;

-- 2 
CREATE OR REPLACE VIEW view_sales_by_branch AS 
SELECT 
		b.branch_code, 
        SUM(s.total_sales) AS total_sales
FROM
		Sales AS s
JOIN
		Branch AS b ON s.branch_id = b.branch_id
GROUP BY
		b.branch_code;

-- 3 
CREATE OR REPLACE VIEW view_sales_by_city AS 
SELECT 
		b.city, 
        SUM(s.total_sales) AS total_sales
FROM
		Sales AS s
JOIN
		Branch AS b ON s.branch_id = b.branch_id
GROUP BY
		b.city;
        
select * from view_sales_by_product_line;

select * from view_sales_by_branch;

select * from view_sales_by_city;

--  Customer Behavior Analysis Views
-- 1
CREATE OR REPLACE VIEW view_sales_by_customer_type AS
SELECT 
    c.customer_type, 
    SUM(s.total_sales) AS total_sales
FROM 
    Sales AS s
JOIN 
    Customers AS c ON s.customer_id = c.customer_id
GROUP BY 
    c.customer_type;
    
select * from view_sales_by_customer_type;

-- 2
CREATE OR REPLACE VIEW view_sales_by_gender AS
SELECT 
    c.gender, 
    SUM(s.total_sales) AS total_sales
FROM 
    Sales AS s
JOIN 
    Customers AS c ON s.customer_id = c.customer_id
GROUP BY 
    c.gender;
    
select * from view_sales_by_gender;

-- Profitability & Discounts views
-- 1
CREATE OR REPLACE VIEW view_gross_income_by_product_line AS
SELECT 
    p.product_line, 
    SUM(s.gross_income) AS total_gross_income
FROM 
    Sales AS s
JOIN 
    Products AS p ON s.product_id = p.product_id
GROUP BY 
    p.product_line
ORDER BY 
    total_gross_income DESC;
    
select * from view_gross_income_by_product_line;
    
-- 2
CREATE VIEW view_avg_tax_by_product_line AS
SELECT
		p.product_line,
        AVG(s.tax) AS avg_tax
FROM
		Sales AS s
JOIN
		Products AS p ON s.product_id = p.product_id
GROUP BY
		p.product_line;
 
select * from view_avg_tax_by_product_line;

