// IBookManager.aidl
package com.blueizz.ipc.aidl;

// Declare any non-default types here with import statements
import com.blueizz.ipc.aidl.Book;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book> getBookList();

     void addBook(in Book book);

}
