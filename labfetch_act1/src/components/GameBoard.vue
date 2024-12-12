<template>
  <div class="game-board">
    <table>
      <thead>
        <tr>
          <th v-for="category in categories" :key="category.id">{{ category.name }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="value in [100, 200, 300]" :key="value">
          <td 
            v-for="category in categories" 
            :key="category.id" 
            :class="getCellClass(category.name, value)"
            @click="selectQuestion(category.name, value)"
          >
            {{ displayCellText(category.name, value) }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    categories: Array,
    questions: Object,
    answeredQuestions: Object
  },
  methods: {
    selectQuestion(category, value) {
      this.$emit('questionSelected', { category, value });
    },
    getCellClass(category, value) {
      const difficulty = value === 100 ? 'easy' : value === 200 ? 'medium' : 'hard';
      const questionStatus = this.answeredQuestions[category]?.[difficulty];
      if (questionStatus) {
        return questionStatus.correct ? 'correct' : 'incorrect';
      }
      return '';
    },
    displayCellText(category, value) {
      const difficulty = value === 100 ? 'easy' : value === 200 ? 'medium' : 'hard';
      const questionStatus = this.answeredQuestions[category]?.[difficulty];
      if (questionStatus) {
        return `P#${questionStatus.player}`;
      }
      return `$${value}`;
    }
  }
}
</script>

<style>
.correct {
  color: green;
}
.incorrect {
  color: red;
}
table {
  width: 100%;
}
thead {
  color: white;
  border: 3px solid gold;
}
td {
float: middle;
text-align: center;
color: gold;
border: 3px solid gold;
}
</style>
