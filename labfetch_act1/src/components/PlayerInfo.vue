<script>
export default {
  props: ['players']
}
</script>

<template>
  <table class="playerInfo">
      <th v-for="player in players" :key="player.name">
        {{ player.name }} : ${{ player.score }}
      </th>
  </table>
</template>

<style>
.playerInfo {
  width: 100%;
  text-align: center;
  font-size: x-large;
  color: white;
}
</style>
