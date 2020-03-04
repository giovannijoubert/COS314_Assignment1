import java.io.*; 
import java.util.*; 

public class atsp {
    private static int ATSPDimension;
    private static int SameCity = -1;
    private static int[][] ATSPMatrix;


    public static void main(String[] args) { 

        //check if .atsp file has been provided
        if(args.length < 1 || !args[0].endsWith(".atsp")){ 
            System.out.println("You need to provide a valid .atsp file as your first parameter.");
            System.exit(0);
        }

        //readATSPFile and convert to ATSPMatrix
        readATSPFile(args[0]);


        //Console Output
        System.out.println("DIMENSION: " + ATSPDimension);
        System.out.println("SAME CITY: " + SameCity);
        System.out.println("MATRIX:"); 
        for(int i = 0; i < ATSPDimension; i++){
            for(int j = 0; j < ATSPDimension; j++){
                System.out.print(ATSPMatrix[i][j] + "\t");
            }
            System.out.println("");
        }
                
 
    }

    //readATSPFile and convert to ATSPMatrix
    public static void readATSPFile(String fileName){
        BufferedReader rd = null;
        try {
            // Open the file for reading.
            rd = new BufferedReader(new FileReader(new File(fileName)));
    
            Boolean readMatrix = false;
            int row = 0;
            int col = 0;

            //read each line
            String inputLine = null;
            while((inputLine = rd.readLine()) != null){
                //Stop reading at EOF
                if(inputLine.contains("EOF"))
                break;

                //Save Matrix Dimension
                if(inputLine.contains("DIMENSION: ")){
                    ATSPDimension = Integer.parseInt(inputLine.substring(inputLine.indexOf(":") + 1, inputLine.length()).replace(" ",""));
                    ATSPMatrix = new int[ATSPDimension][ATSPDimension]; 
                }

                if(readMatrix){
                    //get all of the tokens out of one input line
                    StringTokenizer st = new StringTokenizer(inputLine);
                    while (st.hasMoreTokens()) {
                        ATSPMatrix[row][col] = Integer.parseInt(st.nextToken());

                        //Get diagonal identifier (can be 0, in which case it's not unique) 
                        if(SameCity == -1)
                            SameCity = ATSPMatrix[row][col];

                        col++;

                        //only go to next row when all cols filled (input lines not square)
                        if(col == ATSPDimension){
                            col = 0;
                            row++;
                        }
                    }
                }
                    
                //Start reading Matrix on next pass
                if(inputLine.contains("EDGE_WEIGHT_SECTION"))
                    readMatrix = true;
            }

        }
        catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
        finally {
            // Close the file.
            try {
                rd.close();
            }
            catch (IOException ex) {
                System.err.println("An IOException was caught!");
                ex.printStackTrace();
            }
        }
    }
    
    
}