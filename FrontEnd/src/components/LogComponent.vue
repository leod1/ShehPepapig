<template>
  <div class="w-full flex flex-row gap-3 p-3 rounded-lg shadow">
    <div class="flex items-center flex-col">
      <div><svg @click="this.vote" xmlns="http://www.w3.org/2000/svg" class="h-6 hover:scale-125 transition cursor-pointer w-6" viewBox="0 0 20 20" fill="currentColor">
        <path fill-rule="evenodd" d="M14.707 12.707a1 1 0 01-1.414 0L10 9.414l-3.293 3.293a1 1 0 01-1.414-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 010 1.414z" clip-rule="evenodd" />
      </svg></div>
      <div class="text-red-600 text-lg font-extrabold">{{ this.log.votes}}</div>
    </div>
    <div class="flex-1">
      <div class="flex flex-row justify-between">
        <div v-if="!editing" class="md:text-xl text-base font-extrabold">{{ this.displayLog.title }}</div>
        <div class="flex-1 w-full" v-else><input v-model="this.editedLog.title"
                                                 class="bg-slate-100 pr-6 rounded-lg p-1 font-extrabold text-xl w-full ring-1 ring-slate-700/10 flex-1"
                                                 type="text"></div>
        <div class="flex flex-row md:text-base text-xs items-center">
          <svg xmlns="http://www.w3.org/2000/svg" class="md:h-6 md:w-6 w-5 h-5" fill="none" viewBox="0 0 24 24"
               stroke="currentColor"
               stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          {{ frenchDate(this.log.date) }}
        </div>
      </div>
      <div v-if="!editing" class="md:text-base text-sm">{{ this.displayLog.log }}</div>
      <div class="flex-1 w-full mt-1" v-else><textarea v-model="this.editedLog.log"
                                                       class="bg-slate-100 pr-6 rounded-lg p-1 w-full ring-1 ring-slate-700/10 flex-1"></textarea>
      </div>
      <div class="flex items-center justify-between gap-2 flex-row">
        <div class="flex flex-row gap-2 items-center">
          <img class="md:w-8 md:h-8 w-6 h-6 rounded-full"
               :src='"https://ui-avatars.com/api/?name="+this.log.name+"?background=0D8ABC&color=fff"'>
          <router-link :to="{ name: 'profile', params: { id: this.log.user_id }}">
            <div class="md:text-lg text-base font-bold">{{ this.log.name }}</div>
          </router-link>
        </div>
        <div v-if="!editing && editable"
             class="flex flex-row gap-2 items-center">
          <svg @click="editing = !editing" xmlns="http://www.w3.org/2000/svg"
               class="h-6 text-red-600 hover:scale-125 cursor-pointer transition  w-6" fill="none" viewBox="0 0 24 24"
               stroke="currentColor"
               stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
          </svg>
          <svg @click="this.deleteLog" xmlns="http://www.w3.org/2000/svg"
               class="h-6 text-red-600 hover:scale-125 cursor-pointer transition  w-6" fill="none" viewBox="0 0 24 24"
               stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
          </svg>
        </div>
        <div v-else-if="editing && editable" class="flex flex-row gap-2 items-center">
          <svg @click="this.saveLog" xmlns="http://www.w3.org/2000/svg"
               class="h-6 text-green-600 hover:scale-125 cursor-pointer transition  w-6" fill="none"
               viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          <svg @click="editing = false" xmlns="http://www.w3.org/2000/svg"
               class="h-6 text-red-600 text-red-600 hover:scale-125 cursor-pointer transition  w-6" fill="none"
               viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LogComponent",
  props: {
    log: Object
  },
  data() {
    return {
      editable: localStorage.getItem('id') == this.log.user_id,
      editing: false,
      displayLog: this.log,
      editedLog: {...this.log},
      votes: this.log.votes
    }
  },
  methods: {
    deleteLog: async function () {
      await fetch("/api/logs/delete", {
        method: "DELETE",
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          id: this.log.id,
        })
      }).then(res => res.json()).then(res => {
        console.log(res.message);
        if (res.message == "ok") {
          this.$parent.getLogs();
        }
      })
    },
    saveLog: async function () {
      await fetch("/api/logs/edit", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({token: localStorage.getItem("token"), log: this.editedLog})
      }).then(res => res.json()).then(res => {
        if (res.message === "ok") {
          this.editing = false;
          this.displayLog = res.log;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>