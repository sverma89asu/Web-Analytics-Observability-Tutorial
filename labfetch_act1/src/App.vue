<template>
  <div class="app">
    <h1>LET'S PLAY JEOPARDY!!</h1><br>
    <PlayerInfo :players="players" /><br>
    <GameBoard 
      :categories="categories" 
      :questions="questions" 
      :answeredQuestions="answeredQuestions" 
      @questionSelected="displayQuestion" 
    />
    <QuestionDisplay 
      v-if="currentQuestion" 
      :currentQuestion="currentQuestion" 
      :currentPlayer="currentPlayerIndex + 1" 
      @answered="handleAnswer" 
    />
    <div v-if="loading" class="loading">Loading game data, please wait...</div>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    <div v-if="winner" class="winner-message">{{ winner }}</div>
  </div>
</template>

<script>
import PlayerInfo from './components/PlayerInfo.vue';
import GameBoard from './components/GameBoard.vue';
import QuestionDisplay from './components/QuestionDisplay.vue';

export default {
  components: {
    PlayerInfo,
    GameBoard,
    QuestionDisplay
  },
  data() {
    return {
      players: [
        { name: "Player 1", score: 0 },
        { name: "Player 2", score: 0 },
        { name: "Player 3", score: 0 }
      ],
      categories: [],
      sessionToken: '',
      questions: {},
      answeredQuestions: {}, // to track which questions have been answered
      currentQuestion: null,
      currentPlayerIndex: 0,
      loading: true,
      errorMessage: '',
      winner: '' // stores the winner message when game ends
    }
  },
  methods: {
    async initialiseGame() {
      try {
        this.sessionToken = await this.getSessionToken();
        this.categories = await this.getCategories();
        this.questions = this.initializeEmptyQuestions(this.categories);
        this.answeredQuestions = this.initializeEmptyQuestions(this.categories);
      } catch (error) {
        this.errorMessage = 'Failed to initialise game.';
      } finally {
        this.loading = false;
      }
    },

    async getSessionToken() {
      try {
        const response = await fetch('https://opentdb.com/api_token.php?command=request');
        const data = await response.json();
        return data.token;
      } catch (error) {
        this.errorMessage = 'Failed to fetch session token.';
      }
    },

    async getCategories() {
      const response = await fetch('https://opentdb.com/api_category.php');
      const data = await response.json();
      const validCategories = data.trivia_categories.filter(category => ![13, 21, 27, 30, 32].includes(category.id));
      return this.selectRandomCategories(validCategories, 4);
    },

    selectRandomCategories(categories, n) {
      return categories.sort(() => 0.5 - Math.random()).slice(0, n);
    },

    initializeEmptyQuestions(categories) {
      return categories.reduce((acc, category) => {
        acc[category.name] = { easy: null, medium: null, hard: null };
        return acc;
      }, {});
    },

    async displayQuestion({ category, value }) {
      const difficulty = value === 100 ? 'easy' : value === 200 ? 'medium' : 'hard';
      
      if (!this.questions[category][difficulty]?.length) {
        try {
          const categoryId = this.categories.find(cat => cat.name === category).id;
          this.questions[category][difficulty] = await this.fetchWithRetry(categoryId, difficulty, this.sessionToken);
          this.errorMessage = ''; // Clear any previous error message
        } catch (error) {
          this.errorMessage = "OOPS! Please try again in 5 seconds.";
          return;
        }
      }
      
      this.currentQuestion = {
        ...this.questions[category][difficulty].shift(),
        category,
        value,
        difficulty
      };
    },

    async fetchWithRetry(categoryId, difficulty, token) {
      let delay = 5000; // Start with a 5-second delay
      const maxAttempts = 5;
      for (let attempt = 0; attempt < maxAttempts; attempt++) {
        const questions = await this.fetchQuestions(categoryId, difficulty, token);
        if (questions.length) return questions;
        this.errorMessage = "OOPS! Please try again in 5 seconds.";
        await this.delay(delay); // Wait before retrying
      }
      throw new Error(`Failed to fetch ${difficulty} questions for category ${categoryId}`);
    },

    async fetchQuestions(categoryId, difficulty, token) {
      const url = `https://opentdb.com/api.php?amount=1&category=${categoryId}&difficulty=${difficulty}&type=boolean&token=${token}`;
      const response = await fetch(url);
      const data = await response.json();
      return data.results;
    },

    delay(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },

    handleAnswer(isCorrect) {
      const player = this.players[this.currentPlayerIndex];
      const difficulty = this.currentQuestion.difficulty;
      const amount = difficulty === 'easy' ? 100 : difficulty === 'medium' ? 200 : 300;
      
      if (isCorrect) {
        player.score += amount;
        this.answeredQuestions[this.currentQuestion.category][difficulty] = {
          player: this.currentPlayerIndex + 1,
          correct: true
        };
        this.errorMessage = "Correct!";
      } else {
        player.score -= amount;
        this.answeredQuestions[this.currentQuestion.category][difficulty] = {
          player: this.currentPlayerIndex + 1,
          correct: false
        };
        this.errorMessage = "Incorrect!";
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;
      }

      this.currentQuestion = null;
      this.checkForWinner();
    },

    checkForWinner() {
      const allQuestionsAnswered = Object.values(this.answeredQuestions).every(category => 
        Object.values(category).every(q => q !== null)
      );
      
      if (allQuestionsAnswered) {
        const winner = this.players.reduce((prev, current) => (prev.score > current.score) ? prev : current);
        this.winner = `${winner.name} has won the game!`;
      }
    }
  },
  mounted() {
    this.initialiseGame();
  }
}
</script>

<style>
body {
  background-color: #0200B6;
}
h1 {
  text-align: center;
  color: gold;
  text-decoration: underline;
}
.loading {
  color: gold;
  font-weight: bold;
}
.error {
  color: red;
  font-weight: bold;
  text-align: center;
}
.winner-message {
  color: green;
  font-weight: bold;
  text-align: center;
}
</style>
