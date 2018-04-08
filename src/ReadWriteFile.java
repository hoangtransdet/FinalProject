import java.io.*;

public class ReadWriteFile {

    public int countLineFromFile() {
        File f = new File("test.txt");
        FileReader fr = null;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader b = new BufferedReader(fr);

        String temp = new String();
        StringBuilder stringAppend = new StringBuilder();
        String nextLine = System.getProperty("line.separator");
        int line = 0;

        try {
            while ((temp = b.readLine()) != null) {
                line++;
            }

            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }


    public String getFile(int lines) {
        File file = new File("test.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String temp = new String();
        StringBuilder stringAppend = new StringBuilder();
        String nextLine = System.getProperty("line.separator");

        int lineRead = 0;
        try {
            while ((temp = bufferedReader.readLine()) != null) {

                stringAppend.append(temp);
                lineRead++;

                if (lines != lineRead)
                    stringAppend.append(nextLine);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringAppend.toString();
    }

    public void setFile(String content) {
        File file = new File("test_out.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
