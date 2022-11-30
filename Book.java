public class Book implements Comparable<Book>{
    private String title;
    private String author;
    private Boolean available;

    Book(String name, String author) {
        this.title = name;
        this.author = author;
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

    @Override
    public int compareTo(Book o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object b) {
        return (b instanceof Book && this.title.equalsIgnoreCase(((Book) b).getName()) && this.author.equalsIgnoreCase(((Book) b).getAuthor()));
    }

    public String toString() {
        return "Name: " + this.getName() + "\nAuthor: " + this.getAuthor() + "\nAvailable for borrow: " + this.available;
    }
}
