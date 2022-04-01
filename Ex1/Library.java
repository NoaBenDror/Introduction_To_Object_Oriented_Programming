/**
 * This class represents a library, which holds a collection of books. Patrons can register at the library
 * to be able to check out books, if a copy of the requested book is available.
 */


class Library {

    /** The maximal number of books this library can hold. */
    int bookCapacity;

    /** The maximal number of books this library allows a single patron to borrow
     *  at the same time. */
    int borrowedPerPatronCapacity;

    /** The maximal number of registered patrons this library can handle. */
    int patronCapacity;

    /** Array of books in this library. */

    Book[] books;

    /** The id of the last book added to the library. if no book was added, lastBookId is -1 */
    int lastBookId = -1;

    /** Array of patrons registered to this library. */

    Patron[] patrons;

    /** The id of the last patron registered to the library. if no patron was registered,
     *  lastPatronId is -1 */

    int lastPatronId = -1;


    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron
     *                           to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */

    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity){
        bookCapacity = maxBookCapacity;
        borrowedPerPatronCapacity = maxBorrowedBooks;
        patronCapacity = maxPatronCapacity;
        books = new Book[maxBookCapacity];  //the length is set to be the number in maxBookCapacity
        patrons = new Patron[maxPatronCapacity]; // the length is set to be the number in maxPatronCapacity
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was
     * successfully added, or if the book was already in the library; a negative number otherwise.
     */

    int addBookToLibrary(Book book){

        for(int i=0; i<=lastBookId; i++){  // check if book is already in the library
            if (books[i]== book)
                    return i;  // return it's id number (already exists)
        }
        if(lastBookId+1==books.length)  // no place available in the library
            return -1; // negative number

        books[lastBookId+1] = book;  // add the book to the library
        lastBookId++;  // after adding a new book, add 1 to lastBookId
        return lastBookId;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */

    boolean isBookIdValid(int bookId){
        return (bookId<=lastBookId)&&(bookId>=0);
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */

    int getBookId(Book book){
        for(int i=0; i<=lastBookId; i++) {  // check if book is owned by this library
            if (books[i] == book)
                return i;  // return it's id number if it is
        }
        return -1; // the book is not owned by this library
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */

    boolean isBookAvailable(int bookId){
        if (! isBookIdValid(bookId)) // given bookId invalid
           return false;
        return (books[bookId].getCurrentBorrowerId() == -1); // checks if book is borrowed already
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was
     * successfully registered or if the patron was already registered. a negative number otherwise.
     */

    int registerPatronToLibrary(Patron patron){
        for(int i=0; i<=lastPatronId; i++){  // check if patron is already registered
            if (patrons[i]== patron)
                return i; // he is, return it's Id
        }

        if(lastPatronId+1==patrons.length)  // no spot available to register
            return -1; // negative number

        patrons[lastPatronId+1] = patron;  // register patron to library
        lastPatronId++;  // after registering a new patron, add 1 to lastPatronId
        return lastPatronId;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */

    boolean isPatronIdValid(int patronId){
        return (patronId<=lastPatronId)&&(patronId>=0);
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return  a non-negative id number of the given patron if he is registered to this library,
     *   -1 otherwise.
     */

    int getPatronId(Patron patron){
        for(int i=0; i<=lastPatronId; i++) {  // check if patron is registered to this library
            if (patrons[i] == patron)
                return i;  // return it's id number if it is
        }
        return -1; // patron is not registered to this library
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of
     * books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */

    boolean borrowBook(int bookId, int patronId){
        if (!isPatronIdValid(patronId))  // patronId invalid
            return false;

        int numBorrowedByPatron = 0; // how many books is this patron borrowing, start with 0

        if(!isBookAvailable(bookId)) // book not available
            return false;

        for(int i=0; i<=lastBookId; i++) {  // traverse all the books
            if (books[i].getCurrentBorrowerId() == patronId) // check if book is borrowed by patronId
                numBorrowedByPatron++; // if yes, add 1 to number of books borrowed by patronId
        }
        if (numBorrowedByPatron == borrowedPerPatronCapacity) // patron is already borrowing the
            // maximal number of books
            return false;

        if (!patrons[patronId].willEnjoyBook(books[bookId])) // patron won't enjoy book
            return false;
        books[bookId].setBorrowerId(patronId); // if we got here, it means patron can borrow book
        return true;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */

    void returnBook(int bookId){
        books[bookId].returnBook();
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available
     * books he will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most.
     * Null if no book is available.
     */

    Book suggestBookToPatron(int patronId){
        if (!isPatronIdValid(patronId))  // patronId invalid
            return null;
        if (books.length==0) // no books in library
            return null;
        int maxEnjoyment= -1; // the score of the book he will enjoy the most, start with -1
        Book mostEnjoyable = books[0]; // most enjoyable book (for now)
        for(int i=0; i<=lastBookId; i++) {
            if(patrons[patronId].willEnjoyBook(books[i])&& // enjoyment threshold
                    patrons[patronId].getBookScore(books[i]) > maxEnjoyment && // maximal enjoyment
                    books[i].getCurrentBorrowerId()==-1){ // availability
                    maxEnjoyment = patrons[patronId].getBookScore(books[i]);
                    mostEnjoyable = books[i];
            }
        }
        if (maxEnjoyment == -1)  // no enjoyable and available book was found
            return null;
        return mostEnjoyable;
    }
}