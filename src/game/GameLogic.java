package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;


public class GameLogic {


    public static int roll(){
        return (int) (Math.random() * 6) + 1;
    }

    public static int[] getScores(int[] dices){
        int[] res = new int[13];
        int[] numbers = new int[6];
        int total = 0;
        //add
        int flag=0;
        for (int i = 0; i < 5; i++) {
            numbers[dices[i]-1] ++;
            total += dices[i];
        }
        int max = 0;
        //1-6 logic
        for (int i = 0; i < 6; i++) {
            res[i] = numbers[i] * (i+1);
            if(max < numbers[i]){
                max = numbers[i];
            }
        }
        //3 of a kind logic
        if(max>=3){
            res[6] = total;
        }
        //4 of a kind logic
        if(max>=4){
            res[7] = total;
        }
        //Full House logic
        if(max==3){
            for (int i = 0; i < 6; i++) {
                if(numbers[i] == 2){
                    res[8] = 25;
                    break;
                }
            }
        }
        //Small Straight logic
        if((numbers[2]!=0 && numbers[3]!=0)){
            if((numbers[0]!=0 && numbers[1]!=0) ||
                    (numbers[1]!=0 && numbers[4]!=0) ||
                    (numbers[4]!=0 && numbers[5]!=0)){
                res[9] = 30;
            }
        }
        //Large Straight logic
        if(max==1 && (numbers[0]==0 || numbers[5]==0)){
            res[10] = 40;
        }
    
        if(max==5){
        	flag++;
        	System.out.println("flag:" + flag);
        	if(flag==2)
        	{
        		res[11]=150;
        	}
            res[11] = 50;
            res[8] = 25;
        }
        //chance
        res[12] = total;
        return res;
    }

    public static int getTotalScore(int[] scores){
        return getUpperTotal(scores) + getLowerTotal(scores);
    }

    public static int getUpperScore(int[] scores){
        int total = 0;
        for (int i = 0; i < 6; i++) {
            total += scores[i];
        }
        return total;
    }

    public static int getUpperBonus(int[] scores){
        int total = 0;
        for (int i = 0; i < 6; i++) {
            total += scores[i];
        }
        if(total >= 63){
            return 35;
        }else{
            return 0;
        }
    }

    public static int getUpperTotal(int[] scores){
        return getUpperScore(scores) + getUpperBonus(scores);
    }

    public static int getLowerTotal(int[] scores){
        int total = 0;
        for (int i = 6; i < scores.length; i++) {
            total += scores[i];
        }
        return total;
    }

    public static boolean isUpperDone(int[] scores){
        for (int i = 0; i < 6; i++) {
            if(scores[i] == -1){
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerDone(int[] scores){
        for (int i = 6; i < scores.length; i++) {
            if(scores[i] == -1){
                return false;
            }
        }
        return true;
    }


    //serialization
    public static String serializeToString(Object obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(obj);
        String str = byteOut.toString("UTF-8");
        
        return str;
    }

    //deserialization
    public static Object deserializeToObject(String str) throws Exception{
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str.getBytes("UTF-8"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        Object obj =objIn.readObject();
        return obj;
    }

}
