package Base;

public class Word {
        private String word_target;
        private String word_explain;

        public Word(String word_target, String word_explain) {
            this.word_target = word_target;
            this.word_explain = word_explain;
        }

        public String getWordTarget() {
            return this.word_target;
        }

        public void setWordTarget(String word_target) {
            this.word_target = word_target;
        }

        public String getWordExplain() {
            return this.word_explain;
        }

        public void setWordExplain(String word_explain) {
            this.word_explain = word_explain;
        }

        public void printWord() {System.out.printf("%-20s%-20s\n", getWordTarget(), getWordExplain());}

}