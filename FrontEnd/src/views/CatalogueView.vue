<template>
  <div v-if="!displayError" class="">
    <div class="flex rounded-lg max-w-sm p-2 border border-slate-700/10 mx-auto flex-col items-center">
      <img class="rounded-full object-cover" :src='"https://ui-avatars.com/api/?name="+this.user.name+"?background=0D8ABC&color=fff"'>
      <div class="font-bold text-xl">{{ this.user.name }}</div>
      <div v-if="isMine">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-red-600 hover:scale-125 transition cursor-pointer h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
          <path stroke-linecap="round" stroke-linejoin="round" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
        </svg>
      </div>
    </div>
    <div v-if="isMine">
      <create-compoenent></create-compoenent>
      <div @click="this.addFile()">test</div>
    </div>
    <div class="text-center text-xl text-slate-600 font-bold" v-if="this.logs.length == 0">Cet utilisateur n'as pas encore créer de Logs</div>
    <div  class="mt-3" v-for="(log, i) in this.logs" :value="log" :key="i">
      <LogComponent :log="log"></LogComponent>
    </div>
  </div>
</template>

<script>
import LogComponent from "@/components/LogComponent.vue";
import CreateCompoenent from "@/components/CreateCompoenent.vue";
import Swal from "sweetalert2";

export default {
  name: "CatalogueView",
  components: {CreateCompoenent, LogComponent},
  data(){
    return {
      logs: Object,
      id: this.$route.params.id,
      isMine: Boolean,
      user: Object,
      displayError: false
    }
  },
  async beforeMount() {
    await this.getLogs();
  },
  methods: {
    addFile() {
      Swal.fire({
        title: 'Insérer une Produit',
        html: `<input type="text" id="login" class="swal2-input" placeholder="Titre du produit">
               <input type="text" id="password" class="swal2-input" placeholder="Description du produit">`,
        input: 'file',
        inputAttributes: {
          autocapitalize: 'off',
          'accept': 'image/*',
          'aria-label': 'Insérer une image'
        },
        showCancelButton: true,
        cancelButtonText: 'Annuler',
        confirmButtonText: 'Créer',
        showLoaderOnConfirm: true,
        preConfirm: () => {
          const title = Swal.getPopup().querySelector('#login').value
          const desc = Swal.getPopup().querySelector('#password').value
          if (!title || !desc) {
            Swal.showValidationMessage(`Please enter login and password`)
          }
          return { title: title, desc: desc }
        }
      }).then((result) => {
        if (result.isConfirmed) {
          if (parseFloat(result.value.size / 1024).toFixed(2) > 1000) {
            Swal.fire({
              title: 'Erreur',
              text: 'L\'image est trop lourde',
              icon: 'error',
              showConfirmButton: false,
              timer: 1500
            })
          } else {
            const reader = new FileReader();
            reader.readAsDataURL(result.value);
            reader.onload = () => {
              fetch('/file', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                  name: result.value.name,
                  file: reader.result,
                  folder_id: this.$route.params.id
                })
              }).then(() => {
                this.fetchFiles()
              })
            };
          }
        }
      })
    },
    getLogs: async function(){
      this.logs = [];
      await fetch("/api/users/logs?id="+this.id, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => res.json()).then(data => {
        if(data.message !== "error"){
          this.logs = [];
          this.logs = data.logs;
          console.log(data);
          this.user = data.users[0];
          this.isMine = localStorage.getItem('id') === this.id;
        } else {
         this.displayError = true;
        }
      })
    },
  }
}
</script>