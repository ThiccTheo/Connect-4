import java.util.Scanner;

class Main {
	enum Turn {
		Player1,
		Player2,
	}

	public static void main(String[] args) {
		is_game_over = false;
		String[][] board = new String[][] {
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
				{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
		};
		current_turn = Turn.Player1;

		while (true) {
			clear();
			draw(board, "");
			logic(board);
			if (is_game_over) {
				break;
			}
			update(board);
		}

		Scanner scan = new Scanner(System.in);
		System.out.print("Play again? (y/n): ");
		String play_again = scan.nextLine();

		if (play_again.toUpperCase().equals("Y")) {
			main(args);
		}
	}

	private static void clear() {
		System.out.print("\033[H\033[2J");
	}

	private static void update(String[][] board) {
		Scanner scan = new Scanner(System.in);

		int x;
		while (true) {
			System.out.print("Enter a move. (1–7): ");
			x = scan.nextInt();
			if (x >= 1 && x <= WIDTH && board[0][x - 1] == EMPTY) {
				break;
			}
		}
		--x;

		String sprite = current_turn == Turn.Player1 ? PLAYER1_SPRITE : PLAYER2_SPRITE;

		int y = HEIGHT - 1;
		while (true) {
			if (board[y][x] != EMPTY) {
				--y;
			} else {
				break;
			}
		}

		board[y][x] = sprite;
		current_turn = current_turn == Turn.Player1 ? Turn.Player2 : Turn.Player1;
	}

	private static void draw(String[][] board, String optional) {
		System.out.println("     Connect 4");
		for (int x = 0; x < WIDTH + 2; ++x) {
			System.out.print(WALL);
		}

		if (!is_game_over) {
			System.out.println(String.format(" %s to move.", current_turn == Turn.Player1 ? PLAYER1_SPRITE : PLAYER2_SPRITE));	
		} else {
			System.out.println(optional);
		}

		for (int y = 0; y < HEIGHT; ++y) {
			System.out.print(WALL);
			for (int x = 0; x < WIDTH; ++x) {
				System.out.print(board[y][x]);
			}
			System.out.print(WALL);
			System.out.println();
		}

		for (int x = 0; x < WIDTH + 2; ++x) {
			System.out.print(WALL);
		}
		System.out.println();
		System.out.println("   1 2 3 4 5 6 7\n");
	}

	private static void logic(String[][] board) {
		is_game_over = win_check(board, PLAYER1_SPRITE);
		if (is_game_over) {
			clear();
			draw(board, String.format(" %s has won!", PLAYER1_SPRITE));
		} else if (!is_game_over) {
			is_game_over = win_check(board, PLAYER2_SPRITE);
			if (is_game_over) {
				clear();
				draw(board, String.format(" %s has won!", PLAYER2_SPRITE));
			}
		}
	}

	private static boolean win_check(String[][] board, String sprite) {
		//horiz
		for (int y = 0; y < HEIGHT; ++y) {
			for (int x = 0; x < WIDTH - 3; ++x) {
				if (board[y][x].equals(sprite)
				    && board[y][x + 1].equals(sprite)
				    && board[y][x + 2].equals(sprite)
				    && board[y][x + 3].equals(sprite)) {
					return true;					
				}
			}
		}

		//vert
		for (int y = 0; y < HEIGHT - 3; ++y) {
			for (int x = 0; x < WIDTH; ++x) {
				if (board[y][x].equals(sprite)
				    && board[y + 1][x].equals(sprite)
				    && board[y + 2][x].equals(sprite)
				    && board[y + 3][x].equals(sprite)) {
					return true;			
				}
			}
		}

		//diag down
		for (int y = 0; y < HEIGHT - 3; ++y) {
			for (int x = 0; x < WIDTH - 3; ++x) {
				if (board[y][x].equals(sprite)
				    && board[y + 1][x + 1].equals(sprite)
				    && board[y + 2][x + 2].equals(sprite)
				    && board[y + 3][x + 3].equals(sprite)) {
					return true;			
				}
			}
		}
		
		//diag up
		for (int y = HEIGHT - 1; y > 2; --y) {
			for (int x = 0; x < WIDTH - 3; ++x) {
				if (board[y][x].equals(sprite)
				    && board[y - 1][x + 1].equals(sprite)
				    && board[y - 2][x + 2].equals(sprite)
				    && board[y - 3][x + 3].equals(sprite)) {
					return true;			
				}
			}
		}
		
		return false;
	}

	private static final int WIDTH = 7;
	private static final int HEIGHT = 6;
	private static boolean is_game_over;
	private static Turn current_turn;
	private static final String EMPTY = "  ";
	private static final String WALL = "🟦";
	private static final String PLAYER1_SPRITE = "🟡";
	private static final String PLAYER2_SPRITE = "🔴";
}