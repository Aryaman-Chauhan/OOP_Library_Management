public class Book implements Comparable<Book>{
    private String title;
    private String author;
    private Boolean available;
    private String isbn;
    private String genre;
    private String publisher;

    Book(String name, String author, String isbn, String genre, String publisher) { //Constructor for Book which takes 5 arguments as parameters and initializes them
        this.title = name;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publisher = publisher;
        available = true;
    }

    public void setName(String name) { //Sets the name of the book to the passed argument
        this.title = name;
    }

    public String getName() { //Gets the name of the book
        return title;
    }

    public Boolean isAvailable() { //Gets the availability status of the book
        return available;
    }

    public String getAuthor() { //Gets the author of the book
        return author;
    }

    public void setAuthor(String author) { //Sets the author of the book to the passed argument
        this.author = author;
    }

    public void setAvailable(Boolean av) {  //Sets the availability status of the book to the passed argument
        available = av;
    }

    public String getIsbn() { //Sets the isbn code of the book to the passed argument
        return isbn;
    }

    public void setIsbn(String isbn) { //Gets the isbn code of the book
        this.isbn = isbn;
    }

    public String getGenre() { //Gets the genre of the book
        return genre;
    }

    public void setGenre(String genre) { //Sets the genre of the book to the passed argument
        this.genre = genre;
    }

    public String getPublisher() { //Gets the publisher of the book
        return publisher;
    }

    public void setPublisher(String publisher) { //Sets the publisher of the book to the passed argument
        this.publisher = publisher;
    }

    @Override
    public int compareTo(Book o) { //Compares two books on the basis of their names
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object b) { //Two books are equal if they have the same name and author 
        return (b instanceof Book && this.title.equalsIgnoreCase(((Book) b).getName()) && this.author.equalsIgnoreCase(((Book) b).getAuthor()));
    }

    public String toString() { //Returns all the instance fields of the Book class
        return "\nName: " + this.getName() + "\nAuthor: " + this.getAuthor() + "\nISBN: "+ this.getIsbn()+"\nGenre: "+ this.genre
                +"\nPublisher: "+this.getPublisher()+"\n";
    }
}
