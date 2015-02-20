/*
    Sample code to simulate usage of SequenceInputStream in java.io package.
*/
package sequenceinputstreamwithenumerationdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NawazishMohammad
 */
public class SequenceInputStreamWithEnumerationDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MyInputStreamEnumerator myInputStreamEnumerator = new MyInputStreamEnumerator();
        SequenceInputStream sis = new SequenceInputStream(myInputStreamEnumerator);
        try {
            /*
                   file threeinone.txt would be one consolidated file made out of
                   the bytes read from each InputStream returned by an Enumeration.
                   This Enumeration is fed to the constructor of SequenceInputStream
            
            */
            FileOutputStream fos = new FileOutputStream("C:\\CoreCode\\threeinone.txt"); 
            int c;
            while ((c=sis.read())!= -1){
                fos.write(c);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SequenceInputStreamWithEnumerationDemo.class.getName()).log(Level.SEVERE, "could not open FOS", ex);
        } catch (IOException ex) {
            Logger.getLogger(SequenceInputStreamWithEnumerationDemo.class.getName()).log(Level.SEVERE, "could not read bytes from input stream", ex);
        }
    }
    
}

class MyInputStreamEnumerator implements Enumeration<FileInputStream> {

    List <File> fileList = new ArrayList<>();
    int size;
    int counter = 0;
    int getOf;
    public MyInputStreamEnumerator() {
        //For dev. and testing purpose I have three .txt files at C:\\CoreCode\\NewCoreCodeDir 
        Path path = Paths.get("C:\\CoreCode\\NewCoreCodeDir");
        File file = path.toFile();
        File[] files = file.listFiles();
        fileList.addAll(Arrays.asList(files));
        size = fileList.size();
    }
    

    @Override
    public boolean hasMoreElements() {
        if (counter < size){
            getOf = counter;
            counter += 1;
            return true;
        }
        return false;
    }

    @Override
    public FileInputStream nextElement() {
        try {
            return new FileInputStream(fileList.get(getOf));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyInputStreamEnumerator.class.getName()).log(Level.SEVERE, "could not create FIS", ex);
        }
        return null;
    }
    
}
