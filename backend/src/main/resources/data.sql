INSERT INTO BOOKS (title, author, isbn, quantity, available_quantity)
VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 10, 7),
('Effective Java', 'Joshua Bloch', '9780134685991', 8, 5),
('Spring in Action', 'Craig Walls', '9781617294945', 12, 9),
('Head First Java', 'Kathy Sierra', '9780596009205', 15, 11),
('Java Concurrency in Practice', 'Brian Goetz', '9780321349606', 6, 4),
('Design Patterns', 'Erich Gamma', '9780201633610', 9, 6),
('Refactoring', 'Martin Fowler', '9780201485677', 7, 5),
('The Pragmatic Programmer', 'Andrew Hunt', '9780201616224', 10, 8),
('Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 5, 3),
('Data Structures and Algorithms', 'Narasimha Karumanchi', '9788193245279', 14, 10),
('Microservices Patterns', 'Chris Richardson', '9781617294549', 11, 8),
('Spring Boot Up and Running', 'Mark Heckler', '9781492076988', 13, 10),
('Hibernate Tips', 'Thorben Janssen', '9783963136986', 6, 4),
('Docker Deep Dive', 'Nigel Poulton', '9781521822807', 9, 7),
('Kubernetes in Action', 'Marko Luksa', '9781617293726', 5, 3),
('System Design Interview', 'Alex Xu', '9781736049112', 14, 9),
('Computer Networks', 'Andrew Tanenbaum', '9780132126953', 7, 5),
('Operating System Concepts', 'Abraham Silberschatz', '9781118063330', 10, 6),
('Artificial Intelligence', 'Stuart Russell', '9780136042594', 4, 2),
('Database System Concepts', 'Henry Korth', '9780073523323', 8, 5);

INSERT INTO MEMBERS (name, email, phone)
VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '9876543210'),
('Diya Patel', 'diya.patel@example.com', '9876543211'),
('Vivaan Mehta', 'vivaan.mehta@example.com', '9876543212'),
('Ananya Singh', 'ananya.singh@example.com', '9876543213'),
('Aditya Verma', 'aditya.verma@example.com', '9876543214'),
('Ishita Kapoor', 'ishita.kapoor@example.com', '9876543215'),
('Krishna Nair', 'krishna.nair@example.com', '9876543216'),
('Meera Joshi', 'meera.joshi@example.com', '9876543217'),
('Rohan Deshmukh', 'rohan.deshmukh@example.com', '9876543218'),
('Sneha Iyer', 'sneha.iyer@example.com', '9876543219'),
('Rahul Khanna', 'rahul.khanna@example.com', '9876543220'),
('Priya Menon', 'priya.menon@example.com', '9876543221'),
('Kabir Arora', 'kabir.arora@example.com', '9876543222'),
('Neha Kulkarni', 'neha.kulkarni@example.com', '9876543223'),
('Yash Malhotra', 'yash.malhotra@example.com', '9876543224'),
('Pooja Bansal', 'pooja.bansal@example.com', '9876543225'),
('Arjun Rao', 'arjun.rao@example.com', '9876543226'),
('Simran Gill', 'simran.gill@example.com', '9876543227'),
('Dev Patel', 'dev.patel@example.com', '9876543228'),
('Tanya Shah', 'tanya.shah@example.com', '9876543229');

INSERT INTO TRANSACTIONS (member_id, book_id, issue_date, due_date, return_date, status)
VALUES

-- Heavy readers
(1, 1, '2026-05-10', '2026-05-24', NULL, 'ISSUED'),
(1, 5, '2026-04-01', '2026-04-15', '2026-04-12', 'RETURNED'),
(1, 2, '2026-03-01', '2026-03-15', '2026-03-14', 'RETURNED'),
(1, 11, '2026-02-01', '2026-02-15', '2026-02-13', 'RETURNED'),
(1, 7, '2026-01-10', '2026-01-24', '2026-01-20', 'RETURNED'),

(3, 3, '2026-05-01', '2026-05-15', NULL, 'ISSUED'), -- overdue
(3, 9, '2026-04-03', '2026-04-17', '2026-04-15', 'RETURNED'),
(3, 6, '2026-05-18', '2026-06-01', NULL, 'ISSUED'),
(3, 14, '2026-02-05', '2026-02-19', '2026-02-17', 'RETURNED'),

(7, 7, '2026-05-07', '2026-05-21', NULL, 'ISSUED'), -- due today
(7, 8, '2026-04-07', '2026-04-21', NULL, 'ISSUED'), -- overdue
(7, 3, '2026-03-07', '2026-03-21', NULL, 'ISSUED'), -- overdue
(7, 15, '2026-02-07', '2026-02-21', '2026-02-18', 'RETURNED'),
(7, 19, '2026-01-07', '2026-01-21', '2026-01-19', 'RETURNED'),
(7, 2, '2025-12-01', '2025-12-15', '2025-12-10', 'RETURNED'),

-- Moderate users
(2, 2, '2026-05-02', '2026-05-16', '2026-05-10', 'RETURNED'),
(2, 7, '2026-05-15', '2026-05-29', NULL, 'ISSUED'),
(2, 4, '2026-04-02', '2026-04-16', NULL, 'ISSUED'), -- overdue
(2, 15, '2026-04-10', '2026-04-24', NULL, 'ISSUED'), -- overdue

(5, 5, '2026-05-05', '2026-05-19', '2026-05-14', 'RETURNED'),
(5, 4, '2026-04-05', '2026-04-19', '2026-04-17', 'RETURNED'),
(5, 10, '2026-05-20', '2026-06-03', NULL, 'ISSUED'),

(10, 10, '2026-05-10', '2026-05-24', NULL, 'ISSUED'),
(10, 3, '2026-04-10', '2026-04-24', '2026-04-22', 'RETURNED'),
(10, 9, '2026-03-10', '2026-03-24', NULL, 'ISSUED'), -- overdue
(10, 1, '2026-02-10', '2026-02-24', NULL, 'ISSUED'), -- overdue

(13, 13, '2026-05-13', '2026-05-27', '2026-05-20', 'RETURNED'),
(13, 16, '2026-04-13', '2026-04-27', '2026-04-25', 'RETURNED'),
(13, 16, '2026-05-19', '2026-06-02', NULL, 'ISSUED'),

-- Occasional users
(4, 4, '2026-05-04', '2026-05-18', NULL, 'ISSUED'), -- overdue
(6, 6, '2026-05-06', '2026-05-20', NULL, 'ISSUED'), -- overdue
(8, 8, '2026-05-08', '2026-05-22', NULL, 'ISSUED'),
(9, 9, '2026-05-09', '2026-05-23', '2026-05-18', 'RETURNED'),

(11, 12, '2026-04-11', '2026-04-25', NULL, 'ISSUED'), -- overdue
(12, 14, '2026-04-12', '2026-04-26', NULL, 'ISSUED'), -- overdue
(14, 18, '2026-04-14', '2026-04-28', NULL, 'ISSUED'), -- overdue
(15, 20, '2026-05-18', '2026-06-01', NULL, 'ISSUED'),

(16, 11, '2026-04-16', '2026-04-30', '2026-04-28', 'RETURNED'),
(17, 13, '2026-05-17', '2026-05-31', NULL, 'ISSUED'),

-- Rare users
(18, 18, '2026-05-18', '2026-06-01', '2026-05-20', 'RETURNED'),
(19, 19, '2026-05-19', '2026-06-02', NULL, 'ISSUED'),
(20, 20, '2026-05-20', '2026-06-03', NULL, 'ISSUED'),

-- Explicit overdue test cases
(3, 18, '2026-04-01', '2026-04-15', NULL, 'ISSUED'),
(7, 6, '2026-03-15', '2026-03-29', NULL, 'ISSUED'),

-- Recently returned
(1, 20, '2026-05-01', '2026-05-15', '2026-05-14', 'RETURNED'),
(7, 11, '2026-05-02', '2026-05-16', '2026-05-15', 'RETURNED'),
(3, 5, '2026-05-03', '2026-05-17', '2026-05-16', 'RETURNED'),

-- Random extra activity
(5, 16, '2026-01-01', '2026-01-15', '2026-01-14', 'RETURNED'),
(5, 17, '2025-11-01', '2025-11-15', '2025-11-10', 'RETURNED'),

(12, 2, '2026-02-11', '2026-02-25', '2026-02-24', 'RETURNED'),

(14, 9, '2026-03-01', '2026-03-15', NULL, 'ISSUED'), -- overdue

(17, 4, '2026-05-01', '2026-05-15', NULL, 'ISSUED'), -- overdue

(19, 8, '2026-02-20', '2026-03-06', '2026-03-01', 'RETURNED'),

-- Member 1 binge reading
(1, 3, '2026-05-12', '2026-05-26', NULL, 'ISSUED'),
(1, 4, '2026-04-20', '2026-05-04', NULL, 'ISSUED'), -- overdue
(1, 8, '2026-03-11', '2026-03-25', '2026-03-20', 'RETURNED'),

-- Member 2 mixed history
(2, 11, '2026-05-18', '2026-06-01', NULL, 'ISSUED'),
(2, 19, '2026-01-08', '2026-01-22', '2026-01-18', 'RETURNED'),

-- Member 4 inactive but overdue
(4, 15, '2026-04-18', '2026-05-02', NULL, 'ISSUED'), -- overdue

-- Member 5 returning regularly
(5, 1, '2026-05-01', '2026-05-15', '2026-05-13', 'RETURNED'),
(5, 6, '2026-05-17', '2026-05-31', NULL, 'ISSUED'),

-- Member 6 messy borrower
(6, 13, '2026-03-01', '2026-03-15', NULL, 'ISSUED'), -- overdue
(6, 18, '2026-05-10', '2026-05-24', NULL, 'ISSUED'),

-- Member 7 library addict
(7, 10, '2026-05-05', '2026-05-19', NULL, 'ISSUED'), -- overdue
(7, 12, '2026-05-16', '2026-05-30', NULL, 'ISSUED'),
(7, 14, '2026-04-01', '2026-04-15', '2026-04-10', 'RETURNED'),
(7, 16, '2026-02-01', '2026-02-15', '2026-02-12', 'RETURNED'),

-- Member 8
(8, 5, '2026-05-02', '2026-05-16', NULL, 'ISSUED'), -- overdue
(8, 2, '2026-03-20', '2026-04-03', '2026-03-30', 'RETURNED'),

-- Member 9
(9, 4, '2026-05-20', '2026-06-03', NULL, 'ISSUED'),

-- Member 10
(10, 18, '2026-04-01', '2026-04-15', NULL, 'ISSUED'), -- overdue
(10, 5, '2026-01-01', '2026-01-15', '2026-01-11', 'RETURNED'),

-- Member 11
(11, 6, '2026-05-14', '2026-05-28', NULL, 'ISSUED'),
(11, 9, '2026-03-03', '2026-03-17', NULL, 'ISSUED'), -- overdue

-- Member 12
(12, 1, '2026-05-01', '2026-05-15', NULL, 'ISSUED'), -- overdue
(12, 17, '2026-05-19', '2026-06-02', NULL, 'ISSUED'),

-- Member 13
(13, 8, '2026-05-04', '2026-05-18', NULL, 'ISSUED'), -- overdue

-- Member 14
(14, 7, '2026-05-09', '2026-05-23', NULL, 'ISSUED'),
(14, 3, '2026-04-01', '2026-04-15', '2026-04-11', 'RETURNED'),

-- Member 15
(15, 12, '2026-02-10', '2026-02-24', NULL, 'ISSUED'), -- overdue

-- Member 16
(16, 19, '2026-05-13', '2026-05-27', NULL, 'ISSUED'),
(16, 20, '2026-05-01', '2026-05-15', '2026-05-10', 'RETURNED'),

-- Member 17
(17, 2, '2026-05-02', '2026-05-16', NULL, 'ISSUED'), -- overdue
(17, 5, '2026-05-18', '2026-06-01', NULL, 'ISSUED'),

-- Member 18
(18, 11, '2026-04-10', '2026-04-24', NULL, 'ISSUED'), -- overdue

-- Member 19
(19, 14, '2026-05-17', '2026-05-31', NULL, 'ISSUED'),

-- Member 20
(20, 16, '2026-05-03', '2026-05-17', NULL, 'ISSUED'), -- overdue
(20, 18, '2026-03-01', '2026-03-15', '2026-03-12', 'RETURNED');