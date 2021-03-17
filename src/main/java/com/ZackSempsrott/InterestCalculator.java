package com.ZackSempsrott;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.math.RoundingMode.HALF_DOWN;

public class InterestCalculator {


    public static void main(String[] args) {


        //Welcome to the calculator
        System.out.println("Welcome to the Compounding Interest Calculator!");

        //prompt user input initial deposit amount and if they want to make additional monthly deposits
        System.out.print("Please input your initial deposit as a number: ");
        Scanner input = new Scanner(System.in);
        String initialDepositInput = input.nextLine();
        BigDecimal initialDeposit = new BigDecimal(initialDepositInput);

        System.out.print("Would you like to make additional monthly deposits? (y/n): ");
        String monthlyDepositResponse = input.nextLine();
        BigDecimal monthlyDeposit = BigDecimal.ZERO;
        if(monthlyDepositResponse.equals("y")){
            System.out.print("Please enter monthly deposit amount as a number: ");
            String monthlyDepositInput = input.nextLine();
            monthlyDeposit = new BigDecimal(monthlyDepositInput);
        }

        //prompt user for number of years to hold investment
        System.out.print("Please input a number for years you want the investment to grow: ");
        String yearsInput = input.nextLine();
        int yearsValue = Integer.parseInt(yearsInput);
        BigDecimal numberOfYears = new BigDecimal(yearsValue);
        int totalYears = numberOfYears.intValue();


        //prompt user for compound frequency
        System.out.print("Enter the compound frequency.");
        System.out.print("(Please type 'daily', 'semi-monthly', 'monthly', 'semi-annually', or 'annually': ");
        String compoundInput = input.nextLine();
        double compoundFrequency = 0.0;
        if (compoundInput.equalsIgnoreCase("daily")){
            compoundFrequency = 365.25;
        }
        else if (compoundInput.equalsIgnoreCase("semi-monthly")){
            compoundFrequency = 24.0;
        }
        else if (compoundInput.equalsIgnoreCase("monthly")){
            compoundFrequency = 12.0;
        }
        else if (compoundInput.equalsIgnoreCase("semi-annually")){
            compoundFrequency = 2.0;
        }
        else if (compoundInput.equalsIgnoreCase("annually")){
            compoundFrequency = 1.0;
        }


        //prompt user for return %
        System.out.print("Please enter your expected annual return percentage as a number with no % symbol: ");
        String returnInput = input.nextLine();
        double rateOfReturn = Double.parseDouble(returnInput);
        double returnPercentage = rateOfReturn / 100;

        //calculate amount of interest, based off of compound frequency and return %
        BigDecimal exponentialReturnPercentage = BigDecimal.valueOf(Math.pow(1 + (returnPercentage / compoundFrequency), compoundFrequency * totalYears));
        BigDecimal interestEarnedOnPrincipal = initialDeposit.multiply(exponentialReturnPercentage).subtract(initialDeposit);

        final double depositsPerYear = 12; //can only make monthly deposits right now

        //this is part of the compounding formula. broke it into two parts since it's so long.
        BigDecimal calculationFromPart1OfFormula = (exponentialReturnPercentage.subtract(BigDecimal.ONE)).divide(BigDecimal.valueOf(returnPercentage / compoundFrequency), 2, HALF_DOWN);
        //this finishes out the interest calculation
        BigDecimal interestEarnedOnMonthlyDeposits = monthlyDeposit.multiply(BigDecimal.valueOf((depositsPerYear / compoundFrequency)).multiply(calculationFromPart1OfFormula));




        //calculate total of initial investment + monthly deposits
        BigDecimal totalOfMonthlyDeposits = monthlyDeposit.multiply(numberOfYears.multiply(BigDecimal.valueOf(12)));
        BigDecimal totalMonthlyInterest = interestEarnedOnMonthlyDeposits.subtract(totalOfMonthlyDeposits);
        BigDecimal totalAllDeposits = totalOfMonthlyDeposits.add(initialDeposit);
        BigDecimal totalInterest = totalMonthlyInterest.add(interestEarnedOnPrincipal).setScale(2, HALF_DOWN);




        //add interest to total of all deposits
        BigDecimal finalAmount = totalAllDeposits.add(totalInterest);




        //print out total money accrued, plus a break down of principal deposit, monthly deposits, interest earned
        System.out.println();
        System.out.println("Over the course of " + yearsValue + " years, you built up $" + finalAmount + ".");
        System.out.println("You started with $" + initialDeposit + ".");
        System.out.println("You deposited an additional $" + totalOfMonthlyDeposits  + ".");
        System.out.println("You earned $" + totalInterest + " in interest.");



    }
}
