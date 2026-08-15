package dp;

public class DigitDp {
	/**
	 * N未満(Nを含まない)で条件を満たす値の個数を探索する桁DP
	 * 
	 * dp : N未満が確定している状態ごとの個数
	 * state0 : その桁までNと同じ数値の状態
	 * 
	 * @param N 数値を表す文字列
	 */
	public void dp(String N) {
		final int STATE_SIZE = 10;
		long[] dp = new long[STATE_SIZE];
		int state0 = 0;
		for (int i = 0; i < N.length(); i++) {
			int a = N.charAt(i) - '0';
			long[] ndp = new long[STATE_SIZE];
			// N未満 → N未満
			for (int s = 0; s < STATE_SIZE; s++) {
				if (dp[s] == 0) continue;
				for (int nd = 0; nd <= 9; nd++) {
					int ns = 0; // 次のstateの値を計算する
					ndp[ns] += dp[s];
				}
			}
			// この桁から開始
			int dmax = i == 0 ? a - 1 : 9;
			for (int nd = 1; nd <= dmax; nd++) {
				int ns = 0; // 次のstateの値を計算する
				ndp[ns] += 1;
			}
			// Nちょうどから未満になるパターン
			if (i > 0 && state0 >= 0) {
				for (int nd = 0; nd < a; nd++) {
					int ns = 0; // 次のstateの値をstate0を使用して計算する
					ndp[ns] += 1;
				}
			}
			// Nちょうどの状態遷移
			if (state0 >= 0) {
				state0 = 0; // aの値を使って状態遷移
			}
			dp = ndp;
		}
	}
}
