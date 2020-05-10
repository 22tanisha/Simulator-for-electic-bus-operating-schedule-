package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Screen2Controller implements Initializable{
	
		static ArrayList<Double> east = new ArrayList<>();
	    static ArrayList<Double> west = new ArrayList<>();
	    static HashMap<Integer, ArrayList<ArrayList<Double>>> hasmapBus = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
	    static Screen3Controller controller;
	    static HashMap<Integer, Integer> hashmapBusBatteryRemain = new HashMap<>();
	    static HashMap<Integer, String> hashmapBusLastTime = new HashMap<>();
	    static HashMap<Integer, Double> busStartTime = new HashMap<>();
	    static HashMap<Integer, String> busStartside = new HashMap<>();
	
	
	    static HashMap<Integer, ArrayList<ArrayList<Double>>> hasmapEmptyRide = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
	
	
	    static ArrayList<Double> batteryFastEastStart = new ArrayList<>();
	    static ArrayList<Double> batteryFastWestStart = new ArrayList<>();
	    static ArrayList<ArrayList<Double>> batteryFastEast = new ArrayList<>();
	    static ArrayList<ArrayList<Double>> batteryFastWest = new ArrayList<>();
	
	
	    static ArrayList<Double> batteryRegEastStart = new ArrayList<>();
	    static ArrayList<Double> batteryRegWestStart = new ArrayList<>();
	    static ArrayList<ArrayList<Double>> batteryRegEast = new ArrayList<>();
	    static ArrayList<ArrayList<Double>> batteryRegWest = new ArrayList<>();
	
	    static ArrayList<ArrayList<Double>> batteryFastOvernightEast = new ArrayList<>();
	    static ArrayList<ArrayList<Double>> batteryFastOvernightWest = new ArrayList<>();
	    
	    static final int HELIOX_FAST = 450;
	    static final int HELIOX_REG = 50;
	    static final int ABB_FAST = 300;
	    static final int ABB_REG = 100;
	    static final int battery294 = 294;
	    static final int battery394 = 394;
	    static int busNumbers=0;
	    static float charger1Price = 0;
	    static float charger2Price = 0;
	    static float busPrice = 0;
	    static float totalExpenditure = 0;
	
	    static double nextAvailable = 0;
	    static double chargingend = 0;
	    static String fastB="";
	    static String regB="";
	    static int busB=0;
	    
	    static float a,b,c;
	    static String output="";
	
	 	@FXML
	    private VBox screen2;

	    @FXML
	    private TextField busModelText;

	    @FXML
	    private TextField noOfBusText;

	    @FXML
	    private TextField lionelFast;

	    @FXML
	    private TextField lionelSlow;

	    @FXML
	    private TextField mcdonaldsFast;

	    @FXML
	    private TextField mcdonaldsSlow;

	    @FXML
	    private TextField chargerModelText;
	    
	    @FXML
	    private Button btnGenerate;
	    
	    Alert ab = new Alert(AlertType.NONE);

	    @FXML
	    void schedule(ActionEvent event) throws IOException {
	       	
	    	printBusSchedule();
   		 	printChargerSchedule();
   		    printEmptyRide();
	    	try {
	        	
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3.fxml"));
	    	    Parent root = (Parent) loader.load();
	   		 	controller = loader.getController();
	   		 	controller.setOutput(output);
	   		
	   		 	//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

			    Stage stage = new Stage();
			    stage.setScene(new Scene(root));
			    /*stage.setX(primaryScreenBounds.getMinX());
			    stage.setY(primaryScreenBounds.getMinY());
			    stage.setWidth(primaryScreenBounds.getWidth());
			    stage.setHeight(primaryScreenBounds.getHeight());*/
			    stage.setTitle("Operation Schedule");

			    /*Stage stage1 = (Stage) btnGenerate.getScene().getWindow();
			    stage1.close();
			    stage1.setOnCloseRequest(e -> Platform.exit());*/
			    
			    stage.show();

	   		 	
	        }catch (IOException e) {}
	    	//VBox screen3Pane = (VBox) FXMLLoader.load(Main.class.getResource("Screen3.fxml"));
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1){
			lionelFast.setText("1");
			lionelSlow.setText("1");
			mcdonaldsFast.setText("1");
			mcdonaldsSlow.setText("1");
		}
		
		public void assignValues(float aa, float ab, float ac) {
			a =aa;
			b =ab;
			c =ac;
		}
		
		public void setLabelText(String text){
			busModelText.setText(text);
		}
		
		public void setchargerText(String text) {
			chargerModelText.setText(text);
			operationSchedule();
		}
		public void setbusNo() {
			busNumbers=hasmapBus.size();
			noOfBusText.setText(Integer.toString(busNumbers));	
		}
		
		public void operationSchedule() {
			
	        double[] east_s = {4.55, 5.25, 5.57, 6.28, 7.01, 7.33, 8.05, 8.35, 9.06, 9.58, 10.21, 10.44, 11.08, 11.31, 11.54, 12.16, 12.37, 12.58, 13.20, 13.46, 14.13, 14.40, 15.09, 15.38, 16.07, 16.37, 17.07, 17.37, 18.07, 18.38, 19.09, 19.40, 20.11, 20.42, 21.12, 21.42, 22.12, 22.42, 23.11, 23.40, 24.03, 24.20};
	        double[] west_s = {5.22, 5.40, 6.00, 6.22, 6.47, 7.17, 8.15, 8.44, 9.15, 9.46, 10.17, 10.48, 11.18, 11.49, 12.18, 12.47, 13.15, 13.43, 14.11, 14.39, 15.08, 15.37, 16.06, 16.35, 17.05, 17.32, 18.01, 18.27, 18.53, 19.15, 19.29, 19.37, 19.45, 19.53, 20.01, 20.09, 20.17, 20.25, 20.33, 20.41, 20.49, 20.58, 21.07, 21.16, 21.25, 21.33, 21.42, 21.51, 22.00, 22.09, 22.19, 22.30, 22.42, 22.56, 23.13, 23.35, 23.59, 24.26, 24.53, 25.20};

	        for (double d : east_s) east.add(d);
	        for (double d : west_s) west.add(d);

	        int ch_man = 0;
	        //output+=);
	        String temp_ch = chargerModelText.getText();
	        System.out.println(temp_ch);
	        if(temp_ch.equals("Heliox")) {
	        	ch_man = 1;
	        }
	        else {
	        	ch_man = 2;
	        }
	         
	        int ch_bat = 0;
	        String temp_bat = busModelText.getText();
	        System.out.println(temp_bat);
	        if(temp_bat.equals("Novabus LFSE+ 294kW")){
	        	ch_bat = 1; 
	    	}
	        else {
	        	ch_bat = 2;
	        }
	    
	        System.out.println(ch_man);
	        System.out.println(ch_bat);
	        busSchedule(ch_man, ch_bat);
	        setbusNo();
	        setHasmapEmptyRide(east_s, west_s);
	        
	        overNightCharging(1, 1, 1, 1, ch_man, ch_bat);
	        costCalculation(a, b, c, 2, 2, hasmapBus.size());
		}
		
		
		static void printEmptyRide() {
	    	
	    	//hasmapEmptyRide
	    	output+="\n\n\n***********\nEmpty Rides\n***********";
	    	for(int i: hasmapEmptyRide.keySet())
	    	{
	    		output+="\nBus "+i+"\n------";
	    		if(!hasmapEmptyRide.get(i).get(0).isEmpty()) {
	    			output+="\nEast - "+hasmapEmptyRide.get(i).get(0);
	    		}
	    		if(!hasmapEmptyRide.get(i).get(1).isEmpty()) {
	    			output+="\nWest - "+hasmapEmptyRide.get(i).get(1);
	    		}
	    		output+="\n";
	    		
	    	}
	    	
	    }
		
		static void  printChargerSchedule()
	    {
	        /*
	           output+=batteryRegEast);
	        output+=batteryRegEastStart);
	        output+=batteryFastEast);
	        output+=batteryFastEastStart);
	         */

	        output+="\n\n\n********************************************";
	        output+="\n            Charger Schedule";
	        output+="\n********************************************";


	        output+="\n\n";
	        output+="\nCharger Id : "+regB+"/East";
	        output+="\n---------------------------------------------";
	        output+="\nStart Time\tEnd Time\tBus Id\tNext Trip";
	        output+="\n---------------------------------------------";
	        for(int i=0;i<batteryRegEast.size();i++)
	        {
	            double x=0;
	            int  busvar=  (int) Math.round(batteryRegEast.get(i).get(2));
	            double next = batteryRegEast.get(i).get(1);

	            for(int j=0;j<hasmapBus.get(busvar).get(0).size();j++)
	            {
	                if(hasmapBus.get(busvar).get(0).get(j)>=next)
	                {
	                    x=hasmapBus.get(busvar).get(0).get(j);
	                    break;
	                }
	            }
	            output+="\n" + batteryRegEast.get(i).get(0)+"  \t\t"+batteryRegEast.get(i).get(1)+"\t\t  "+busvar+"\t    "+x+"/West";

	        }



	        output+="\n\n";
	        output+="\nCharger Id : "+fastB+"/East";
	        output+="\n---------------------------------------------";
	        output+="\nStart Time\tEnd Time\tBus Id\tNext Trip";
	        output+="\n---------------------------------------------";
	        Collections.sort(batteryFastEastStart);

	        int sizz=batteryFastEastStart.size();
	        for(int i=0;i<sizz;i++) {
	            int index = 0;

	            double t = batteryFastEastStart.get(0);
	            for (int j = 0; j < batteryFastEast.size(); j++) {
	                if (t == batteryFastEast.get(j).get(0)) {
	                    index = j;
	                    break;
	                }
	            }


	            double x = 0;
	            int busvar = (int) Math.round(batteryFastEast.get(index).get(2));
	            double next = batteryFastEast.get(index).get(1);

	            for (int j = 0; j < hasmapBus.get(busvar).get(0).size(); j++) {
	                if (hasmapBus.get(busvar).get(0).get(j) >= next) {
	                    x = hasmapBus.get(busvar).get(0).get(j);
	                    break;
	                }
	            }
	            output+="\n" + batteryFastEast.get(index).get(0)+"  \t\t"+batteryFastEast.get(index).get(1)+"\t\t  "+busvar+"\t    "+x+"/West";

	            batteryFastEastStart.remove(0);
	        }




	        output+="\n\n";
	        output+="\nCharger Id : "+regB+"/West";
	        output+="\n---------------------------------------------";
	        output+="\nStart Time\tEnd Time\tBus Id\tNext Trip";
	        output+="\n---------------------------------------------";
	        for(int i=0;i<batteryRegWest.size();i++)
	        {
	            double x=0;
	            int  busvar=  (int) Math.round(batteryRegWest.get(i).get(2));
	            double next = batteryRegWest.get(i).get(1);

	            for(int j=0;j<hasmapBus.get(busvar).get(1).size();j++)
	            {
	                if(hasmapBus.get(busvar).get(1).get(j)>=next)
	                {
	                    x=hasmapBus.get(busvar).get(1).get(j);
	                    break;
	                }
	            }
	            output+= "\n" + (Math.round(( batteryRegWest.get(i).get(0)) * 100.0) / 100.0 )   +"  \t\t"+(Math.round(( batteryRegWest.get(i).get(1)) * 100.0) / 100.0 )+"\t\t  "+busvar+"       "+x+"/East";

	        }

	        output+="\n\n";
	        output+="\nCharger Id : "+fastB+"/West";
	        output+="\n---------------------------------------------";
	        output+="\nStart Time\tEnd Time\tBus Id\tNext Trip";
	        output+="\n---------------------------------------------";
	        Collections.sort(batteryFastWestStart);

	         sizz=batteryFastWestStart.size();
	        for(int i=0;i<sizz;i++) {
	            int index = 0;

	            double t = batteryFastWestStart.get(0);
	            for (int j = 0; j < batteryFastWest.size(); j++) {
	                if (t == batteryFastWest.get(j).get(0)) {
	                    index = j;
	                    break;
	                }
	            }
	            double x = 0;
	            int busvar = (int) Math.round(batteryFastWest.get(index).get(2));
	            double next = batteryFastWest.get(index).get(1);

	            for (int j = 0; j < hasmapBus.get(busvar).get(1).size(); j++) {
	                if (hasmapBus.get(busvar).get(1).get(j) >= next) {
	                    x = hasmapBus.get(busvar).get(1).get(j);
	                    break;
	                }
	            }
	            output+= "\n" + batteryFastWest.get(index).get(0)+"  \t\t"+batteryFastWest.get(index).get(1)+"\t\t  "+busvar+"\t    "+x+"/East";

	            batteryFastWestStart.remove(0);
	        }
	    }

		
		static  void printBusSchedule(){

	        
			output+="\n*************************************************************************************";
	           output+="\n                                  Bus Schedule";
	           output+="\n*************************************************************************************";

	        output+="\n\nBus Battery size = "+busB;
	        for(int i:busStartside.keySet())
	        {
	            output+="\n\n";
	            output+="\nBus Id : "+i;
	            output+="\n------------------------------------------------------------------------------------------";
	            output+="\nStation\t\t\t\t\t  Charger\t      Start Time  \tEnd Time \tBattery Capacity";
	            output+="\n------------------------------------------------------------------------------------------";
	            String s="";
	           // String s=busStartside.get(i)+"\t";



	            if(busStartside.get(i).equals("east")) {
	                boolean FlagForRegCharge=false;

	                for (int j = 0; j < batteryRegEast.size(); j++) {
	                    if (batteryRegEast.get(j).get(2) == i) {
	                        s += "\nEast/Terminus Macdonald\t  " + regB + "\t\t" +  (Math.round((batteryRegEast.get(j).get(0)) * 100.0) / 100.0 )   + "\t\t " + (Math.round((batteryRegEast.get(j).get(1)) * 100.0) / 100.0 )   + "       " + busB;
	                        FlagForRegCharge = true;
	                        break;
	                    }
	                }

	                if(FlagForRegCharge==false)
	                {
	                    for (int j = 0; j < batteryFastEast.size(); j++) {
	                        if (batteryFastEast.get(j).get(2) == i) {
	                            s += "\nEast/Terminus Macdonald\t  " + regB + "\t\t" + (Math.round((batteryFastEast.get(j).get(0)) * 100.0) / 100.0 )   + "\t\t " + (Math.round((batteryFastEast.get(j).get(1)) * 100.0) / 100.0 ) + "       " + busB;
	                            break;
	                        }
	                    }

	                }
	            }else {
	                boolean FlagForRegCharge=false;

	                    for (int j = 0; j < batteryRegWest.size(); j++) {
	                        if (batteryRegWest.get(j).get(2) == i) {
	                            s += "\nWest/Lionel-Groulx\t\t " + regB + "\t\t    " +   (Math.round((batteryRegWest.get(j).get(0)) * 100.0) / 100.0 ) + "\t\t  " +  (Math.round((batteryRegWest.get(j).get(1)) * 100.0) / 100.0 ) + "\t\t  " + busB;
	                            FlagForRegCharge=true;
	                            break;
	                        }
	                    }

	                if(FlagForRegCharge==false)
	                {
	                    for (int j = 0; j < batteryFastWest.size(); j++) {
	                        if (batteryFastWest.get(j).get(2) == i) {
	                            s += "\nWest/Lionel-Groulx\t\t  " + regB + "\t\t    " +  (Math.round((batteryFastWest.get(j).get(0)) * 100.0) / 100.0 )  + "\t\t  " + (Math.round((batteryFastWest.get(j).get(1)) * 100.0) / 100.0 ) + "\t\t  " + busB;
	                        break;
	                        }
	                    }

	                }


	            }
	            output+=s;


	            ArrayList<Double> chargeeastside = new ArrayList<>();
	            for(int j=0;j<batteryFastEast.size();j++)
	            {
	                if(batteryFastEast.get(j).get(2)==i)
	                {
	                    chargeeastside=batteryFastEast.get(j);
	                    break;
	                }

	            }
	            ArrayList<Double> chargewestside =new ArrayList<>();
	            for(int j=0;j<batteryFastWest.size();j++)
	            {
	                if(batteryFastWest.get(j).get(2)==i)
	                {
	                    chargewestside=batteryFastWest.get(j);
	                    break;
	                }
	            }

	            ArrayList<Double> eastSideTimes= new ArrayList<>();
	            for(double j:hasmapBus.get(i).get(0))
	            {
	                eastSideTimes.add(j);
	            }
	            //eastSideTimes= hasmapBus.get(i).get(0);
	            ArrayList<Double> westSideTimes= new ArrayList<>();
	           // westSideTimes= hasmapBus.get(i).get(1);
	            for(double j:hasmapBus.get(i).get(1))
	            {
	                westSideTimes.add(j);
	            }

	            int bat_cap=busB;

	            int counter=eastSideTimes.size()+westSideTimes.size();
	            String sideFlag=busStartside.get(i);
	            for(int k=0;k<counter;k++)
	            {
	                if(sideFlag.equals("east"))
	                {

	                    if(bat_cap<40){
	                        output+="\nEast/Terminus Macdonald\t  "+fastB+"\t\t\t"+chargeeastside.get(0)+"\t\t "+  (Math.round((chargeeastside.get(1)) * 100.0) / 100.0 )  +"\t\t\t"+busB;
	                        output+="\nEast/Terminus Macdonald\t  "+"  -\t\t"+"\t\t"+eastSideTimes.get(0)+"\t\t "+   (Math.round(((eastSideTimes.get(0)+1)) * 100.0) / 100.0 )     +"       "+(busB-40);
	                        bat_cap=busB;

	                    }else{
	                        output+="\nEast/Terminus Macdonald\t  "+"  -\t\t"+"\t\t"+eastSideTimes.get(0)+"\t\t "+   (Math.round(((eastSideTimes.get(0)+1)) * 100.0) / 100.0 ) +"       "+(bat_cap-40);
	                    }
	                    eastSideTimes.remove(0);
	                    sideFlag="west";
	                    bat_cap-=40;
	                }else{

	                    if(bat_cap<40){
	                        output+="\nWest/Lionel-Groulx  \t\t"+fastB+"\t\t  "+chargewestside.get(0)+"\t\t "+chargewestside.get(1)+"\t\t "+busB;
	                        output+="\nWest/Lionel-Groulx\t\t\t"+"    -  "+"   \t\t\t"+westSideTimes.get(0)+"\t\t  "+    (Math.round(((westSideTimes.get(0)+1)) * 100.0) / 100.0 )  +"       "+(busB-40);
	                        bat_cap=busB;

	                    }else{
	                        output+="\nWest/Lionel-Groulx\t\t\t"+"    -  "+"   \t\t\t"+westSideTimes.get(0)+"\t\t  "+ (Math.round(((westSideTimes.get(0)+1)) * 100.0) / 100.0 )+"       "+(bat_cap-40);
	                    }
	                    westSideTimes.remove(0);
	                    sideFlag="east";
	                    bat_cap-=40;
	                }
	            }

	        }



	    }
		
		static void costCalculation(float charger1Price1, float charger2Price1, float busPrice1, int noOfCharger1, int noOfCharger2, int noOfbuses) {
	        charger1Price = noOfCharger1 * charger1Price1;
	        charger2Price = noOfCharger2 * charger2Price1;
	        busPrice = noOfbuses * busPrice1;
	        totalExpenditure = charger1Price + charger2Price + busPrice;
	        output+="***************";
	        output+="\nCost estimation";
	        output+="\n***************";
	        output+="\nTotal Price of Charger  "+regB+"  = "+charger2Price;
	        output+="\nTotal Price of Charger  "+fastB+"  = "+charger1Price;
	        output+="\nTotal Price of Buses  = "+busPrice;
	        output+="\nOverall Capital Expenditure = "+totalExpenditure+"\n\n";

	    }
		
		static double checkround(Double arriivalTime, double diff) {

	        arriivalTime = Math.round((arriivalTime) * 100.0) / 100.0;
	        int intPart = arriivalTime.intValue();
	        double roundeddpart = Math.round((arriivalTime - intPart) * 100.0) / 100.0;


	        if (roundeddpart < 0.60) {
	            arriivalTime = Math.round((arriivalTime) * 100.0) / 100.0;
	        }
	        if (roundeddpart == 0.60) {
	            arriivalTime = Double.valueOf(intPart + 1);
	        }
	        if (roundeddpart > 0.60) {
	            roundeddpart = roundeddpart - 0.60 + diff;
	            intPart -= 1;
	            arriivalTime = Double.valueOf(intPart + roundeddpart);

	        }
	        return arriivalTime;
	    }
		
		static void overNightCharging(int fasteast, int regeast, int fatwest, int regAwest, int ch_man, int ch_bat) {

	        ArrayList<Double> tArray;
	        int eastLast = 0, westLast = 0;
	        ArrayList<Integer> arreast = new ArrayList<>();
	        ArrayList<Integer> arrwest = new ArrayList<>();
	        int charging_time = 0;
	        double charging_time2 = 0;
	        if (ch_man == 1) {
	            if (ch_bat == 1) {
	                charging_time = 1;
	                charging_time2 = 0.10;
	                busB=296;

	            } else {
	                charging_time = 2;
	                charging_time2 = 0.15;
	                busB=394;
	            }
	            fastB="OC 450kW";
	            regB="FAST DC 50kW";

	        } else {
	            if (ch_bat == 1) {
	                charging_time = 2;
	                charging_time2 = 0.15;
	                busB=296;
	            } else {
	                charging_time = 3;
	                charging_time2 = 0.2;
	                busB=394;
	            }
	            fastB="HVC 300PD";
	            regB="HVC 100PU-S";

	        }
	        for (int key : busStartside.keySet()) {
	            if (busStartside.get(key).equals("east")) {
	                arreast.add(key);
	            } else {
	                arrwest.add(key);
	            }
	        }


	        chargingend = busStartTime.get(arreast.get(0));
	        int busvar = arreast.get(0);
	        double chargngstart = chargingend - charging_time;
	        nextAvailable = chargingend + charging_time;
	        tArray = new ArrayList<>();
	        tArray.add(chargngstart);
	        tArray.add(chargingend);
	        tArray.add(Double.valueOf(busvar));


	        batteryRegEast.add(tArray);
	        batteryRegEastStart.add(chargngstart);

	        arreast.remove(0);

	        for (int i : arreast) {
	            double svar = busStartTime.get(i);
	            if (svar >= nextAvailable) {
	                chargngstart = chargingend;
	                chargingend = chargngstart + charging_time;
	                nextAvailable = chargingend + charging_time;


	                tArray = new ArrayList<>();
	                tArray.add(Math.round((chargngstart) * 100.0) / 100.0 );
	                tArray.add(Math.round((chargingend) * 100.0) / 100.0 );
	                tArray.add(Double.valueOf(i));

	                batteryRegEast.add(tArray);
	                batteryRegEastStart.add(chargngstart);
	            }
	        }

	        for (int k = 1; k < batteryRegEast.size(); k++) {

	            int t = (int) Math.round(batteryRegEast.get(k).get(2));
	            arreast.remove(arreast.indexOf(t));
	        }

	        if (!arreast.isEmpty()) {
	            chargingend = busStartTime.get(arreast.get(0));
	            busvar = arreast.get(0);
	            chargngstart = chargingend - charging_time2;
	            nextAvailable = chargingend + charging_time2;
	            chargngstart = checkround(chargngstart, charging_time2);
	            nextAvailable = checkround(nextAvailable, charging_time2);

	            tArray = new ArrayList<>();
	            tArray.add(Math.round((chargngstart) * 100.0) / 100.0);
	            tArray.add(Math.round((chargingend) * 100.0) / 100.0);
	            tArray.add(Double.valueOf(busvar));


	            batteryFastEast.add(tArray);
	            batteryFastOvernightEast.add(tArray);
	            batteryFastEastStart.add(chargngstart);

	            arreast.remove(0);

	            for (int i : arreast) {
	                double svar = busStartTime.get(i);
	                if (svar >= nextAvailable) {
	                    chargngstart = chargingend;
	                    chargingend = chargngstart + charging_time2;
	                    nextAvailable = chargingend + charging_time2;
	                    chargingend = checkround(chargingend, charging_time2);
	                    nextAvailable = checkround(nextAvailable, charging_time2);

	                    tArray = new ArrayList<>();
	                    tArray.add(Math.round((chargngstart) * 100.0) / 100.0);
	                    tArray.add(Math.round((chargingend) * 100.0) / 100.0);
	                    tArray.add(Double.valueOf(i));

	                    batteryFastEast.add(tArray);
	                    batteryFastOvernightEast.add(tArray);
	                    batteryFastEastStart.add(chargngstart);
	                }
	            }
	        }


	        chargingend = busStartTime.get(arrwest.get(0));
	        busvar = arrwest.get(0);
	        chargngstart = chargingend - charging_time;
	        nextAvailable = chargingend + charging_time;
	        tArray = new ArrayList<>();
	        tArray.add(Math.round((chargngstart) * 100.0) / 100.0  );
	        tArray.add( Math.round((chargingend) * 100.0) / 100.0  );
	        tArray.add(Double.valueOf(busvar));


	        batteryRegWest.add(tArray);
	        batteryRegWestStart.add(chargngstart);

	        arrwest.remove(0);


	        for (int i : arrwest) {
	            double svar = busStartTime.get(i);
	            if (svar >= nextAvailable) {
	                chargngstart = chargingend;
	                chargingend = chargngstart + charging_time;
	                nextAvailable = chargingend + charging_time;


	                tArray = new ArrayList<>();
	                tArray.add(((chargngstart) * 100.0) / 100.0 );
	                tArray.add(( (chargingend) * 100.0) / 100.0 );
	                tArray.add(Double.valueOf(i));

	                batteryRegWest.add(tArray);
	                batteryRegWestStart.add(chargngstart);
	            }
	        }

	        for (int k = 1; k < batteryRegWest.size(); k++) {
	            int t = (int) Math.round(batteryRegWest.get(k).get(2));
	            arrwest.remove(arrwest.indexOf(t));
	        }


	        if (!arrwest.isEmpty()) {


	            chargingend = busStartTime.get(arrwest.get(0));
	            busvar = arrwest.get(0);
	            chargngstart = chargingend - charging_time2;
	            nextAvailable = chargingend + charging_time2;
	            chargngstart = checkround(chargngstart, charging_time2);
	            nextAvailable = checkround(nextAvailable, charging_time2);

	            tArray = new ArrayList<>();
	            tArray.add(Math.round((chargngstart) * 100.0) / 100.0 );
	            tArray.add(Math.round((chargingend) * 100.0) / 100.0 );
	            tArray.add(Double.valueOf(busvar));


	            batteryFastWest.add(tArray);
	            batteryFastOvernightWest.add(tArray);
	            batteryFastWestStart.add(chargngstart);

	            arrwest.remove(0);

	            for (int i : arrwest) {
	                double svar = busStartTime.get(i);
	                if (svar >= nextAvailable) {
	                    chargngstart = chargingend;
	                    chargingend = chargngstart + charging_time2;
	                    nextAvailable = chargingend + charging_time2;
	                    chargingend = checkround(chargingend, charging_time2);
	                    nextAvailable = checkround(nextAvailable, charging_time2);

	                    tArray = new ArrayList<>();
	                    tArray.add( Math.round((chargngstart) * 100.0) / 100.0  );
	                    tArray.add( Math.round((chargingend) * 100.0) / 100.0  );
	                    tArray.add(Double.valueOf(i));

	                    batteryFastWest.add(tArray);
	                    batteryFastOvernightWest.add(tArray);
	                    batteryFastWestStart.add(chargngstart);
	                }
	            }
	        }


	        /*output+=batteryRegEast);
	        output+=batteryRegEastStart);
	        output+=batteryFastEast);
	        output+=batteryFastEastStart);

	        output+="\n::::::::::::::::::::\n");
	        output+=batteryRegWest);
	        output+=batteryRegWestStart);
	        output+=batteryFastWest);
	        output+=batteryFastWestStart);*/


	    }

		
		static void setHasmapEmptyRide(double[] east_s, double[] west_s) {

	        ArrayList<Double> easts = new ArrayList<>();
	        ArrayList<Double> wests = new ArrayList<>();
	        for (double d : east_s) easts.add(d);
	        for (double d : west_s) wests.add(d);

	        for (int key : hasmapBus.keySet()) {
	            ArrayList<Double> easttemp = new ArrayList<>();
	            ArrayList<Double> westtemp = new ArrayList<>();

	            ArrayList<Double> temp1 = hasmapBus.get(key).get(0);
	            ArrayList<Double> temp2 = hasmapBus.get(key).get(1);
	            for (double dd : temp1) {
	                if (!easts.contains(dd) && !wests.contains(dd)) {

	                    easttemp.add(dd);
	                }
	            }
	            for (double dd : temp2) {
	                if (!easts.contains(dd) && !wests.contains(dd)) {

	                    westtemp.add(dd);
	                }
	            }
	            if (!westtemp.isEmpty() || !easttemp.isEmpty()) {
	                ArrayList<ArrayList<Double>> tempschedule = new ArrayList<>();
	                tempschedule.add(easttemp);
	                tempschedule.add(westtemp);
	                hasmapEmptyRide.put(key, tempschedule);
	            }
	        }
	    }
		
		static int betterycheck(Double arriivalTime, int batCap, int manufacture, int battery, int bus, String side) {
	        double Stime = Math.round((arriivalTime) * 100.0) / 100.0;

	        if (manufacture == 1) {
	            if (battery == 294) {
	                arriivalTime += 0.03; // 3 minutes
	            } else {
	                arriivalTime += 0.05; // 5 minutes
	            }
	        } else {
	            if (battery == 294) {
	                arriivalTime += 0.02; // 2 minutes
	            } else {
	                arriivalTime += 0.05; // 5 minutes
	            }

	        }
	        int intPart = arriivalTime.intValue();
	        double roundeddpart = Math.round((arriivalTime - intPart) * 100.0) / 100.0;

	        if (roundeddpart < 0.60) {
	            arriivalTime = Math.round((arriivalTime) * 100.0) / 100.0;
	        }
	        if (roundeddpart == 0.60) {
	            arriivalTime = Double.valueOf(intPart + 1);
	        }
	        if (roundeddpart > 0.60) {
	            roundeddpart = roundeddpart - 0.60;
	            intPart += 1;
	            arriivalTime = Double.valueOf(intPart + roundeddpart);

	        }

	        double eTime = arriivalTime;
	        ArrayList<Double> TaRy = new ArrayList<>();
	        TaRy.add(Stime);
	        TaRy.add(eTime);
	        TaRy.add(Double.valueOf(bus));
	        if (side.equals("east")) {
	            batteryFastEast.add(TaRy);
	            batteryFastEastStart.add(Stime);
	        } else {
	            batteryFastWest.add(TaRy);
	            batteryFastWestStart.add(Stime);
	        }
	        batCap = battery;
	        // batteryFastEast

	        return batCap;
	    }
		
		
		public static void busSchedule(int manufacture, int battery) {
	        if (battery == 1) {
	            battery = battery294;
	        } else {
	            battery = battery394;
	        }

	        int bus = 0;
	        ArrayList<ArrayList<Double>> busSchedule;

	        while (!east.isEmpty() || !west.isEmpty()) {

	            int batCap = battery;
	            bus++;
	            busSchedule = new ArrayList<>();
	            ArrayList<Double> scheduleEast = new ArrayList<>();
	            ArrayList<Double> scheduleWest = new ArrayList<>();

	            String listFlag = "";
	            int tt = -1;
	            if (east.isEmpty() && !west.isEmpty()) {
	                listFlag = "east";
	                scheduleEast.add(west.get(0));
	                scheduleWest.add(west.get(0));
	                busStartTime.put(bus, west.get(0));
	                busStartside.put(bus, "west");

	                west.remove(0);
	                tt = 1;
	            }
	            if (west.isEmpty() && !east.isEmpty()) {
	                listFlag = "west";
	                scheduleWest.add(east.get(0));
	                scheduleEast.add(east.get(0));
	                busStartside.put(bus, "east");

	                east.remove(0);
	                tt = 1;
	            }
	            if (east.isEmpty() && west.isEmpty()) {
	                if (listFlag.equals("east")) {
	                    scheduleEast.remove(0);
	                    hashmapBusLastTime.put(bus, (scheduleWest.get(scheduleWest.size() - 1) + 1) + "-east");

	                } else {
	                    scheduleWest.remove(0);
	                    hashmapBusLastTime.put(bus, (scheduleEast.get(scheduleEast.size() - 1) + 1) + "-west");
	                }
	                busSchedule.add(scheduleEast);
	                busSchedule.add(scheduleWest);
	                hasmapBus.put(bus, busSchedule);

	                break;
	            }

	            if (!east.isEmpty() && !west.isEmpty()) {
	                if (east.get(0) < west.get(0)) {
	                    listFlag = "west";
	                    scheduleWest.add(east.get(0));
	                    scheduleEast.add(east.get(0));
	                    busStartTime.put(bus, east.get(0));
	                    busStartside.put(bus, "east");

	                    east.remove(0);
	                    if (east.isEmpty()) {
	                        tt = 0;
	                    }
	                } else {

	                    listFlag = "east";
	                    scheduleEast.add(west.get(0));
	                    scheduleWest.add(west.get(0));
	                    busStartTime.put(bus, west.get(0));
	                    busStartside.put(bus, "west");

	                    west.remove(0);
	                    if (west.isEmpty()) {
	                        tt = 0;
	                    }
	                }
	            }

	            batCap -= 40;

	            int kk = 0;
	            String EmptyFlag = "non";


	            //     output+="\n{}="+scheduleEast);
	            //    output+=scheduleWest);
	            //   output+=east);
	            // output+=west);
	            //output+="\n---");

	            if (!east.isEmpty() && !west.isEmpty()) {
	                while ((((scheduleEast.get(scheduleEast.size() - 1)) + 1) <= west.get(west.size() - 1)) && (((scheduleWest.get(scheduleWest.size() - 1)) + 1) <= east.get(east.size() - 1))) {

	                    if (kk == 0) {

	                        if (listFlag.equals("east")) {
	                            scheduleEast.remove(0);

	                        } else {
	                            scheduleWest.remove(0);

	                        }
	                    }
	                    kk++;

//	                    output+=kk);

	                    //                  output+=scheduleEast);
	//
	                    //                  output+=listFlag+"--=" + listFlag);
	                    //                output+=scheduleWest);

	                    if (listFlag.equals("west")) {

	                        Double previousDeparture = scheduleEast.get(scheduleEast.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;

	                        if (batCap < 40) {
	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "west");
	                        }

	                        for (int i = 0; i < west.size(); i++) {

	                            if (west.get(i) >= arriivalTime)                        // IMP. it can be >= also
	                            {
	                                scheduleWest.add(west.get(i));
	                                west.remove(i);
	                                listFlag = "east";
	                                batCap -= 40;
	                                break;
	                            }
	                        }
	                        if (west.isEmpty()) {
	                            EmptyFlag = "west";
	                            break;
	                        }
	                        //              output+=((scheduleWest.get(scheduleWest.size() - 1)) + 1) >= east.get(east.size() - 1));
	                        //            output+=!east.isEmpty() );
	                        //          output+=(west.size() - east.size()>=4));
	                        //        output+=east.size()+" "+west.size());

	                        if ((((scheduleWest.get(scheduleWest.size() - 1)) + 1) >= east.get(east.size() - 1)) && !east.isEmpty() && (east.size() == 2 && west.size() == 18)) {
	                            while (scheduleWest.get(scheduleWest.size() - 1) <= west.get(west.size() - 1)) {

	                                Double previousDeparture1 = scheduleWest.get(scheduleWest.size() - 1);
	                                Double arriivalTime1 = previousDeparture1 + 1;

	                                if (batCap < 40) {
	                                    batCap = betterycheck(arriivalTime1, batCap, manufacture, battery, bus, "east");
	                                }

	                                scheduleEast.add(arriivalTime1);
	                                batCap -= 40;

	                                Double previousDeparture2 = scheduleEast.get(scheduleEast.size() - 1);
	                                Double arriivalTime2 = previousDeparture2 + 1;

	                                if (batCap < 40) {
	                                    batCap = betterycheck(arriivalTime2, batCap, manufacture, battery, bus, "west");
	                                }

	                                for (int ii = 0; ii < west.size(); ii++) {

	                                    if (west.get(ii) >= arriivalTime2)                        // IMP. it can be >= also
	                                    {
	                                        scheduleWest.add(west.get(ii));
	                                        west.remove(ii);
	                                        batCap -= 40;
	                                        break;
	                                    }
	                                }
	                            }
	                        }

	                    } else {

	                        Double previousDeparture = scheduleWest.get(scheduleWest.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;


	                        if (batCap < 40) {
	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "east");

	                        }

	                        for (int i = 0; i < east.size(); i++) {

	                            if (east.get(i) >= arriivalTime)                        // IMP. it can be >= also
	                            {
	                                scheduleEast.add(east.get(i));
	                                east.remove(i);
	                                listFlag = "west";
	                                batCap -= 40;
	                                break;
	                            }
	                        }
	                        if (east.isEmpty()) {
	                            EmptyFlag = "east";
	                            break;
	                        }

	                    }

	                }
	            }


	            if (east.isEmpty()) {
	                EmptyFlag = "east";

	                while (scheduleWest.get(scheduleWest.size() - 1) <= west.get(west.size() - 1)) {

	                    if (tt == 0) {
	                        scheduleWest.remove(0);
	                        EmptyFlag = "west";
	                        tt = 2;

	                    }
	                    if (tt == 1) {
	                        scheduleEast.remove(0);
	                        EmptyFlag = "east";
	                        tt = 2;
	                    }

	                    if (EmptyFlag.equals("west")) {

	                        Double previousDeparture = scheduleEast.get(scheduleEast.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;


	                        if (batCap < 40) {
	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "west");

	                        }

	                        for (int i = 0; i < west.size(); i++) {

	                            if (west.get(i) >= arriivalTime)                        // IMP. it can be >= also
	                            {
	                                scheduleWest.add(west.get(i));
	                                west.remove(i);
	                                EmptyFlag = "east";
	                                batCap -= 40;
	                                break;
	                            }
	                        }
	                        if (scheduleWest.isEmpty()) {
	                            break;
	                        }

	                    } else {


	                        Double previousDeparture = scheduleWest.get(scheduleWest.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;
	                        // output+=arriivalTime);

	                        if (arriivalTime + 1 >= west.get(west.size() - 1)) {
	                            break;
	                        }
	                        if (batCap < 40) {
	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "east");

	                        }

	                        scheduleEast.add(arriivalTime);
	                        batCap -= 40;
	                        EmptyFlag = "west";

	                    }
	                }
	            }

	            if (west.isEmpty()) {
	                EmptyFlag = "west";

	                while (scheduleEast.get(scheduleEast.size() - 1) <= east.get(east.size() - 1)) {


	                    if (tt == 0) {
	                        scheduleEast.remove(0);
	                        EmptyFlag = "west";
	                        tt = 1;
	                    } else {
	                        scheduleWest.remove(0);
	                        EmptyFlag = "east";
	                    }

	                    if (EmptyFlag.equals("east")) {

	                        Double previousDeparture = scheduleWest.get(scheduleWest.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;


	                        if (batCap < 40) {

	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "east");

	                        }


	                        for (int i = 0; i < east.size(); i++) {

	                            if (east.get(i) >= arriivalTime)                        // IMP. it can be >= also
	                            {
	                                scheduleEast.add(east.get(i));
	                                east.remove(i);
	                                EmptyFlag = "west";
	                                batCap -= 40;
	                                break;
	                            }
	                        }

	                        if (scheduleEast.isEmpty()) {
	                            break;
	                        }

	                    } else {

	                        Double previousDeparture = scheduleEast.get(scheduleEast.size() - 1);
	                        Double arriivalTime = previousDeparture + 1;


	                        if (arriivalTime + 1 >= east.get(east.size() - 1)) {
	                            break;
	                        }

	                        if (batCap < 40) {
	                            batCap = betterycheck(arriivalTime, batCap, manufacture, battery, bus, "west");

	                        }

	                        scheduleWest.add(arriivalTime);
	                        batCap -= 40;
	                        EmptyFlag = "east";

	                    }
	                }
	            }


	            Double max = 0.0;
	            String side = "";

	            if (!scheduleEast.isEmpty() && !scheduleWest.isEmpty()) {
	                Double max2 = Collections.max(scheduleEast);
	                Double max1 = Collections.max(scheduleWest);

	                if (max1 > max2) {
	                    max = max1;
	                    side = "east";
	                } else {
	                    max = max2;
	                    side = "west";
	                }
	            } else if (scheduleEast.isEmpty() && !scheduleWest.isEmpty()) {
	                max = Collections.max(scheduleWest);
	                side = "east";

	            } else if (!scheduleEast.isEmpty() && scheduleWest.isEmpty()) {
	                max = Collections.max(scheduleEast);
	                side = "west";

	            }
	            max += 1;
	            String tmp = max + "-" + side;
	            hashmapBusLastTime.put(bus, tmp);


	            busSchedule.add(scheduleEast);
	            busSchedule.add(scheduleWest);
	            hasmapBus.put(bus, busSchedule);


	            hashmapBusBatteryRemain.put(bus, batCap);

	           /* output+=hasmapBus);
	            output+=batteryFast);
	            output+=);
	            output+=);
	            output+=hashmapBusBatteryRemain);
	            output+=);
	            output+=east);
	            output+=);
	            output+=west);  */
	        }

	    }
		
}
