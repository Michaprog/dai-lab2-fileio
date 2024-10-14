package ch.heig.dai.lab.fileio.Michaprog;
import java.io.*;
import java.nio.charset.Charset;

public class FileReaderWriter {

    /**
     * Read the content of a file with a given encoding.
     * @param file the file to read. 
     * @param encoding
     * @return the content of the file as a String, or null if an error occurred.
     */
    public String readFile(File file, Charset encoding) {
        // TODO: Implement the method body here. 
        // Use the ...Stream and ...Reader classes from the java.io package.
        // Make sure to close the streams and readers at the end.

        if (file == null || encoding == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file),
                                encoding))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return result.toString();
    }

    /**
     * Write the content to a file with a given encoding. 
     * @param file the file to write to
     * @param content the content to write
     * @param encoding the encoding to use
     * @return true if the file was written successfully, false otherwise
     */
    public boolean writeFile(File file, String content, Charset encoding) {
        // TODO: Implement the method body here. 
        // Use the ...Stream and ...Reader classes from the java.io package.
        // Make sure to flush the data and close the streams and readers at the end.

        if (file == null || content == null|| encoding == null) {
            return false;
        }

        try (
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
                BufferedWriter bw = new BufferedWriter(osw)
        ) {
            bw.write(content);
            bw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }
}
