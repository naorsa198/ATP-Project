package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private List<Byte> list;//list of bytes by this order: rows,cols, startRow,startCol, endRow, endCol and then the maze itself. sepertated by 0




    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        list = new LinkedList<Byte>();
    }


    @Override
    public void write(int b) {
        //nothing to do yet

    }/**
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
    public void write (byte[] b){

        compressLogic(b);
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

    /**
     * this function does the vcompression
     * @param b to compress
     */
    private void compressLogic(byte[] b) {
        //boolean (if they still -1, so we still didn't got the information of thme
        int cols=-1, rows=-1, startRow=-1, startCol=-1, endRow=-1, endCol=-1;
        int i =0;

        while(i<b.length) { //run all over the bytes array
            byte[] tmp = new byte[8];
            for (int j=0; j<8 && i<b.length; j++) { //run for each 8 bits
                if ((cols==-1) && (b[i]==4 || b[i]==5 || b[i]==6 || b[i]==7)) { //to find the rows and cols of maze
                    cols = i + 1;
                    rows = b.length / cols;
                    //System.out.println("FUCK YOU");
                    //System.out.println("from compressor: "+rows + " , " + cols);
                }
                if (startRow==-1 && (b[i]==2 || b[i]==6)) { //to find the start pos
                    //rows - i/cols //cols = i%cols
                    if (cols==-1) {
                        startRow = 0;
                        startCol=i;
                    }
                    else {
                        startRow = i / cols;
                        startCol = i % cols;
                    }
                    b[i] = 0;
                }
                else if (endRow==-1 && (b[i]==3 || b[i]==7)){ //to find the end
                    if (cols==-1) {
                        endRow=0;
                        endCol=i;
                    }else {
                        endRow = i / cols;
                        endCol = i % cols;
                    }
                    b[i]=0;
                }

                //regular case of new line
                else if (b[i]==4)
                    b[i]=0;
                else if (b[i]==5)
                    b[i]=1;

                tmp[j]=b[i];
                i++;
            }
            if (i==b.length && b.length%8!=0) {
                byte[] last = new byte[b.length%8];
                for (int j=0; j<last.length; j++) {
                    last[j]=tmp[j];
                }
                tmp=last;
            }
            byte result = convertBinaryArrayToByte(tmp);
            list.add(result);
        }
        //we will add the end col and then a zero seperator
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

        //we will add the cols and then a zero seperator
        byte[] cols_byte = convertIntToByteArray(cols);
        addBytesToList(cols_byte);
        // we will add the rows and then a zero seperator
        byte[] rows_byte = convertIntToByteArray(rows);
        addBytesToList(rows_byte);

//        System.out.println("compressed rows:");
//        printArray(rows_byte);
//        System.out.println("compressed cols:");
//        printArray(cols_byte);
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

    /**
     * convert binary array to bytes array
     * @param tmp array of binary array
     * @return byte
     */
    private byte convertBinaryArrayToByte(byte[] tmp) {
        int int_result = 0;
        int ZeroOrOne=0;
        double power = 0;
        for (int i=tmp.length-1; i>=0; i--){
            if (tmp[i]==0)
                ZeroOrOne=0;
            else
                ZeroOrOne=1;
            int_result = int_result + ZeroOrOne * (int)Math.pow(2,power);
            power++;
        }
        byte result = (byte)int_result;
        return result;
    }
}
