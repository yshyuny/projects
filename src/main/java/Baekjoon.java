import java.io.*;
import java.util.*;

public class Baekjoon {
    public static int getMinCost(int startrow, int startcol, String[] chessboard) {
        String[] board = {"WBWBWBWB", "BWBWBWBW"}; // 화이트 버전, 블랙버전

        int BlackVerCount = 0; // 화이트를 기준으로 최소 비용을 고를 예정

        for(int i = 0; i < 8; i++){ // 세로 8
            int row = startrow + i; // 매개변수로 받은 chessboard의 값의 인덱스는 8X8이 아닌 전체 범위이기때문
            for(int j = 0; j < 8; j++){ // 가로 8
                int col = startcol + j;

                if(chessboard[row].charAt(col) != board[row%2].charAt(j)){
                    BlackVerCount++;
                }
            }
        }
        // whiteVerCount는 하얀버전으로 체스판을 자를때의 최소비용이고 64 - whiteVerCount하면 블랙의 최소비용이다.
        // 왜냐면, 체스판의 최대 크기가 8x8이기 때문
        return Math.min(BlackVerCount, 64-BlackVerCount);

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); //세로줄 크기
        int M = Integer.parseInt(st.nextToken()); // 가로줄 크기

        String[] chessboard = new String[N];

        for(int i = 0; i < N; i++){
            chessboard[i] = br.readLine(); //한줄씩 입력받는 String형 배열
        }

        // 1.체스판 자르기
        int count = 9999; //가장 큰값으로 지정해두고(초기값)
        for(int i = 0; i <= N-8; i++){
            for(int j = 0; j <= M-8; j++){
                // 현 체스판의 최소 비용 구하기
                int resultCount = getMinCost(i, j, chessboard);
                //전체 최적의 값과 비교하여 갱신하기

                if(count > resultCount){
                    count = resultCount;
                }
            }
        }

        System.out.println(count);

    }
}