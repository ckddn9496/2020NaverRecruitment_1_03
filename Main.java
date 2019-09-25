import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* 2020 Naver 개발자 채용 코딩테스트 3번
 * 추석을 맞아 로이네 가족이 모여 명절 음식을 함께 만들기로 했습니다. 로이네 가족은 효율적으로 음식을 준비하기 위해 음식 만드는 과정을 몇 가지 단계로 나누고, 각자 한 단계씩 맡아서 준비하려 합니다. 예를 들어 송편을 만드는 과정은 다음과 같이 7단계로 나눌 수 있습니다.

단계	할 일	소요 시간	먼저 해야 되는 바로 전 단계
1	솔잎을 세척합니다.	5분	없음
2	반죽을 준비합니다.	30분	없음
3	콩과 깨를 준비합니다.	15분	없음
4	반죽에 콩을 채웁니다.	30분	2, 3
5	반죽에 깨를 채웁니다.	35분	2, 3
6	솥에 솔잎을 깔고 송편을 찝니다.	20분	1, 4, 5
7	송편을 그릇에 담습니다.	4분	6
이때, 한 번에 한 단계씩 순서대로 송편을 만들어 간다면 139분이 걸립니다. 그러나, 반드시 먼저 해야 하는 일과 동시에 진행해도 괜찮은 일들을 구분한 다음 음식을 준비한다면 시간을 단축시킬 수 있습니다. 각 단계들을 잘 살펴보면 1,2,3 단계는 동시에 진행할 수 있습니다. 또, 2, 3단계를 수행한 후에는 4, 5 단계도 동시에 진행할 수 있습니다. 아래 그림에서 괄호 안의 숫자는 각 단계의 소요 시간을 나타냅니다.

food_stage_1.png

예를 들어 2단계, 3단계를 동시에 진행하면, 2단계가 더 오래 걸리기 때문에 4단계, 5단계를 수행하기 위해서는 적어도 30분이 지나야 합니다. 따라서, 4단계를 완료하는 데는 60분, 5단계를 완료하는 데는 65분이 걸리고, 6단계를 수행하려면 적어도 65분이 지나야 합니다. 6단계를 완료하는 데는 85분이 걸리고, 7단계까지 완료해서 송편을 완성하는 데는 최소 89분이 걸립니다.

또, 각 단계별로 살펴보면 1, 2, 3단계는 반드시 먼저 해야 되는 전 단계들이 하나도 없지만, 4단계, 5단계의 경우 반드시 2단계, 3단계를 먼저 해야 합니다. 6단계의 경우 1단계, 4단계, 5단계를 반드시 먼저 해야 되는데, 4단계, 5단계를 완료하기 위해서는 2단계, 3단계 또한 완료해야 하기 때문에, 이 경우 6단계를 완료하기 위해 반드시 먼저 해야 하는 단계들은 (1,2,3,4,5) 단계로 총 5개입니다. 마찬가지로 7단계를 수행하기 위해 반드시 먼저 해야 하는 단계들은 (1,2,3,4,5,6) 단계로 총 6개입니다.

다양한 음식들을 만들기 위해 거쳐야 하는 단계가 주어졌을 때, k 단계를 수행하기 전에 반드시 먼저 완료해야 하는 단계 개수와 k 단계를 완료하는데 최소 몇 분이 걸리는지 구하려 합니다. 단, 로이네 가족은 모든 단계를 각자 하나씩 맡아서 준비할 수 있을 정도로 충분히 많다고 가정합니다.

각 단계별 소요 시간이 순서대로 들은 배열 cook_times, 먼저 해야 되는 단계 관계를 담은 2차원 배열 order, 로이가 구하려고 하는 단계의 번호 k가 매개변수로 주어집니다. k 단계를 수행하기 전에 반드시 먼저 완료해야 하는 단계 개수와 k 단계를 완료하는데 최소 몇 분이 걸리는지 배열에 순서대로 담아 return 하도록 solution 함수를 완성해주세요.

제한사항
cook_times의 길이는 1 이상 1,000 이하인 자연수입니다.
cook_times의 첫 번째 원소는 1 단계에 해당하는 소요 시간이며, 이후 마지막 단계까지 각 단계의 소요 시간을 번호 순서대로 담고 있습니다.
각 단계의 소요 시간은 1 이상 1,000 이하인 자연수이며 분 단위입니다.
order는 가로(열) 길이가 2, 세로(행) 길이가 1 이상 200,000 이하인 2차원 배열입니다.
order의 각 행은 [a, b] 형태입니다.
이는 b 단계를 수행하기 위해 해야 하는 바로 전 단계가 a 단계라는 의미입니다.
a, b는 1 이상 cook_times 배열의 길이 이하인 자연수이며, a와 b가 같은 경우는 없습니다.
항상 모든 음식을 만들 수 있는 관계로만 입력이 주어집니다.
k는 1 이상 cook_times 배열의 길이 이하인 자연수입니다.
return 하는 배열의 첫 번째 원소는 k 단계를 수행하기 전에 반드시 먼저 해야 하는 단계 개수, 두 번째 원소는 k 단계를 완료하는데 걸리는 최소 시간을 담아주세요.
입출력 예
cook_times	order	k	result
[5, 30, 15, 30, 35, 20, 4]	[[2,4],[2,5],[3,4],[3,5],[1,6],[4,6],[5,6],[6,7]]	6	[5, 85]
[5, 30, 15, 30, 35, 20, 4, 50, 40]	[[2,4],[2,5],[3,4],[3,5],[1,6],[4,6],[5,6],[6,7],[8,9]]	9	[1, 90]
[5, 3, 2]	[[1, 2], [2, 3], [1, 3]]	3	[2,10]
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.

입출력 예 #2

1~7단계는 문제 예시와 같습니다. 8 단계를 먼저 수행해야 9 단계를 할 수 있습니다. 9 단계를 완료하는데 최소 90분이 걸립니다.

입출력 예 #3

1 단계와 2 단계를 모두 수행해야 3 단계를 할 수 있습니다. 3 단계를 완료하는데 최소 10분이 걸립니다.
 * */
public class Main {

	public static void main(String[] args) {
		
//		int[] cook_times = {5, 30, 15, 30, 35, 20, 4};
//		int[][] order = {{2,4},{2,5},{3,4},{3,5},{1,6},{4,6},{5,6},{6,7}};
//		int k = 6; // return [5, 85]
		
//		int[] cook_times = {5, 30, 15, 30, 35, 20, 4, 50, 40};
//		int[][] order = {{2,4},{2,5},{3,4},{3,5},{1,6},{4,6},{5,6},{6,7},{8,9}};
//		int k = 9; // return [1, 90]
		
		int[] cook_times = {5, 3, 2};
		int[][] order = {{1,2},{2,3},{1,3}};
		int k = 3; // return [2, 10]
		
		
		System.out.println(Arrays.toString(new Solution().solution(cook_times, order, k)));
	}

}
class Solution {
//	class Cook {
//		int id;
//		int cookTime;
//		List<Integer> subCookIds;
//	
//		public Cook(int id, int cookTime) {
//			this.id = id;
//			this.cookTime = cookTime;
//			subCookIds = new ArrayList<>();
//		}
//
//		public int getCookTime() {
//			return cookTime;
//		}
//
//		public List<Integer> getSubCookIds() {
//			return subCookIds;
//		}
//		
//		public void addSubCookId(int id) {
//			this.subCookIds.add(id);
//		}
//		
//	}
	
	class Cook {
		int id;
		int cookTime;
		HashSet<Integer> subCookIds;
		int minTime = 0;
		
		public Cook(int id, int cookTime) {
			this.id = id;
			this.cookTime = cookTime;
			subCookIds = new HashSet<>();
		}

		public int getMinTime() {
			return minTime;
		}

		public void setMinTime(int minTime) {
			this.minTime = minTime;
		}

		public int getCookTime() {
			return cookTime;
		}

		public HashSet<Integer> getSubCookIds() {
			return subCookIds;
		}
		
		public void addSubCookId(int id) {
			this.subCookIds.add(id);
		}
		
		public void addSubCookIds(HashSet<Integer> ids) {
			this.subCookIds.addAll(ids);
		}
		
		@Override
		public String toString() {
			return id + " "+cookTime + "\n" + subCookIds + "\n";
		}
	}

	public int[] solution(int[] cook_times, int[][] order, int k) {
		Cook[] cooks = new Cook[cook_times.length+1];
		for (int i = 1; i <= cook_times.length; i++) {
			cooks[i] = new Cook(i, cook_times[i-1]);
		}
		
//		System.out.println(cooks);
		
		for (int[] o : order) {
			int child = o[0];
			int parent = o[1];
			cooks[parent].addSubCookId(child);
		}
//		System.out.println(Arrays.toString(cooks));
		
		for (int i = 1; i <= k; i++) {
			Cook cook = cooks[i];
			// set cook minTime
			
			int maxTime = 0;
			if (cook.getSubCookIds() != null) {
				HashSet<Integer> set = new HashSet<>();
				for (Iterator<Integer> it = cook.getSubCookIds().iterator(); it.hasNext(); ) {
					int subId = it.next();
					maxTime = maxTime > cooks[subId].getMinTime() ? maxTime : cooks[subId].getMinTime();
					set.addAll(cooks[subId].getSubCookIds());
				}
				cook.addSubCookIds(set);
			}
			
			cooks[i].setMinTime(cooks[i].getCookTime() + maxTime);
		}
		
		int[] answer = new int[2];
		// k에 대하여 역으로 탐색
		answer[0] = cooks[k].getSubCookIds().size();
		answer[1] = cooks[k].getMinTime();
//		HashSet<Integer> step = new HashSet<>();
//		searchStep(cooks, step, k);
//		answer[0] = step.size();
//		
//		int minTime = findMinTime(cooks, k);
//		answer[1] = minTime;
		
		return answer;
		
	}

//	private void searchStep(HashMap<Integer, Cook> cooks, HashSet<Integer> step, int k) {
//		Cook cook = cooks.get(k);
//		step.addAll(cook.getSubCookIds());
//		for (Iterator<Integer> it = cook.getSubCookIds().iterator(); it.hasNext(); ) {
//			int subCookId = it.next();
//			searchStep(cooks, step, subCookId);
//		}
//	}
//
//	private int findMinTime(HashMap<Integer, Cook> cooks, int k) {
//		Cook cook = cooks.get(k);
//		int cookTime = cook.getCookTime();
//		
//		int maxSubTime = 0;
//		if (cook.getSubCookIds() != null) {
//			for (Iterator<Integer> it = cook.getSubCookIds().iterator(); it.hasNext(); ) {
//				int subCookId = it.next();
//				int subCookTime = findMinTime(cooks, subCookId);
//				maxSubTime = maxSubTime > subCookTime ? maxSubTime : subCookTime;
//			}
//		}
//		
//		return cookTime + maxSubTime;
//	}
}