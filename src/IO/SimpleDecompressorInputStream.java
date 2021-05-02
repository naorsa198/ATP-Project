package IO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class SimpleDecompressorInputStream extends InputStream{

    private InputStream in;
    private Integer rows, cols, startRow, startCol, endRow, endCol;


    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
        rows = 0;
        cols = 0;
        startRow = 0;
        startCol = 0;
        endRow = 0;
        endCol = 0;
    }


    @Override
    public int read(){
        return -1;
    }


    public int read(byte[] b) {
        byte[] x = new byte[0];
        try {

//            int size2 = super.read(b);
//            System.out.println(size2);
//            byte[] to_send = new  byte[size2];

            //  byte[] to_send = in.readAllBytes();
            List<Byte> list_of_begin = new ArrayList<Byte>();

            while (in.available() > 0)
                list_of_begin.add((byte) in.read());
            byte[] to_send = new byte[list_of_begin.size()];
            for (int i = 0; i < list_of_begin.size(); i++)
                to_send[i] = list_of_begin.get(i);
            //System.out.println("reading " + to_send.length);


            int r = 0;
            int maze_rows = -1, maze_cols = -1;

            int i = 0;
            //search for rows
            i = updateProperty(to_send, 1, i);
            //search for cols
            i = updateProperty(to_send, 2, i);
            //search for startRow
            i = updateProperty(to_send, 3, i);
            //search for startCol
            i = updateProperty(to_send, 4, i);
            //search for endRow
            i = updateProperty(to_send, 5, i);
            //search for endCol
            i = updateProperty(to_send, 6, i);
            int startPoint = i;

            //tests:

            // int sizing = ((rows*cols)/8 + startPoint);

//            if ((rows*cols)/8 + startPoint !=  to_send.length && (rows*cols)/8 + startPoint !=  to_send.length-1){
//                System.out.println("FUCK YOU");
//                System.out.println("from decompressor: "+rows + " , "+ cols);
//            }

            x = Decompress(to_send, startPoint);

            int size;
            if (x.length > b.length)
                size = b.length;
            else size = x.length;

            for (i = 0; i < size; i++) {
                b[i] = x[i];
            }

            in.close();
            return 0;
        } catch (IOException e) {
            System.out.println("IO EXCEPT");
        }

//        catch (IndexOutOfBoundsException e){
//            System.out.println("Index out of bounds exception"+ '\n' + "b len: " + b.length + '\n' + "x len: " + x.length);
//        }
        return -1;
    }



    private int updateProperty(byte[] d, int code, int i) {

        int ifZero=i;
        if (code ==1) { //rows
            while (d[i] != 0) {
                if (d[i] < 0)
                    rows = rows + (int) (d[i] + 256);
                else rows = rows + (int) d[i];
                i++;
            }
        }
        else if (code ==2) { //cols
            while (d[i] != 0) {
                if (d[i] < 0)
                    cols = cols + (int) (d[i] + 256);
                else cols = cols + (int) d[i];
                i++;
            }
        }
        else if (code ==3) { //startRow
            while (d[i] != 0) {
                if (d[i] < 0)
                    startRow = startRow + (int) (d[i] + 256);
                else startRow = startRow + (int) d[i];
                i++;
            }
        }
        else if (code ==4) { //startCol
            while (d[i] != 0) {
                if (d[i] < 0)
                    startCol = startCol + (int) (d[i] + 256);
                else startCol = startCol + (int) d[i];
                i++;
            }
        }
        else if (code ==5) { //endRow
            while (d[i] != 0) {
                if (d[i] < 0)
                    endRow = endRow + (int) (d[i] + 256);
                else endRow = endRow + (int) d[i];
                i++;
            }
        }
        else if (code ==6) { //endCol
            while (d[i] != 0) {
                if (d[i] < 0)
                    endCol = endCol + (int) (d[i] + 256);
                else endCol = endCol + (int) d[i];
                i++;
            }
        }
        if (i == ifZero)
            i = i + 2;
        else i++;
        return i;
    }

    private byte[] Decompress(byte[] d, int start) {

        byte[] tosend = new byte[rows * cols];

        while (start < d.length) {
            int i = 0;
            while (i < d[start]) {
                if (start % 2 == 0)
                    tosend[start] = 0;
                else tosend[start] = 1;
                i++;
            }
            start += 1;
        }
        return tosend;
    }



 }

