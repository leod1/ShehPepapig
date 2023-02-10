<template>
  <create-compoenent></create-compoenent>
  <div class="mt-3" v-for="(log, i) in this.logs" :value="log" :key="i">

    <LogComponent :log="log"></LogComponent>
  </div>
</template>

<script>
import LogComponent from "@/components/LogComponent.vue";
import CreateCompoenent from "@/components/CreateCompoenent.vue";

export default {
  name: "LogView",
  components: {CreateCompoenent, LogComponent},
  data() {
    return {
      logs: Object,
    };
  },
  methods: {
    getLogs: async function(){
      this.logs = [];
      await fetch("/api/logs", {
        method: "GET",
      }).then(res => res.json()).then(data => {
        this.logs = data;
      })
    }
  },
  async beforeMount() {
    await this.getLogs();
  },
}
</script>

<style scoped>

</style>