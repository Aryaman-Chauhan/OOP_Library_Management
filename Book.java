public class Book implements Comparable<Book>{
    private String title;
    private String author;
    private Boolean available;
    private String isbn;
    private String genre;
    private String publisher;

    Book(String name, String author, String isbn, String genre, String publisher) {
        this.title = name;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publisher = publisher;
        available = true;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getName() {
        return title;
    }

    public Boolean isAvailable() {
        return available;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(Boolean av) {
        available = av;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public int compareTo(Book o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object b) {
        return (b instanceof Book && this.title.equalsIgnoreCase(((Book) b).getName()) && this.author.equalsIgnoreCase(((Book) b).getAuthor()));
    }

    public String toString() {
        return "\nName: " + this.getName() + "\nAuthor: " + this.getAuthor() + "\nISBN: "+ this.getIsbn()+"\nGenre: "+ this.genre
                +"\nPublisher: "+this.getPublisher()+"\n";
    }
}
