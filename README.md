# 2020NaverRecruitment_1_03

## 2020 Naver Recruitment  온라인 코딩테스트 (9/22) 03번 문제

> 문제 출처: 네이버

> 코딩테스트 플랫폼: https://programmers.co.kr/

### 1. 문제설명

input으로 int[] cook_times, int[][] order, int k가 들어온다

#### * cook_times

음식들의 조리시간을 담고있는 배열

#### * order

음식들의 조리단계를 담고있는 배열, 각 요소는 `[선행되어야 할 cook번호, 선행된 후 가능한 cook번호]`로 구성된다.

#### * k

알고자 하는 음식 번호. k번째 음식을 하기 위해 선행되어야 할 음식의 갯수와 k번째 음식이 완료되기 위한 최소 시간을 return하는 문제.

### 2. 풀이

두가지 방법으로 문제를 풀었어서 그에 대한 항목을 소개한다.

#### 1) 준비

input으로 들어오는 각 음식의 조리시간과 선행관계들은 한번 읽어와 변수에 저장한다. 이를 위한 Cook 클래스를 두어 각 음식에 대한 정보를 이용할 수 있게 하였다.

```java
class Cook {
  int id;
  int cookTime;
  HashSet<Integer> subCookIds;
  int minTime = 0;
}
```

`id` 음식의 번호
`cookTime` 음식의 조리시간
`subCookIds` 선행되어야 할 음식의 번호
`minTime` 음식을 만들기 위해 필요한 최소시간

#### 2) 재귀적 풀이와 문제점

return 해야 하는 두 요소에 대한 재귀함수를 구성하였었다.

첫째, 음식에 대해 선행음식을 재귀적으로 찾아 모든 선행음식번호를 얻어내는 함수와

```java
private void searchStep(HashMap<Integer, Cook> cooks, HashSet<Integer> step, int k) {
  Cook cook = cooks.get(k);
  step.addAll(cook.getSubCookIds());
  for (Iterator<Integer> it = cook.getSubCookIds().iterator(); it.hasNext(); ) {
    int subCookId = it.next();
    searchStep(cooks, step, subCookId);
  }
}
```

둘째, 음식조리에 걸리는 최소 시간을 재귀적으로 계산하는 함수이다.

```java
private int findMinTime(HashMap<Integer, Cook> cooks, int k) {
  Cook cook = cooks.get(k);
  int cookTime = cook.getCookTime();

  int maxSubTime = 0;
  if (cook.getSubCookIds() != null) {
    for (Iterator<Integer> it = cook.getSubCookIds().iterator(); it.hasNext(); ) {
      int subCookId = it.next();
      int subCookTime = findMinTime(cooks, subCookId);
      maxSubTime = maxSubTime > subCookTime ? maxSubTime : subCookTime;
    }
  }

  return cookTime + maxSubTime;
}
```

두 함수를 통해 얻은 값들을 return하여 문제를 해결하였다.
```java
HashSet<Integer> step = new HashSet<>();
searchStep(cooks, step, k);
answer[0] = step.size();

int minTime = findMinTime(cooks, k);
answer[1] = minTime;
```

하지만, 왠걸... **StackOverflow** 메시지와 함께 실패하였다. 재귀적인 함수의 호출로 메모리사용이 기준량보다 큰 탓인것 같다. 계획을 바꿔 제한된 저장공간과 하위계산한 값들을 이용하여 최적해를 찾아내는 DP풀이법을 적용하기로 하였다.

#### 3) 동적계획법(DP) 풀이

order가 선행순서를 어기지 않는 정렬된 상태로 들어온다는점을 알고(위상정렬된 상태) `cooks[0]`부터 `cooks[n-1]`까지 반복하며 선행되는 음식이 있다면 선행음식중 가장 오래걸리는 선행음식의 시간과, 그 음식의 선행음식을 가져와 현재cook의 선행 음식`subCookIds`과 `minTime`을 갱신해주었다. 이후 k번째 음식을 배열에서 가져와 선행음식집합의 크기와 `minTime`을 가져와 return하여 해결하였다.

```java

Cook[] cooks = new Cook[cook_times.length+1];

/* cook_times and order를 이용해 cooks을 다 채운 후 */

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

```

테스트 케이스가 아닌 경우도 order가 위상정렬된 값인지는 모르겠다. 위상정렬을 해주는 과정을 넣었다면 더 안정적인 코드가 됫을 것인데... 위상정렬이란것을 나중에 알게되었다. 더 공부하자...
