package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;


public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private List<Byte> list;//list of bytes by this order: rows,cols, startRow,startCol, endRow, endCol and then the maze itself. sepertated by 0


    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
        list = new LinkedList<Byte>();
    }

    @Override
    public void write(int b) {
        //nothing to do yet

    }

    ;

    /**
     * function to create a converted maze from byte array to maze
     * 0 - 0
     * 1 - 1
     * 2 - start
     * 3 - end
     * 4 - new line zero
     * 5 - new line one
     * 6 - new line start
     * 7 - new line end
     */

    @Override
    public void write(byte[] b) throws IOException {

        compress(b);
        byte[] new_array= convert_to_ByteArray();
        try {
            out.flush();
            out.write(new_array);
            //printArray(new_array);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //we will do 8 bits and translate it to 00000101 for 5 for example
        //any number lower than 0 will be special key for start row, col, end, etc,,, rows, cols bla bla
    }


    private void compress(byte[] b) {
        int cols = 0;
        int rows = 0;
        int counter = 0;
        int index = 0;
        int startRow = 0;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;
        if (b[0] == 1)
            list.add((byte) 0);
        for (int i = 1; i < b.length; i++) {
            if (b[i] == 0 || b[i]==3 || b[i]==2 || b[i]==5) {
                if (counter == 255) {
                    list.add((byte) counter);
                    list.add((byte) 0);
                    counter = 1;
                }
                else counter++;
                if (b[i + 1] == 1 || b[i+1]==4) {
                    list.add((byte) counter);
                    counter = 0;
                }
            }
            else if (b[i] == 1 || b[i]==4) {
                if (counter == 255) {
                    list.add((byte) counter);
                    list.add((byte) 0);
                    counter = 1;
                }
                else counter++;
                if (b[i + 1] == 0 || b[i+1]==5 ||b[i]==6 || b[i]==2 || b[i]==3 ||b[i]==7 ) {
                    list.add((byte) counter);
                    counter = 0;
                }
            }
            if ((b[i] == 4 || b[i] == 5 || b[i] == 6 || b[i] == 7)) { //to find the rows and cols of maze
                cols = i + 1;
                rows = b.length / cols;
            }
        }
        //find the end and start point
        for (int i = 1; i < b.length; i++) {
            if ((b[i] == 2 || b[i] == 6)) {
                startRow = i / cols;
                startCol = i % cols;
            } else if ((b[i] == 3 || b[i] == 7)) { //to find the end
                if (cols == -1) {
                    endRow = 0;
                    endCol = i;
                } else {
                    endRow = i / cols;
                    endCol = i % cols;
                }
            }
        }
        byte[] end_col_byte = convertIntToByteArray(endCol);
        addBytesToList(end_col_byte);
        //we will add the end row and then a zero seperator
        byte[] end_row_byte = convertIntToByteArray(endRow);
        addBytesToList(end_row_byte);

        //we will add the start col and then a zero seperator
        byte[] start_col_byte = convertIntToByteArray(startCol);
        addBytesToList(start_col_byte);
        //we will add the start  row and then a zero seperator
        byte[] start_row_byte = convertIntToByteArray(startRow);
        addBytesToList(start_row_byte);
    }



    /**
     * convert list to byte array
     * @return byte array
     */
    private byte[] convert_to_ByteArray() {
        byte[] b= new byte[list.size()];
        //System.out.println("writing " + list.size());
        int i=0;
        while (!list.isEmpty()){
            b[i]=list.remove(0);
            i++;
        }
        return b;
    }


        private void addBytesToList(byte[] array) {
            //add a seperator zero
            list.add(0, (byte) 0);
            for (int i=0; i<array.length; i++) {
                list.add(0,array[i]);
            }

        }

        /**
         * helper method- convert int to byte array
         * @param num - to convert
         * @return byte array
         */
        private byte[] convertIntToByteArray(int num) {
            byte[] array;
            if (num==0){
                array = new byte[1];
            }
            else if (num%255 == 0){
                array = new byte[(num/255)];
            }else {
                array = new byte[(num / 255) + 1];
            }
            int tmp = num;
            for (int i=0; i<array.length; i++) {
                if (tmp<=255)  {
                    array[i]=(byte)tmp;
                }
                else{
                    array[i]=(byte)255;
                }
                tmp=tmp-255;
            }

            return array;
        }
}