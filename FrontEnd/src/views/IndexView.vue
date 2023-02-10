<template>
  <link href="/app.css" rel="stylesheet">
  <div class="">
    <div>
      <HelloWorld msg="Welcome to Your Vue.js App"/>
    </div>
  </div>
</template>

<script>
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "IndexView",
  components: {HelloWorld}
}
</script>

<style scoped>

</style>