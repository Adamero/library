# library

skrypt1 - zamienia liczby 0 w tytule na O
String script = """
                    
                     function findBooks() {
                           var book = bookRepository.findAll();
                           var test;
                           for (books of bookRepository.findAll()){
                                tst = books.getTitle();
                                if(tst.includes("0")){
                                test = books;
                                books.setTitle(tst.replaceAll("0","O"));
                                bookRepository.save(books);
                                }
                           }                           
                           return book;
                         }
                        
                         console.log(findBooks());
                         findBooks();
                """;
skrypt2 - dodaje błędne dane        
String script = """
                    function addBooks() {
                        var Book = Java.type('pl.edu.wat.testowy.entity.Book');
                        var books = [{ title: 't00es00t000w0'}, { title: 't01es01t001w1'}];
                        for (book of books) {
                            if (book.title.includes("0")) {
                                var newBook = new Book();
                                newBook.setTitle(book.title);
                                newBook.setDescription(book.description);
                                bookRepository.save(newBook);
                            }
                        }
                    }
                    addBooks();
                """;
