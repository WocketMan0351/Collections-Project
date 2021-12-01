import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class ScrabbleScores {

	public static void main(String[] args) {
		ArrayList<Integer> letterScores = loadLetterScores();
		Queue<String> words = readWords();
		Stack<String> best = new Stack<String>();

		System.out.println("Scrabble Scores - Cody Worthen");

		Scanner in = new Scanner(System.in);
		System.out.print("How many rotations? ");
		int numRotate = in.nextInt();
		in.close();

		rotate(words, numRotate);

		// Go through all of the words in the Queue and score it.
		// Each time a new high score is found, push it onto the stack.
		int previousBest = 0;

		for (String s : words) {
			// Your code here
			int wordScore = score(s, letterScores);

			if (wordScore > previousBest) {
				best.push(s);
				previousBest = wordScore;
			}

		}

		// Go through the stack from the top to the bottom displaying the scores from
		// highest to lowest
		while (!best.isEmpty()) {
			// Your code here
			System.out.println(score(best.peek(), letterScores) + " - " + best.pop());
		}
	}

	// Calculate the Scrabble score for the given word by adding score for each
	// letter.
	// Note: if you subtract 'a' from a character, you will get its position in the
	// ArrayList.
	// For example, 'c' - 'a' is 2 (a=0, b=1, c=2, ...)
	private static int score(String s, ArrayList<Integer> l) {
		int total = 0;

		Scanner wordScanner = new Scanner(s);
		wordScanner.useDelimiter("");

		for (int i = 0; i < s.length(); i++) {
			char ch = wordScanner.next().charAt(0);
			int index = ch - 'a';
			total += l.get(index);
		}

		return total;
	}

	// Rotate the Queue n times.
	// Rotating involves removing an item from the front
	// of the Queue and adding it to the rear of the Queue.
	private static void rotate(Queue<String> q, int n) {
		// normally takes the item in the rear and move it to the front so we pass -n
		Collections.rotate((List<?>) q, -n);
	}

	// The score for an 'A' will be at position 0, 'B' will be at position 1 ... 'Z'
	// will be at position 25
	private static ArrayList<Integer> loadLetterScores() {
		ArrayList<Integer> l = new ArrayList<>();

		int[] letterScores = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

		for (int score : letterScores) {
			l.add(score);
		}

		return l;
	}

	private static Queue<String> readWords() {
		Queue<String> words = new LinkedList<String>();

		try {
			File inputFile = new File("words.txt");
			Scanner in = new Scanner(inputFile);

			while (in.hasNext()) {
				words.add(in.next().toLowerCase());
			}

			in.close();
		} catch (FileNotFoundException f) {
			System.out.println("Cannot file file 'words.txt' in the project directory.");
			System.exit(1);
		}

		return words;
	}
}
