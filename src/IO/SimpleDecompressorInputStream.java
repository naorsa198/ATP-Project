package IO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;
    private Integer rows, cols , startRow , startCol, endRow , endCol;



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
    private byte[] Decompress(byte[] d, int start) {



        int startFrom = start;

        //run on maze
        byte[] result = new byte[rows * cols];
        int index=0;
        int k= startFrom;
        try {
            for (; k < d.length-1; k++) {
                byte worksOn = d[k];
                byte[] tmp = ConvertToBinaryArray(worksOn, 8);
                for (int l = 0; l < 8; l++) {
                    result[index] = tmp[l];
                    index++;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
            System.out.println("I am out of bounds");

        }

        /**
         * last convertion
         */
        try {
            byte dk = d[k];
            int mod = result.length % 8;
            if (mod == 0)
                mod = 8;
            byte[] tmp = ConvertToBinaryArray(dk, mod);
            for (int l = 0; l < mod; l++) {
                result[index] = tmp[l];
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("x");
        }

        //update properties on maze- new lines
        for (int j=cols-1; j<result.length; j= j+ cols){
            if (result[j]==0)
                result[j]=4;
            else result[j]=5;
        }

        //update start point
        int index_start = startRow*cols + startCol;
        if (result[index_start]==4)
            result[index_start]=6;
        else result[index_start]=2;

        //update end point
        int index_end = endRow*cols + endCol;
        if (result[index_end]==4)
            result[index_end]=7;
        else result[index_end]=3;

        return result;
    }

    /**
     *
     * @param b - compressed maze (with at least 12 cells of properties)
     * @return
     */


    @Override
    public int read(byte[] b) {
        byte[] x = new byte[0];
        try {


            List<Byte> list_of_begin = new ArrayList<Byte>();

            while (in.available() > 0)
                list_of_begin.add((byte) in.read());
            byte[] to_send = new byte[list_of_begin.size()];
            for (int i = 0; i < list_of_begin.size(); i++)
                to_send[i] = list_of_begin.get(i);


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





    private byte[] ConvertToBinaryArray(byte tmp, int size) {
        int intNum = (int) tmp;
        if (intNum < 0)
            intNum = intNum + 256;
        byte[] temp = new byte[size];
        for (int i = size-1; i >=0; i--) {
            temp[i] = (byte) (intNum % 2);
            intNum = intNum / 2;
        }
        return temp;
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


}
