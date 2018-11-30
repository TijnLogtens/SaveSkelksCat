import java.util.Scanner;

public class Timer {

public static void main(String[] args) {

long startTime = System.currentTimeMillis();
 
long endTime = System.currentTimeMillis();
 
long costTime = (endTime - startTime)/1000;
 
System.out.println(costTime + "s");

}
}
