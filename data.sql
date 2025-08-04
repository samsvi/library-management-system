CREATE TABLE books (
       id SERIAL PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       author TEXT NOT NULL,
       isbn VARCHAR(20) UNIQUE NOT NULL,
       published_year INT
);

CREATE TABLE book_copies (
     id SERIAL PRIMARY KEY,
     book_id INT NOT NULL REFERENCES books(id),
     available BOOLEAN DEFAULT TRUE
);

INSERT INTO books (title, author, isbn, published_year) VALUES ('Clean Code', 'Robert C. Martin', '9780132350884', 2008) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Effective Java', 'Joshua Bloch', '9780134685991', 2018) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Design Patterns', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', '9780201633610', 1994) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Refactoring', 'Martin Fowler', '9780201485677', 1999) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780201616224', 1999) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Head First Design Patterns', 'Eric Freeman, Bert Bates', '9780596007126', 2004) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Java Concurrency in Practice', 'Brian Goetz', '9780321349606', 2006) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Test Driven Development', 'Kent Beck', '9780321146533', 2002) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Domain-Driven Design', 'Eric Evans', '9780321125217', 2003) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Working Effectively with Legacy Code', 'Michael Feathers', '9780131177055', 2004) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Patterns of Enterprise Application Architecture', 'Martin Fowler', '9780321127426', 2002) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Clean Architecture', 'Robert C. Martin', '9780134494166', 2017) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('The Mythical Man-Month', 'Frederick P. Brooks Jr.', '9780201835953', 1995) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Continuous Delivery', 'Jez Humble, David Farley', '9780321601919', 2010) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Agile Software Development', 'Alistair Cockburn', '9780135974445', 2001) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Clean Code in Python', 'Mariano Anaya', '9781788835835', 2018) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Effective Python', 'Brett Slatkin', '9780134034287', 2015) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Python Cookbook', 'David Beazley, Brian K. Jones', '9781449340377', 2013) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Fluent Python', 'Luciano Ramalho', '9781491946008', 2015) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', 2018) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 2009) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Cracking the Coding Interview', 'Gayle Laakmann McDowell', '9780984782857', 2015) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Head First Java', 'Kathy Sierra, Bert Bates', '9780596009205', 2005) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('Effective Modern C++', 'Scott Meyers', '9781491903995', 2014) ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (title, author, isbn, published_year) VALUES ('The C Programming Language', 'Brian W. Kernighan, Dennis M. Ritchie', '9780131103627', 1988) ON CONFLICT (isbn) DO NOTHING;

