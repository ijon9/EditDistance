public class EditDistance {
    
    static class Box {
        public int val;
        public char op;
        public Box(int val, char op) {
            this.val = val;
            this.op = op;
        }
    }

    public static void stringMatching(String s, String t, Box[][] dp) {
        int[] opt = new int[3];
        char[] move = new char[] {'M', 'I', 'D'};
        
        // Row init
        for(int col=0; col<dp[0].length; col++) {
            dp[0][col] = new Box(col, 'I');
        }
        // Col init
        for(int row=0; row<dp.length; row++) {
            dp[row][0] = new Box(row, 'D');
        }
        // Algorithm
        for(int row=1; row<dp.length; row++) {
            for(int col=1; col<dp[0].length; col++) {
                opt[0] = dp[row-1][col-1].val + (s.charAt(row-1) == t.charAt(col-1) ? 0 : 1);
                opt[1] = dp[row][col-1].val + 1;
                opt[2] = dp[row-1][col].val + 1;

                int cost = opt[0];
                char parent = cost > dp[row-1][col-1].val ? 'S' : 'M';
                for(int i=1; i<opt.length; i++) {
                    if(opt[i] < cost) {
                        cost = opt[i];
                        parent = move[i];
                    }
                }
                dp[row][col] = new Box(cost, parent);
            }
        }
    }

    public static String reconstructPath(Box[][] dp) {
        String retStr = "";
        int row = dp.length-1;
        int col = dp[0].length-1;
        while(row != 0 || col != 0) {
            char op = dp[row][col].op;
            retStr = op + retStr;
            if(op == 'M' || op == 'S') {
                row--;
                col--;
            }
            else if(op == 'I') {
                col--;
            }
            else row--;
        }
        return retStr;
    }

    public static void main(String[] args) {
        // Make sure to add 1 when making dp array
        String s = "leonard skiena";
        String t = "lynard skynard";
        Box[][] dp = new Box[s.length()+1][t.length()+1];
        stringMatching(s, t, dp);

        // for(int row=0; row<dp.length; row++) { 
        //     for(int col=0; col<dp[0].length; col++) {
        //         System.out.print(dp[row][col].op);
        //         System.out.print(" ");
        //     }
        //     System.out.println();
        // }


        System.out.println(dp[s.length()][t.length()].val);
        System.out.println(reconstructPath(dp));
    }
}