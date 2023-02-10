<template>
  <nav-bar v-if="renderComponent" :user="this.user"/>
  <link href="/app.css" rel="stylesheet">
  <div class="sm:w-2/3 mt-24 w-11/12 mx-auto">
    <router-view/>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import {RouterView} from "vue-router";

export default {
  watch: {},
  name: 'App',
  components: {
    NavBar,
    RouterView
  },
  data() {
    return {
      renderComponent: true,
    }
  },
  methods: {},
}
</script>