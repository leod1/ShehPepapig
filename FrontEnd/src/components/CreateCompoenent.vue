<template>
  <div class="bg-red-600/10 text-red-600 font-extrabold p-2 rounded-lg text-center" v-if="error.length">{{ error }}</div>
  <div v-if="!creating">
    <svg @click="creating = true" xmlns="http://www.w3.org/2000/svg"
         class="h-10 text-green-600 mx-auto mt-3 hover:scale-110 transition cursor-pointer w-10" fill="none"
         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
      <path stroke-linecap="round" stroke-linejoin="round"
            d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
    </svg>
  </div>
  <div class="ring-1 ring-slate-700/10 p-3 rounded-lg" v-else>
    <div class="flex flex-col gap-1">
      <div class="flex flex-col">
        <label for="title" class="text-lg font-bold text-gray-700">Intitulé du log</label>
        <input id="title" type="text" v-model="title" class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg ">
      </div>
      <div class="flex flex-col">
        <label for="email" class="text-lg font-bold text-gray-700">Contenu du log</label>
        <textarea type="email" id="email" v-model="log"
                  class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg "></textarea>
      </div>
      <div class="flex flex-col">
        <label for="passwo" class="text-lg font-bold text-gray-700">Quand ca s'est passé ?</label>
        <input type="datetime-local" v-model="date" id="passwo"
               class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg">
      </div>
      <div>
        <button @click="createLog"
                class="text-white mx-auto flex mt-2 hover:bg-green-800 transition bg-green-600 p-2 rounded-lg font-bold">
          Créer un log
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreateCompoenent",
  data() {
    return {
      title: "",
      log: "",
      date: new Date(),
      error: "",
      creating: false
    }
  },
  methods: {
    createLog: async function () {
      if (this.title != "" && this.log != "" && this.date) {
        await fetch("/api/logs/create", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            title: this.title,
            log: this.log,
            date: this.date,
            token: localStorage.getItem('token')
          })
        }).then(res => res.json()).then(data => {
          if (data.message === 'ok') {
            this.creating = false
            this.$parent.getLogs();
          } else {
            this.error = data.message
          }
        })
      } else {
        this.error = "Veuillez remplir tous les champs"
      }
    }
  }
}
</script>