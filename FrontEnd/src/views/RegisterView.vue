<template>
  <div class="mt-32 gap-2 justify-center max-w-lg mx-auto flex flex-col">
    <div class="bg-red-600/10 text-red-600 p-1 rounded-lg text-red font-bold text-center" v-if="error">{{ error }}</div>
    <div class="flex flex-col">
      <label for="name" class="text-lg font-bold text-gray-700">Nom</label>
      <input id="name" type="text" v-model="name" class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg ">
    </div>
    <div class="flex flex-col">
      <label for="email" class="text-lg font-bold text-gray-700">Email</label>
      <input type="email" id="email" v-model="email" class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg ">
    </div>
    <div class="flex flex-col">
      <label for="passwo" class="text-lg font-bold text-gray-700">Mot de passe</label>
      <input type="password" v-model="password" id="passwo" class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg">
    </div>
    <div class="flex flex-col">
      <label for="cpasswo" class="text-lg font-bold text-gray-700">Confirmer Mot de passe</label>
      <input type="password" v-model="confirmPassword" id="cpasswo" class="p-2 bg-slate-100 ring-1 ring-red-900/10 rounded-lg">
    </div>
    <button @click="this.sendLogin"
            class="bg-red-500 text-white font-extrabold hover:bg-red-800 transition hover:scale-[1.02] rounded-lg mt-3 p-2">
      S'inscrire
    </button>
    <router-link to="/login">Déjà inscit ?</router-link>
  </div>
</template>

<script>
export default {
  name: "RegisterView",
  data() {
    return {
      name: "",
      email: "",
      password: "",
      confirmPassword: "",
      error: "",
    }
  },
  methods: {
    sendLogin: async function() {
      if(this.name.length && this.email.length && this.password){
        if(this.password == this.confirmPassword){
          this.error = ""
          await fetch('/api/register', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({name: this.name, email: this.email, password: this.password})
          }).then(res => res.json()).then(data => {
            console.log(data)
            if(data.message === 'ok') {
              this.error = "Inscription réussie !"
              localStorage.setItem('token', data.token);
              localStorage.setItem('id', data.id);
              this.$root.getUser(data.token);
              this.$router.push('/profile/'+data.id)
            } else {
              this.error = data.message
            }
          })
        } else{
          this.error = "Les mots de passe ne correspondent pas"
        }
      } else{
        this.error = "Veuillez remplir tous les champs"
      }
    }
  }
}
</script>