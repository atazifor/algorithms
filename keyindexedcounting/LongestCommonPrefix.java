import java.util.Arrays;
import java.util.Collections;

public class LongestCommonPrefix {
    
    public static String lcp(String s) {
        String[] suffixes = suffixes(s);
        Arrays.sort(suffixes);
        String maxPrefix = "";
        int maxLength = 0;
        for(int i = 0;  i < suffixes.length-1; i++) {
            int len = lcp(suffixes[i], suffixes[i+1]);
            if(len > maxLength)  {
                maxLength  = len;
                maxPrefix = suffixes[i].substring(0, len);
            }
        }
        return maxPrefix;
    }
    
    public static String[] suffixes(String s) {
        int N  = s.length();
        String[] suffixes = new String[N];
        for(int i = 0; i < N; i++) {
          suffixes[i] = s.substring(i, N);   
        }
        return suffixes;
    }
    
    public static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for(int i = 0; i < N; i++) {
            if(s.charAt(i) != t.charAt(i))
                return i;
        }
        return N;
    }
    
    public static void main(String[] args) {
        //String text = "a a c a a g t t t a c a a g c a t g a t g ctgtacta g g a g a g t t a t a c t g g t c g t c a aacctgaa c c t a a t c c t t g t g t g t a c a c a cactacta c t g t c g t c g t c a t a t a t c g a g atcatcga a c c g g a a g g c c g g a c a a g g c g gggggtat a g a t a g a t a g a c c c c t a g a t a cacataca t a g a t c t a g c t a g c t a g c t c a tcgataca c a c t c t c a c a c t c a a g a g t t a tactggtc a a c a c a c t a c t a c g a c a g a c g accaacca g a c a g a a a a a a a a c t c t a t a t ctataaaa";
        String text = "In the bustling city of Metropolis, the skyline was a symphony of steel and glass, with towering skyscrapers that reached for the heavens. Amidst the cacophony of honking cars and chattering crowds, there existed a small, quaint bookstore nestled between two modern buildings. This bookstore, known as \"Eternal Pages,\" was a sanctuary for the city's bibliophiles. "
                + "\"Eternal Pages\" was not just any bookstore; it was a place where stories came alive. The moment you stepped through its wooden doors, the scent of aged paper and ink enveloped you, and the whispers of countless tales filled the air. The store was dimly lit by chandeliers that hung from the ceiling, casting a warm, golden glow over the rows of bookshelves that stretched as far as the eye could see. "
                + "Each bookshelf was a portal to a different world. There were sections dedicated to epic fantasies, where dragons soared through the skies and heroes embarked on perilous quests. Another section housed gripping mysteries, where detectives unraveled intricate puzzles and unearthed dark secrets. Romance novels lined another wall, telling tales of love and heartbreak, while historical fiction transported readers to bygone eras. "
                + "At the heart of \"Eternal Pages\" was a cozy reading nook, complete with plush armchairs and soft, woolen blankets. Here, patrons could lose themselves in the pages of a book, accompanied by the soothing melody of classical music that played softly in the background. The owner of the bookstore, a kindly old man named Mr. Whitaker, was always on hand to recommend a good read or to share anecdotes from his vast knowledge of literature. "
                + "One rainy afternoon, a young woman named Clara entered the bookstore. She was new to Metropolis and had heard of the bookstore's enchanting reputation. Seeking refuge from the downpour, she stepped inside, shaking the raindrops from her umbrella. As she wandered through the aisles, her fingers brushed against the spines of the books, and she marveled at the sheer variety of titles. "
                + "In the far corner of the store, Clara discovered a section she hadn't noticed before. It was marked \"Forgotten Legends.\" Intrigued, she pulled a dusty, leather-bound book from the shelf. The cover was embossed with intricate designs, and the title read, \"The Chronicles of Eldoria.\" Clara settled into an armchair and began to read. "
                + "As the words leaped off the page, Clara found herself transported to the mystical land of Eldoria, where magic and adventure awaited. She followed the journey of a young sorcerer named Aric, who was on a quest to save his kingdom from an ancient evil. With each chapter, Clara became more engrossed in the story, losing track of time and the world around her. "
                + "Hours passed, and the rain outside intensified, drumming against the windows of the bookstore. Clara was so absorbed in the tale that she didn't notice the store emptying and the evening drawing to a close. It was only when Mr. Whitaker gently tapped her on the shoulder that she realized how late it had become. "
                + "\"I'm afraid we're closing for the night, dear,\" he said with a kind smile. \"But you can take the book with you. Consider it a gift for a fellow lover of stories.\" "
                + "Clara thanked him profusely and promised to return the next day. She left the bookstore with \"The Chronicles of Eldoria\" clutched tightly in her hands, her heart full of excitement for the adventures that awaited her within its pages. "
                + "As Clara walked back to her apartment, the city lights reflecting off the rain-soaked streets, she couldn't help but feel a sense of wonder. In a world that often felt rushed and chaotic, \"Eternal Pages\" had given her a moment of peace and a reminder of the magic that existed within the pages of a good book. "
                + "And so, every evening after work, Clara returned to \"Eternal Pages.\" She explored new genres, discovered hidden gems, and made friends with other book enthusiasts. The bookstore became her sanctuary, a place where she could escape the stresses of everyday life and immerse herself in the endless possibilities of imagination. "
                + "The story of \"Eternal Pages\" and its patrons is a testament to the enduring power of literature. In a city that never slept, it was a reminder that sometimes, all you needed was a quiet corner, a comfortable chair, and a good book to find a little bit of magic in the world.";

        System.out.println("lcp(text) = " + lcp(text));
    }
}
