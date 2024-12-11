<template>
  <div class="question-display">
    <center><h3>Player {{ currentPlayer }}, here’s your question:</h3>
    <p>{{ currentQuestion.question }}</p>
    <div>
      <button @click="answer(true)">True</button>
      <button @click="answer(false)">False</button>
    </div></center>
  </div>
</template>

<script>
export default {
  props: {
    currentQuestion: Object,
    currentPlayer: Number
  },
  methods: {
    answer(isTrue) {
      const isCorrect = (isTrue && this.currentQuestion.correct_answer === 'True') ||
                        (!isTrue && this.currentQuestion.correct_answer === 'False');
      this.$emit('answered', isCorrect);
    }
  }
}
</script>

<style>
.question-display {
  border: 3px solid gold;
}
p {
  width: 50%;
  font-size: larger;
  color: white;
}
h3 {
  color: white;
}
button {
  font-size: larger;
  margin: 2%;
}
</style>
