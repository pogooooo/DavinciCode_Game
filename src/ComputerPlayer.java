
import java.util.*;

public class ComputerPlayer {
    private static List<Integer> computerTiles = new ArrayList<>();
    // computerTiles = 컴퓨터 플레이어가 가지고 있는 타일
    private static List<Integer> allTiles = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    // allTiles = 게임에 존재하는 모든 타일
    private static Set<Integer> incorrectGuesses = new HashSet<>();
    // incorrectGuesses = 틀린 타일을 기록하는 집합
    private static Random rand = new Random();
    // rand = 랜덤 객체

    public static void main(String[] args) {
        // 컴퓨터 플레이어가 랜덤으로 타일을 선택
        initializeComputerTiles(3);  // 3개의 타일을 랜덤으로 선택

        // 상대 플레이어가 공개한 타일 (예시)
        List<Integer> opponentRevealedTiles = Arrays.asList(3, 7);

        // 가장 큰 수 혹은 작은 수를 맞추는 기능 테스트
        int guess = guessLargestOrSmallest();
        System.out.println("컴퓨터 플레이어가 선택한 가장 큰 수 또는 작은 수: " + guess);

        // 공개된 카드를 기준으로 큰 수와 작은 수 리스트에서 경우의 수가 더 적은 리스트에서 하나를 랜덤으로 선택하고 틀린 경우 기록
        int selectedTile = selectTileBasedOnRevealed(opponentRevealedTiles);
        System.out.println("컴퓨터 플레이어가 선택한 타일 (기준 공개 타일): " + selectedTile);

        // 다빈치코드 게임에서 현재 공개된 카드와 붙어있는 카드 혹은 가장 멀리 있는 카드를 선택하고 공개된 카드가 없을 경우 랜덤으로 선택
        int strategicTile = selectTileStrategically(opponentRevealedTiles);
        System.out.println("컴퓨터 플레이어가 선택한 전략적 타일: " + strategicTile);

        // 예시로 틀린 경우 기록
        boolean isCorrect = false; // 실제 게임 로직에 따라 결정
        if (!isCorrect) {
            incorrectGuesses.add(strategicTile);
        }

        System.out.println("틀린 타일 목록: " + incorrectGuesses);
    }

    // 게임 시작 시 컴퓨터 타일 선택
    private static void initializeComputerTiles(int numberOfTiles) {
        List<Integer> availableTiles = new ArrayList<>(allTiles);
        Collections.shuffle(availableTiles);
        for (int i = 0; i < numberOfTiles; i++) {
            computerTiles.add(availableTiles.get(i));
        }
    }

    private static int guessLargestOrSmallest() {
        // 컴퓨터 플레이어가 가지고 있는 타일을 제외하고 가장 큰수 또는 작은 수를 랜덤으로 맞추는 기능
        List<Integer> remainingTiles = new ArrayList<>(allTiles);
        remainingTiles.removeAll(computerTiles);

        int smallestTile = remainingTiles.get(0);
        int largestTile = remainingTiles.get(remainingTiles.size() - 1);

        return rand.nextBoolean() ? smallestTile : largestTile;
    }

    private static int selectTileBasedOnRevealed(List<Integer> revealedTiles) {
        // 공개된 타일을 기준으로 큰수와 작은수 리스트에서 경우의 수가 더 적은 리스트에서 하나를 랜덤으로 선택하고 틀린 경우를 기록
        List<Integer> remainingTiles = new ArrayList<>(allTiles);
        remainingTiles.removeAll(computerTiles);
        remainingTiles.removeAll(incorrectGuesses);

        if (revealedTiles.isEmpty()) {
            return remainingTiles.get(rand.nextInt(remainingTiles.size()));
        }

        List<Integer> smallTiles = new ArrayList<>();
        List<Integer> largeTiles = new ArrayList<>();

        for (int tile : remainingTiles) {
            if (tile < 6) {
                smallTiles.add(tile);
            } else {
                largeTiles.add(tile);
            }
        }

        List<Integer> chosenList = (smallTiles.size() <= largeTiles.size()) ? smallTiles : largeTiles;
        return chosenList.get(rand.nextInt(chosenList.size()));
    }

    private static int selectTileStrategically(List<Integer> revealedTiles) {
        // 현재 공개된 카드와 붙어있는 카드 혹은 가장 멀리있는 카드를 선택하고 공개된 카드가 없을 경우 랜덤으로 선택
        // 선택한 타일이 틀린 타일이면 다시 랜덤 선택
        List<Integer> remainingTiles = new ArrayList<>(allTiles);
        remainingTiles.removeAll(computerTiles);
        remainingTiles.removeAll(incorrectGuesses);

        if (revealedTiles.isEmpty()) {
            return remainingTiles.get(rand.nextInt(remainingTiles.size()));
        }

        int chosenTile = chooseClosestOrFurthestTile(remainingTiles, revealedTiles);

        while (incorrectGuesses.contains(chosenTile)) {
            chosenTile = remainingTiles.get(rand.nextInt(remainingTiles.size()));
        }

        return chosenTile;
    }

    private static int chooseClosestOrFurthestTile(List<Integer> remainingTiles, List<Integer> revealedTiles) {
        // 공개된 타일의 양 끝 값을 찾고, 공개된 타일과 가장 가까운 타일 및 가장 먼 타일 선택하는 기능
        int minRevealed = Collections.min(revealedTiles);
        int maxRevealed = Collections.max(revealedTiles);

        int closestTile = -1;
        int furthestTile = -1;
        int closestDistance = Integer.MAX_VALUE;
        int furthestDistance = Integer.MIN_VALUE;

        for (int tile : remainingTiles) {
            int distanceToClosest = Math.min(Math.abs(tile - minRevealed), Math.abs(tile - maxRevealed));
            int distanceToFurthest = Math.max(Math.abs(tile - minRevealed), Math.abs(tile - maxRevealed));

            if (distanceToClosest < closestDistance) {
                closestDistance = distanceToClosest;
                closestTile = tile;
            }

            if (distanceToFurthest > furthestDistance) {
                furthestDistance = distanceToFurthest;
                furthestTile = tile;
            }
        }

        return (closestDistance <= furthestDistance) ? closestTile : furthestTile;
    }
}