package Gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

    public final class MarkovChain {

        /**
         * Number of words per state.
         */
        private final int k;

        /**
         * The array of words.
         */
        private final String[] words;

        /**
         * State transition function.
         */
        private final Map<List<String>, Map<List<String>, Integer>> map =
                new HashMap<>();

        /**
         * Maps to each state the number of its following states.
         */
        private final Map<List<String>, Integer> totalCountMap = new HashMap<>();

        /**
         * The list of all states.
         */
        private final List<List<String>> vocabulary = new ArrayList<>();

        private final Random random;

        public MarkovChain(String[] words, int k, Random random) {
            this.words = Objects.requireNonNull(words, "Word array is null.");
            this.k = checkPositive(k);

            if (words.length < k) {
                throw new IllegalArgumentException("number of words < k");
            }

            this.random = Objects.requireNonNull(random, "The random is null.");
            build();
        }

        public MarkovChain(String[] words, int k) {
            this(words, k, new Random());
        }

        public String[] compose(int numberOfWords) {
            checkRequestedNumberOfWords(numberOfWords);
            List<String> startState =
                    vocabulary.get(random.nextInt(vocabulary.size()));

            String[] outputWords = new String[numberOfWords];
            numberOfWords -= k;

            for (int i = 0; i < startState.size(); ++i) {
                outputWords[i] = startState.get(i);
            }

            int index = k;

            while (numberOfWords-- > 0) {
                List<String> nextState = randomTransition(startState);
                outputWords[index++] = lastOf(nextState);
                startState = nextState;
            }

            return outputWords;
        }

        private static <T> T lastOf(List<T> list) {
            return list.get(list.size() - 1);
        }

        private List<String> randomTransition(List<String> startState) {
            Map<List<String>, Integer> localMap = map.get(startState);

            if (localMap == null) {
                return vocabulary.get(random.nextInt(vocabulary.size()));
            }

            int choices = totalCountMap.get(startState);
            int coin = random.nextInt(choices);

            for (Map.Entry<List<String>, Integer> entry : localMap.entrySet()) {
                if (coin < entry.getValue()) {
                    return entry.getKey();
                }

                coin -= entry.getValue();
            }

            throw new IllegalStateException("Should not get here");
        }

        private void build() {
            Set<List<String>> filter = new HashSet<>();
            Deque<String> wordDeque = new ArrayDeque<>();

            for (int i = 0; i < k; ++i) {
                wordDeque.addLast(words[i]);
            }

            for (int i = k; i < words.length; ++i) {
                List<String> startSentence = new ArrayList<>(wordDeque);
                filter.add(startSentence);

                wordDeque.removeFirst();
                wordDeque.addLast(words[i]);
                List<String> nextSentence = new ArrayList<>(wordDeque);

                Map<List<String>, Integer> localMap = map.get(startSentence);

                if (localMap == null) {
                    map.put(startSentence, localMap = new HashMap<>());
                }

                localMap.put(nextSentence,
                        localMap.getOrDefault(nextSentence, 0) + 1);

                totalCountMap.put(startSentence,
                        totalCountMap.getOrDefault(startSentence, 0) + 1);
            }

            vocabulary.addAll(filter);
        }

        private int checkPositive(int k) {
            if (k < 1) {
                throw new IllegalArgumentException("k < 1");
            }

            return k;
        }

        private void checkRequestedNumberOfWords(int numberOfWords) {
            if (numberOfWords < k) {
                throw new IllegalArgumentException(
                        "The minimum number of words for composition should be " +
                                k + ". Received " + numberOfWords);
            }
        }
    }

