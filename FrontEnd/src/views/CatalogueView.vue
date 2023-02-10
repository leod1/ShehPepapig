<template>
  <div v-if="!displayError" class="">
    <div v-if="isMine">
      <svg @click="this.addFile()" xmlns="http://www.w3.org/2000/svg"
           class="h-10 text-green-600 mx-auto mt-3 hover:scale-110 transition cursor-pointer w-10" fill="none"
           viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round"
              d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
      </svg>
    </div>
    <div class="text-center text-xl text-slate-600 font-bold" v-if="this.products.length == 0">Aucun produit a été créer</div>
    <div  class="mt-3" v-for="(log, i) in this.logs" :value="log" :key="i">
      <LogComponent :log="log"></LogComponent>
    </div>
  </div>
</template>

<script>
import LogComponent from "@/components/LogComponent.vue";
import Swal from "sweetalert2";

export default {
  name: "CatalogueView",
  components: {LogComponent},
  data(){
    return {
      products: Object,
      id: this.$route.params.id,
      isMine: Boolean,
      user: Object,
      displayError: false
    }
  },
  async beforeMount() {
    await this.getProducts();
  },
  methods: {
    addFile() {
      Swal.fire({
        title: 'Insérer une Produit',
        html: `<input type="text" id="login" class="swal2-input" style="width: 81%; !important;" placeholder="Titre du produit">
               <input type="text" id="password" class="swal2-input" style="width: 81%; !important;" placeholder="Description du produit">`,
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
          const name = Swal.getPopup().querySelector('#login').value
          const description = Swal.getPopup().querySelector('#password').value
          if (!name || !description) {
            Swal.showValidationMessage(`Entrer un titre et une description`)
          }
          return { name: name, description: description }
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
              fetch('/product', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                  name: result.value.name,
                  description: result.value.description,
                  image: reader.result,
                })
              }).then(() => {

              })
            };
          }
        }
      })
    },
    getProducts: async function(){
      this.products = [];
      await fetch("/api/users/logs?id="+this.id, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => res.json()).then(data => {
        if(data.message !== "error"){
          this.products = [];
          this.products = data.logs;
          console.log(data);
        } else {
         this.displayError = true;
        }
      })
    },
  }
}
</script>