package ch.heig.dai.lab.fileio;

import java.io.File;
import java.nio.charset.Charset;

// *** TODO: Change this to import your own package ***
import ch.heig.dai.lab.fileio.Michaprog.*;

public class Main {
    // *** TODO: Change this to your own name ***
    private static final String newName = "Mikhail Shashkov";

    /**
     * Main method to transform files in a folder.
     * Create the necessary objects (FileExplorer, EncodingSelector, FileReaderWriter, Transformer).
     * In an infinite loop, get a new file from the FileExplorer, determine its encoding with the EncodingSelector,
     * read the file with the FileReaderWriter, transform the content with the Transformer, write the result with the
     * FileReaderWriter.
     * 
     * Result files are written in the same folder as the input files, and encoded with UTF8.
     *
     * File name of the result file:
     * an input file "myfile.utf16le" will be written as "myfile.utf16le.processed",
     * i.e., with a suffixe ".processed".
     */
    public static void main(String[] args) {
        // Read command line arguments
        if (args.length != 2 || !new File(args[0]).isDirectory()) {
            System.out.println("You need to provide two command line arguments: an existing folder and the number of words per line.");
            System.exit(1);
        }

        String folder = args[0];
        int wordsPerLine = Integer.parseInt(args[1]);

        System.out.println("Application started, reading folder " + folder + "...");

        // Initialize necessary objects
        FileExplorer fileExplorer = new FileExplorer(folder);
        EncodingSelector encodingSelector = new EncodingSelector();
        FileReaderWriter fileReaderWriter = new FileReaderWriter();
        Transformer transformer = new Transformer(newName, wordsPerLine);

        // Process files in a loop
        while (true) {
            try {
                // Get a new file
                File file = fileExplorer.getNewFile();

                if (file == null) {
                    // If no new files, break out of the loop
                    System.out.println("No new files found. Exiting...");
                    break;
                }

                // Determine encoding
                Charset encoding = encodingSelector.getEncoding(file);

                if (encoding == null) {
                    System.out.println("Unsupported encoding for file: " + file.getName());
                    continue;
                }

                // Read the file content
                String content = fileReaderWriter.readFile(file, encoding);

                if (content == null) {
                    System.out.println("Could not read content from file: " + file.getName());
                    continue;
                }

                // Transform the content
                content = transformer.replaceChuck(content);
                content = transformer.capitalizeWords(content);
                content = transformer.wrapAndNumberLines(content);

                // Prepare output file name
                File outputFile = new File(file.getAbsolutePath() + ".processed");

                // Write transformed content to the output file in UTF-8 encoding
                boolean success = fileReaderWriter.writeFile(outputFile, content, Charset.forName("UTF-8"));

                if (success) {
                    System.out.println("Processed file written to: " + outputFile.getName());
                } else {
                    System.out.println("Failed to write processed file for: " + file.getName());
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
