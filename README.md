# OOP_Library_Management
In this repository, we students of BITS Pilani of Group 34 and Group 64 will work together to create a library management system with the following abilities:

## Project 4
### Bits Library Management System

**User Mode (for Students):**
+ Register in the library portal
+ Can search for the book from the book’s collection
+ Submit a request to issue a book
+ When a due date is about to come up, the student should be able to either reissue the book or deposit the book
+ Should be able to view the dues on the library account.

**Admin Mode (for Librarian):**
+ Manage details of students and books
+ Able to add/remove books from the collection 

**Also, we have the following constraints:**
+ A student is limited to three book checkouts.
+ A student can keep a book for at most 15 days.
+ If a student returns a book after the deadline, the system should be able to calculate the charges and add it to the existing dues.

Concepts expected to be incorporated into the project: **Multithreading** (to handle multiple requests for submitting issue requests), inheritance, interfaces, Swing framework (Java)/React (JS) for GUI [GUI will earn 5 marks bonus and is not mandatory to be implemented]

Testing will be done using test cases entered through a text file and outputs printed to another text file. Test case format and sample test cases will be uploaded after the 1st phase of submission.

Associated TA (contact via e-mail only): Mithil Shah (f20200980@pilani.bits-pilani.ac.in)


## Documentation

The following functionalities have been implemented for Student:
+ Registration/ Sign-In
+ Book Search by:
  + Title
  + Author
  + ISBN
+ Book Issue:
  + Can issue at maximum 3 books
  + Will have due date set to 15 days ahead of current date
+ Book Re-Issue: Can't be re-issued again
+ View total dues.
+ Return book:
  + Will show dues incurred for the returned book
  + Will add this to the total dues

The following functionalities have been implemented for Librarian:
+ Review all Books
+ Review all students
+ Add/Remove Books
+ Set Fine(Default set to Rs 1.0/day delay)

This project uses MySQL database which is implemented in the Database_DAO.java, which requires a [.jar file](https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.30/mysql-connector-java-8.0.30.jar), which needs to be imported in order for MySQL Driver to work.

### Multithreading

We have used multithreading to handle issue requests and reissue requests.
There are two classes generated which implement the Runnable interface
and override the run() method - BookIssuer and ReissuerBook.

These 2 classes each have a synchronized function - issueBook() in BookIssuer and 
reissuerBook() in ReissuerBook.

The reason why they are synchronized is that if two students are trying to access the same book
at the same time, then error would get generated. So we want to only one thread to access the function
at one time.
