package com.larrygames.equationsolver;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lawrence on 2017-11-26.
 */

public class Calculator {
	public boolean error = false;
	private String pi = "\u03C0";
	private List<String> equation;
	private boolean infinity = false;
public double solve(String equationstr) {

		equation = Arrays.asList(equationstr.split(""));//creates the list
		equation = new ArrayList<String>(equation);//makes it changeable
		equation = deleteEmpty(equation);//gets rid of spaces
		//checks if it is solved
		while (!checkIfNum(makeString(equation))) {
			//e and pi
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals(pi)||equation.get(index).equals("e")) {
					equation = constant(equation,index);
				}
			}
			//abs
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals("|")) {
					equation = abs(equation,index);
				}

			}
			//sin cos tan
			for (int index =0; index<equation.size();index++){
				if (equation.get(index).equals("s")&&equation.get(index+1).equals("i")&&equation.get(index+2).equals("n")){
					equation = sin(equation,index,false);
				}
				else if (equation.get(index).equals("c")&&equation.get(index+1).equals("o")&&equation.get(index+2).equals("s")){
					equation = cos(equation,index,false);
				}else if (equation.get(index).equals("t")&&equation.get(index+1).equals("a")&&equation.get(index+2).equals("n")){
					equation = tan(equation,index,false);
					if (infinity == true){
						MainActivity.setInfinity(true);
						infinity = false;
					}
				}
				else if (equation.get(index).equals("a")&&equation.get(index+1).equals("s")&&equation.get(index+2).equals("i")&&equation.get(index+3).equals("n")){
					System.out.println(equation);
					equation = sin(equation,index,true);
				}else if (equation.get(index).equals("a")&&equation.get(index+1).equals("c")&&equation.get(index+2).equals("o")&&equation.get(index+3).equals("s")){
					equation = cos(equation,index,true);
				}else if (equation.get(index).equals("a")&&equation.get(index+1).equals("t")&&equation.get(index+2).equals("a")&&equation.get(index+3).equals("n")){
					equation = tan(equation,index,true);
				}
			}
			//sqrt
			while(equation.contains("\u221A")){
				for (int index=0;index<equation.size();index++) {
						if (equation.get(index).equals("\u221A")) {
							equation = sqrt(equation,index);

						}
					}
				}
			//log
			for (int index=0;index<equation.size();index++) {
					if (equation.get(index).equals("l")&&equation.get(index+1).equals("n")) {
						equation = ln(equation,index);
					}

			}
			//log
			for (int index=0;index<equation.size();index++) {
				if (index+2<equation.size()) {
				if (equation.get(index).equals("l")&&equation.get(index+1).equals("o")&&equation.get(index+2).equals("g")) {
					equation = log(equation,index);
					}
				}
				}
			//fact
			while(equation.contains("!")){
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals("!")) {
					equation = fact(equation,index);
					}
				}}
			//brackets
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals("(")) {
					equation = brack(equation,index,true);
					}

				}

			//power
			while(equation.contains("^") ){
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals("^")) {
					if (equation.get(index).equals("^")) {
					equation = pow(equation,index);
						}}
					}
				}
			//muti and div
			while(equation.contains("*")||equation.contains("/") ){
			for (int index=0;index<equation.size();index++) {
				if (equation.get(index).equals("*")|| equation.get(index).equals("/")) {
					if (equation.get(index).equals("*")) {
					equation = muti(equation,index);
						}
					else {
						equation = div(equation,index);

					}}

			}}
			//add and sub
			while(equation.contains("+")||equation.contains("-") && !isNumber(equation))
				for (int index=0;index<equation.size();index++) {
					if (equation.get(index).equals("+")|| equation.get(index).equals("-")) {
						if (equation.get(index).equals("+")) {
						equation = add(equation,index);
							}
						else {
							if(checkIfNum(makeString(equation)))
									{break;}
							else if (index ==0) {
								continue;
							}else {
								equation = sub(equation, index);

						}}
					}

				}
				break;
			}

		try {
		return Double.valueOf(makeString(equation));}
		catch(Exception e) {

			error = true;
			return 0.0;
		}

	}
	public List<String> constant(List<String> equation,int index){

	if (equation.get(index).equals(pi)){
		equation.remove(index);
		equation.addAll(index,deleteEmpty(Arrays.asList(String.valueOf(Math.PI).split(""))));
	}
	else if(equation.get(index).equals("e")){
		equation.remove(index);
		equation.addAll(index,deleteEmpty(Arrays.asList(String.valueOf(Math.E).split(""))));
	}

	return equation;
	}
public boolean isNumber(List<String>equation){
	String strEquation ="";
	for (int index=0;index<equation.size();index++){
		strEquation+=equation.get(index);
	}
	try{
		Double.valueOf(strEquation);
		return true;
	}catch(Exception e){
		return false;
	}
}
public List<String> sqrt(List<String>equation,int index){
	Double ans;
	String num="";

	num = makeString(brack(equation,index+1,false));
	num = String.valueOf(solve(num));
	equation = brack(equation,index+1,true);
	ans= Math.sqrt(Double.valueOf(num));
	equation = deleteEquation(index,"",num,equation);
	equation.addAll(isNegative(equation,ans,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(ans).split(""))));
	return equation;
}
public List<String> log(List<String> equation,int index){
	String num1 = "";
	String base = "";
	Double product;
	//finding the first number
	if (equation.get(index+3).equals("(")) {


			equation.add(index+3,"0");
			equation.add(index+3,"1");}
	System.out.println(equation);
	for (int baseIndex=index+3;baseIndex<equation.size();baseIndex++) {

		if(checkIfNum(equation.get(baseIndex)) || equation.get(baseIndex).equals(".")) {
			base += equation.get(baseIndex);
		}
		if (equation.get(baseIndex).equals("(")) {



			if(!equation.contains(")")) {
				equation.add(")");
			}
			num1 = makeString(brack(equation,baseIndex,false));
			num1 = String.valueOf(solve(num1));
			equation = brack(equation,baseIndex,true);
			break;
		}
		//else {break;}
	}//finding the second number
	//deleting the old equation

	for (int deleteIndex=index+2+base.length()+num1.length();deleteIndex>=index;deleteIndex--) {
		equation.remove(deleteIndex);
	}

	product =Math.log10(Double.valueOf(num1))/Math.log10(Double.valueOf(base));
	equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
	return equation;
}

	public List<String> ln(List<String> equation,int index){
		String num1 = "";
		Double product;
		//finding the first number

		num1 = makeString(brack(equation,index+2,false));
		num1 = String.valueOf(solve(num1));
		equation = brack(equation,index+2,true);


		for (int deleteIndex=index+1+num1.length();deleteIndex>=index;deleteIndex--) {
			equation.remove(deleteIndex);
		}

		product = Math.log(Double.valueOf(num1));
		equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
		return equation;
	}
	public List<String> sin(List<String> equation,int index, boolean inverse){
		String num1 = "";
		Double product;
		double angle;
		int delIndex;
		int startIndex = 0;
		//finding the first number
		if (!inverse){
			startIndex = 3;
			delIndex = index+2+num1.length();
			}
		else{
			startIndex = 4;
			delIndex = index+3+num1.length();
		}
		num1 = makeString(brack(equation,index+startIndex,false));
		num1 = String.valueOf(solve(num1));
		equation = brack(equation,index+startIndex,true);
		if (!inverse){
		delIndex = index+2+num1.length();}
		else{
			delIndex = index+3+num1.length();
		}
		for (int deleteIndex=delIndex;deleteIndex>=index;deleteIndex--) {
			equation.remove(deleteIndex);
		}

		if (!inverse){
			angle = Double.valueOf(num1);
			if (!MainActivity.getIsRad()){
				angle = Math.toRadians(angle);
			}
			product = Math.round(100.0*Math.sin(angle))/100.0;}
		else{
			System.out.println(num1);
			product = Math.asin(Double.valueOf(num1));
			System.out.println(product);
			if  (!MainActivity.getIsRad()){
				product = Math.toDegrees(product);
			}
		}
		equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
		return equation;
	}
	public List<String> cos(List<String> equation,int index,boolean inverse){
		String num1 = "";
		Double product;
		double angle;
		//finding the first number

		num1 = makeString(brack(equation,index+3,false));
		num1 = String.valueOf(solve(num1));
		equation = brack(equation,index+3,true);


		for (int deleteIndex=index+2+num1.length();deleteIndex>=index;deleteIndex--) {
			equation.remove(deleteIndex);
		}
		angle = Double.valueOf(num1);
		if (!MainActivity.getIsRad()){
			angle = Math.toRadians(angle);
		}
		product = Math.round(100.0*Math.cos(angle))/100.0;
		equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
		return equation;
	}
	public List<String> equationFinder(List<String> equation) {
		int xCount = 0;
		for (int index = 0; index < equation.size(); index++) {
			if (equation.get(index).equals("X")) {
				if (equation.get(index + 1).equals("^")) {
					if (equation.get(index + 2).equals("(")) {
						brack(equation, index + 3, false);

					}
				}
			}
		}
		return equation;
	}
	public List<String> tan(List<String> equation,int index,boolean inverse){
		String num1 = "";
		Double product;
		double angle;
		//finding the first number

		num1 = makeString(brack(equation,index+3,false));
		num1 = String.valueOf(solve(num1));
		equation = brack(equation,index+3,true);


		for (int deleteIndex=index+2+num1.length();deleteIndex>=index;deleteIndex--) {
			equation.remove(deleteIndex);
		}
		angle = Double.valueOf(num1);
		if (!MainActivity.getIsRad()){
			angle = Math.toRadians(angle);

		}
		if (angle == 3*Math.PI/2 || angle == Math.PI/2){
			infinity = true;
			return equation;
		}

			product = Math.round(100.0*Math.tan(angle))/100.0;
		equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
		return equation;
	}
public List<String> fact(List<String> equation,int index){
	String num1 = "";



	Double product;
	//finding the first number
	num1 = firstNum(index,equation);

	//deleting the old equation
	equation = deleteEquation(index,num1,"",equation);
	//finding the answer to the numbers
	product =1.0;
	for (int n= Integer.valueOf(num1); n>0;n--) {
		product*=n;
	}

	equation.addAll(isNegative(equation,product,index,num1), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
	return equation;
}
	public List<String> abs(List<String> equation,int index){
		int brack1 = index;
		int brack2 = 0;
		List<String> brackEquation = new ArrayList<String>();
		Double product; // This will hold the answer.
		//this will find the second abs bracket
		for (int brack2Index=index+1;brack2Index<equation.size();brack2Index++) {
			if (equation.get(brack2Index).equals("|")){
				brack2 = brack2Index;
			}
			else if (brack2Index==equation.size()-1&& !equation.get(brack2Index).equals("|")){//if its missing a end bracket add one.
				equation.add("|");
				brack2=brack2Index+1;
			}
		}
		for (int equationIndex=brack1+1;equationIndex<brack2;equationIndex++) {//solves it

			brackEquation.add(equation.get(equationIndex));
		}

		product = solve(makeString(brackEquation));
		product = Math.abs(product);

		for (int deleteIndex=brack2;deleteIndex>=brack1;deleteIndex--) {
			equation.remove(deleteIndex);
		}
		//finding the answer to the numbers

		equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));

		return equation;
	}


	public List<String> brack(List<String> equation,int index,boolean returnEquation){

	int brack1 = index;
	int brack2 = 0;
	int brackcount=0;
	List<String> brackEquation = new ArrayList<String>();


	Double product;
	//finding the second number
	for (int brack2Index=index+1;brack2Index<equation.size();brack2Index++) {

		if(equation.get(brack2Index).equals("(")) {

			brackcount++;
		}else if (equation.get(brack2Index).equals(")") && brackcount==0) {

			brack2=brack2Index;
			break;

		}else if (equation.get(brack2Index).equals(")")) {
			brackcount--;


		}
		else if (brack2Index==equation.size()-1&& !equation.get(brack2Index).equals(")")){
			equation.add(")");
			brack2=brack2Index+1;
		}

	}//deleting the old equation.

	for (int equationIndex=brack1+1;equationIndex<brack2;equationIndex++) {

		brackEquation.add(equation.get(equationIndex));
	}

	product = solve(makeString(brackEquation));

	if (!returnEquation) {
		return brackEquation;
	}
	for (int deleteIndex=brack2;deleteIndex>=brack1;deleteIndex--) {
		equation.remove(deleteIndex);
	}
	//finding the answer to the numbers

	equation.addAll(isNegative(equation,product,index,"0"), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));

	return equation;
}
public List<String> deleteEmpty(List<String> equation){
	List<String> equationTemp = new ArrayList<String>(equation);
	int spacesDeleted = 0;
	for (int index=0;index<equation.size();index++){
		if (!(index<equation.size()-spacesDeleted)){
			break;
		}
		if (equationTemp.get(index).equals("")){
		equationTemp.remove(index);
		spacesDeleted++;
		continue;}
}return equationTemp;
}
public String firstNum(int index,List<String> equation){
	//finding the first number
	String num1= "";

	for (int num1Index=index-1;num1Index>-1;num1Index--) {
		if(checkIfNum(equation.get(num1Index)) || equation.get(num1Index).equals(".")) {
			num1= equation.get(num1Index)+ num1;

		}
		else if(equation.get(num1Index).equals("-")) {
			num1 = equation.get(num1Index) + num1;
			break;
		}

		else {break;}
	}
	return num1;
}//finding the second number
public String secondNum(int index,List<String> equation){
	String num2 = "";
	for (int num2Index=index+1;num2Index<equation.size();num2Index++) {
		if(checkIfNum(equation.get(num2Index)) || equation.get(num2Index).equals(".")) {
			num2+= equation.get(num2Index);
		}

		else if(equation.get(num2Index).equals("-")&& num2Index==index+1) {
			num2+= "-";//adding the negative in
		}
		else {break;}
	}return num2;}
public List<String> deleteEquation(int index,String num1,String num2,List<String> equation){
	for (int deleteIndex=index+num2.length();deleteIndex>=index-num1.length();deleteIndex--) {

		equation.remove(deleteIndex);}


	return equation;
}
public List<String> pow(List<String> equation,int index){
	String num1 = "";
	String num2 = "";



	Double product;
	num1 = firstNum(index,equation);
	num2 = secondNum(index,equation);
	//deleting the old equation
	equation = deleteEquation(index,num1,num2,equation);
	//finding the answer to the numbers
	product = Math.pow(Double.valueOf(num1),Double.valueOf(num2));
	equation.addAll(isNegative(equation,product,index,num1), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
	return equation;
}
public List<String> div(List<String> equation,int index){
	String num1 = "";
	String num2 = "";
	Double product;
	num1 = firstNum(index,equation);
	num2 = secondNum(index,equation);
	//deleting the old equation

	equation = deleteEquation(index,num1,num2,equation);
	//finding the answer to the numbers
	//finding the answer to the numbers

	product = Double.valueOf(num1)/Double.valueOf(num2);

	equation.addAll(isNegative(equation,product,index,num1), deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));

	return equation;
}
	public List<String> muti(List<String> equation,int index){
		String num1 = "";
		String num2 = "";



		Double product;
		num1 = firstNum(index,equation);
		num2 = secondNum(index,equation);
		//deleting the old equation
		equation = deleteEquation(index,num1,num2,equation);
		//finding the answer to the numbers
		product = Double.valueOf(num1)*Double.valueOf(num2);
		equation.addAll(isNegative(equation,product,index,num1),deleteEmpty(Arrays.asList(String.valueOf(product).split(""))));
		return equation;
	}
	public int isNegative(List<String> equation, double ans,int index,String num1){
		if (equation.size()>0){
			if (!String.valueOf(ans).startsWith("-")) {

				try{
				if (checkIfNum(equation.get(index - num1.length()-1))) {
					this.equation.add(index - num1.length(), "+");
					return index - num1.length() + 1;
				} else {

					return index - num1.length() ;
				}
			}catch(Exception e){return 0;}
}

		}
		return 0;
	}
public List<String> sub(List<String> equation,int index){
	String num1 = "";
	String num2 = "";



	Double diff;
	num1 = firstNum(index,equation);
	num2 = secondNum(index,equation);
	//deleting the old equation
	equation = deleteEquation(index,num1,num2,equation);
	//finding the answer to the numbers
	System.out.println(num1 + num2);
	diff = Double.valueOf(num1)-Double.valueOf(num2);

	equation.addAll(isNegative(equation,diff,index,num1),deleteEmpty(Arrays.asList(String.valueOf(diff).split(""))));
	return equation;
}
public  List<String> add(List<String> equation, int index){
	String num1 = "";
	String num2 = "";



	Double sum;
	num1 = firstNum(index,equation);
	num2 = secondNum(index,equation);
	//deleting the old equation
	equation = deleteEquation(index,num1,num2,equation);
	//finding the answer to the numbers
	System.out.println(equation);
	System.out.println(num1+" "+num2);
	sum = Double.valueOf(num1)+Double.valueOf(num2);

	equation.addAll(isNegative(equation,sum,index,num1), deleteEmpty(Arrays.asList(String.valueOf(sum).split(""))));
	return equation;
}
public  String reverseString(String str) {
	String rstr="";//Backwards string
	String[] astr = str.split("");//String array
	for (int index = str.length()-1; index>-1;index--) {//going backwards in the string adding it to the other
		rstr+=astr[index];
	}return rstr;

}
public  String makeString(List<String> str) {
	String newStr="";
	for(String letter: str) {newStr+=letter;}
	return newStr;
}
public  boolean checkIfNum(String num) {
	try {
		Double.valueOf(num);
		return true;
	}catch (Exception e) {
	return false;}

}
}
