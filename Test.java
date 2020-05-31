import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Test {

	public static ArrayList<Integer> DP_Part1_Read_Month() throws FileNotFoundException {
		ArrayList<Integer> halfPrice = new ArrayList<Integer>();
		int R = 0;
		Scanner myReader = new Scanner(new File("month_demand.txt"));
		myReader.nextLine();
		int counter = 0;
		while (myReader.hasNextInt()) {
			int t = myReader.nextInt();
			if (counter % 2 != 0) {
				halfPrice.add(t);
				R += t;
			}
			counter++;
		}
		halfPrice.add(R);
		return halfPrice;
	}

	public static ArrayList<Integer> DP_Part1_Read_Garaga() throws FileNotFoundException {
		ArrayList<Integer> garrage = new ArrayList<Integer>();
		garrage.add(0);
		Scanner myReader = new Scanner(new File("garage_cost.txt"));
		myReader.nextLine();
		int counter = 0;
		while (myReader.hasNextInt()) {
			int t = myReader.nextInt();
			if (counter % 2 != 0) {
				garrage.add(t);
			}
			counter++;
		}
		return garrage;
	}

	public static void DP_Part1(int month, int d, int p) throws FileNotFoundException {
		ArrayList<Integer> order = DP_Part1_Read_Month();
		ArrayList<Integer> GarageCost = DP_Part1_Read_Garaga();
		int R = order.get(order.size() - 1);
		int[][] table = new int[month + 1][R];
		for (int i = 0; i < R; i++) {
			table[0][i] = GarageCost.get(i);
		}
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < month + 1; i++) {
			for (int j = 0; j < R; j++) {
				int mi = order.get(i - 1) - p + j;

				for (int k = 0; k < mi + 1 && k < R && mi > 0; k++) {
					if (mi > 0) {
						int price = table[i - 1][k] + d * (mi - k) + GarageCost.get(j);
						min = Math.min(price, min);
					} else {
						min = table[i - 1][k] + GarageCost.get(j);
					}
				}
				if (mi <= 0) {
					min = table[i - 1][0] + GarageCost.get(j);
				}
				table[i][j] = min;
				min = Integer.MAX_VALUE;
			}
		}
		int[] finalResultArr = table[month];
		Arrays.sort(finalResultArr);
		System.out.println("DP Results-Cost : " + finalResultArr[0]);
	}

	public static void Greedy_Part1(int month, int d, int p) throws FileNotFoundException {
		ArrayList<Integer> order = DP_Part1_Read_Month();
		ArrayList<Integer> GarageCost = DP_Part1_Read_Garaga();
		int R = order.get(order.size() - 1);
		int[][] table = new int[month + 1][R];
		for (int i = 0; i < R; i++) {
			table[0][i] = GarageCost.get(i);
		}
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < month + 1; i++) {
			for (int j = 0; j < R; j++) {
				int mi = order.get(i - 1) - p + j;

				if (mi > 0) {
					int price = table[i - 1][0] + d * (mi - 0) + GarageCost.get(j);
					min = Math.min(price, min);
				} else {
					min = table[i - 1][0] + GarageCost.get(j);
				}
				if (mi <= 0) {
					min = table[i - 1][0] + GarageCost.get(j);
				}

				table[i][j] = min;

				min = Integer.MAX_VALUE;
			}
		}
		int[] finalResultArr = table[month];
		Arrays.sort(finalResultArr);
		System.out.println("Greedy Result : " + finalResultArr[0]);
	}

	public static int getIndex(Integer[] arr, int number) {
		for (int i = (arr.length - 1); i > 0; i--) {
			if (number == arr[i]) {
				return i;
			}
		}
		return -1;
	}

	public static ArrayList<Integer> sequencielPrice(int B, int x) throws FileNotFoundException {
		ArrayList<Integer> halfPrice = new ArrayList<Integer>();
		Scanner myReader = new Scanner(new File("month_demand.txt"));
		myReader.nextLine();
		int counter = 0;
		while (myReader.hasNextInt()) {
			int t = myReader.nextInt();
			if (counter % 2 != 0) {
				halfPrice.add((B * t) / 2);
			}
			counter++;
		}
		ArrayList<Integer> return_Data = new ArrayList<Integer>();
		for (int i = 0; i < x; i++) {
			if (i != 0) {
				return_Data.add(halfPrice.get(i) + halfPrice.get(i - 1));
			} else {
				return_Data.add(halfPrice.get(i));
			}
		}
		return_Data.add(halfPrice.get(x - 1)); // Last month half
		return return_Data;
	}

	public static ArrayList<int[]> investment(int c, int x) throws FileNotFoundException {
		ArrayList<int[]> invest = new ArrayList<int[]>();
		Scanner myReader = new Scanner(new File("investment.txt"));
		int siz = myReader.nextLine().split("\t").length - 1;
		int counter = 0;
		int[] tempArr = new int[c];
		while (myReader.hasNextInt() && invest.size() < x) {
			if (counter == 0) {
				myReader.nextInt();
			} else if (counter <= c) {
				tempArr[counter - 1] = myReader.nextInt();
			} else if ((counter % (siz + 1)) == 0) {
				counter = 0;
				invest.add(tempArr);
				tempArr = new int[c];
				myReader.nextInt();
			} else {
				myReader.nextInt();
			}
			counter++;
		}
		return invest;
	}

	public static int[] noTransfer(int[] money, int addMoney, int[] faiz) {
		int[] returnArray = new int[faiz.length];
		for (int i = 0; i < faiz.length; i++) {
			returnArray[i] = money[i] + addMoney;
			returnArray[i] = ((returnArray[i] * faiz[i]) / 100) + returnArray[i];
		}
		return returnArray;
	}

	public static ArrayList<int[]> Transfer(int[] money, int addMoney, int[] invest, int commmission,
			int[] noTransferMoney) {
		int[] newMoney = new int[money.length];
		for (int i = 0; i < newMoney.length; i++) {
			newMoney[i] = ((money[i] * commmission) / 100);
			newMoney[i] = money[i] - newMoney[i];
			newMoney[i] = newMoney[i] + addMoney;
		}
		ArrayList<int[]> calculator = new ArrayList<int[]>();

		for (int i = 0; i < newMoney.length; i++) {
			int[] returnArray = new int[invest.length];
			for (int j = 0; j < newMoney.length; j++) {
				if (i != j) {
					int t = (newMoney[i] * invest[j]) / 100 + newMoney[i];
					returnArray[j] = t;
				} else {
					returnArray[j] = noTransferMoney[i];
				}
			}
			calculator.add(returnArray);
		}
		return calculator;
	}

	public static int[] compare(ArrayList<int[]> arrays) {
		int[] returnArray = new int[arrays.size()];
		int[] compare = new int[returnArray.length];
		for (int i = 0; i < returnArray.length; i++) {
			for (int j = 0; j < arrays.get(i).length; j++) {
				compare[j] = arrays.get(j)[i];
			}
			Arrays.sort(compare);
			returnArray[i] = compare[compare.length - 1];
		}
		return returnArray;
	}

	public static void DP_Part2(int month, ArrayList<int[]> invest, ArrayList<Integer> halfPrices, int commission) {

		int[] startMoney = new int[invest.get(0).length];
		for (int i = 0; i < startMoney.length; i++) {
			startMoney[i] = halfPrices.get(0);
		}
		startMoney = noTransfer(startMoney, 0, invest.get(0));
		for (int i = 1; i < month; i++) {
			int[] notMakeTransfer = noTransfer(startMoney, halfPrices.get(i), invest.get(i));
			ArrayList<int[]> makeTransfer = Transfer(startMoney, halfPrices.get(i), invest.get(i), commission,
					notMakeTransfer);
			startMoney = compare(makeTransfer);
		}
		Arrays.sort(startMoney);
		System.out.println("DP Results-Profit: " + startMoney[startMoney.length - 1] + " " + halfPrices.get(month));
	}

	public static void Greedy_Part2(int month, ArrayList<int[]> invest, ArrayList<Integer> halfPrices, int commission) {

		Integer[] arr = new Integer[invest.get(0).length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (halfPrices.get(0) * invest.get(0)[i]) / 100 + halfPrices.get(0);
		}
		int max = Collections.max(Arrays.asList(arr));
		int index = getIndex(arr, max);
		for (int i = 1; i < month; i++) {
			ArrayList<Integer> transfer = new ArrayList<Integer>();
			for (int j = 0; j < invest.get(0).length; j++) {
				if (j != index) {
					transfer.add(invest.get(i)[j]);
				}
			}
			int noTransferMoney = ((arr[index] + halfPrices.get(i)) * invest.get(i)[index]) / 100;
			noTransferMoney = noTransferMoney + arr[index] + halfPrices.get(i);
			int maxTransferInvest = Collections.max(transfer);
			int transferMoney = (arr[index] * commission) / 100;
			transferMoney = (arr[index] - transferMoney) + halfPrices.get(i);
			transferMoney = (transferMoney * maxTransferInvest) / 100 + transferMoney;
			if (transferMoney > noTransferMoney) {
				for (int j = 0; j < arr.length; j++) {
					if (maxTransferInvest == invest.get(i)[j]) {
						index = j;
						arr[index] = transferMoney;
						break;
					}
				}
			} else {
				arr[index] = noTransferMoney;
			}
		}
		Arrays.sort(arr);
		System.out.println("Greedy Result : " + arr[arr.length - 1] + " " + halfPrices.get(month));
	}

	public static void main(String[] args) throws FileNotFoundException {
		int x, p, d,B,c,t;
		x = 5;p = 6;d = 6;t=2;c=6;B=100;
		
		DP_Part1(x, d, p);
		DP_Part2(x, investment(c, x), sequencielPrice(B, x), t);
		Greedy_Part1(x, d, p);
		Greedy_Part2(x,investment(c, x),sequencielPrice(100, x),t);
		
	}
}
