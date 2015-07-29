import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*
1
4 20 6 34
4 40
18 15
10 7
20 12
*/
public class RoadTrip {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		int m = Integer.parseInt(scn.nextLine());

		String s = scn.nextLine();

		String sArr[] = s.split(" ");
		int arr[] = strToIntArr(sArr);

		int n = arr[0];
		int f = arr[1];
		int t = arr[2];
		int l = arr[3];

		Station st[] = new Station[n];

		for (int i = 0; i < n; i++) {
			String ss = scn.nextLine();
			String ssArr[] = ss.split(" ");
			int arr2[] = strToIntArr(ssArr);
			int distance = arr2[0];
			int cost = arr2[1];
			st[i] = new Station(i + 1, cost, distance);
		}

		int cl = 0;
		int sum = 0;

		if (t >= l) {
			System.out.println(sum);
		} else {
			ArrayList<Station> sInit = new ArrayList<Station>();
			int i = 0;
			while (i < n && st[i].getDistance() <= t) {
				sInit.add(st[i]);
				i++;
			}

			sInit.sort(new Comparator<Station>() {
				@Override
				public int compare(Station o1, Station o2) {

					return o1.getCost() - o2.getCost();
				}
			});

			if (!sInit.isEmpty()) {
				Station currentSt = (Station) sInit.get(0);

				//System.out.println(currentSt);

				cl = currentSt.getDistance();
				t -= currentSt.getDistance();
				while (cl < l) {
					System.out.println(currentSt);
					
					int j = currentSt.getIndex();
					ArrayList<Station> sts = new ArrayList<Station>();
					while (j < n && st[j].getDistance() - cl <= f) {
						sts.add(st[j]);
						j++;
					}

					sts.sort(new Comparator<Station>() {
						@Override
						public int compare(Station o1, Station o2) {

							return o1.getCost() - o2.getCost();
						}
					});

					if (isEnd(cl, l, f)) {
						if (isCurrentStCheapest(sts, currentSt)) {
							sum += (l - currentSt.getDistance() - t) * currentSt.getCost();
						} else {
							if (!sts.isEmpty()) {
								int dis = sts.get(0).getDistance() - currentSt.getDistance();
								sum = sum + (dis - t) * currentSt.getCost();
							} else {
								sum += (l - currentSt.getDistance() - t) * currentSt.getCost();
							}
						}
					} else {
						if (isCurrentStCheapest(sts, currentSt)) {
							sum = sum + ((f - t) * currentSt.getCost());
							t = f;
						} else {
							int dis = sts.get(0).getDistance() - currentSt.getDistance();
							sum = sum + (dis - t) * currentSt.getCost();
							t = 0;
						}
					}

					if (!sts.isEmpty()) {
						if (t > 0) {
							t = sts.get(0).getDistance() - currentSt.getDistance();
						}
						currentSt = sts.get(0);
						cl = currentSt.getDistance();

					} else {
						cl = l;
					}

				}
			}

			System.out.println("Final Sum: " + sum);

		}
	}

	public static boolean isCurrentStCheapest(ArrayList<Station> sList, Station current) {
		int currentValue = current.getCost();

		for (Station s : sList) {
			if (s.getCost() < currentValue) {
				return false;
			}
		}
		return true;

	}

	public static boolean isEnd(int cl, int l, int f) {
		if ((cl + f) < l) {
			return false;
		}
		return true;
	}

	public static int[] strToIntArr(String[] s) {
		int out[] = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			out[i] = Integer.parseInt(s[i]);

		}
		return out;
	}

}

class Station {
	int cost = 0;
	int index = 0;
	int distance = 0;

	public Station(int index, int cost, int distance) {
		this.cost = cost;
		this.index = index;
		this.distance = distance;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String toString() {
		return "Index: " + index + ", Distance: " + distance + ", Cost: " + cost;
	}

}
