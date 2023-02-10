<template>
  <div v-if="!displayError" class="">
    <div>
      <svg @click="this.addFile()" xmlns="http://www.w3.org/2000/svg"
           class="h-10 text-green-600 mx-auto mt-3 hover:scale-110 transition cursor-pointer w-10" fill="none"
           viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round"
              d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"/>
      </svg>
    </div>
    <div class="text-center text-xl text-slate-600 font-bold" v-if="this.products.length == 0">Aucun produit a été
      créer
    </div>
    <div class="grid gap-1 grid-cols-3">
      <div class="mt-3" v-for="(product, i) in this.products" :value="products" :key="i">
        <ProductComponent :product="product"></ProductComponent>
      </div>
    </div>
  </div>
</template>

<script>
import ProductComponent from "@/components/ProductComponent.vue";
import Swal from "sweetalert2";

export default {
  name: "CatalogueView",
  components: {ProductComponent},
  data() {
    return {
      products: Object,
    }
  },
  async beforeMount() {
    await this.getProducts();
  },
  methods: {
    async addFile() {
      const cats = await fetch("http://leod1.site:8887/category", {
        headers: {
          'Content-Type': 'application/json',
          "Access-Control-Allow-Origin": '*',
          "Access-Control-Allow-Methods": 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
          "Access-Control-Allow-Headers": 'Origin, Content-Type, X-Auth-Token, Accept',
          "Access-Control-Max-Age": "1728000",
        },
        method: "GET",
      }).then(res => res.json()).then(data => {
        return data;
      })
      let options = ''
      cats.forEach((cat) => {
        options += `<option value="${cat.id}">${cat.name}</option>`
      });
      console.log(cats);
      Swal.fire({
        title: 'Insérer une Produit',
        html: `<input type="text" id="login" class="swal2-input" style="width: 81%; !important;" placeholder="Titre du produit">
               <input type="text" id="password" class="swal2-input" style="width: 81%; !important;" placeholder="Description du produit">
               <input type="text" id="image" class="swal2-input" style="width: 81%; !important;" placeholder="Url de l'image du produit">
               <input type="number" id="prix" class="swal2-input" style="width: 81%; !important;" placeholder="Prix du produit">
                <select id="category" class="swal2-input" style="width: 81%; !important;">
                  ${options}
                </select>`,
        showCancelButton: true,
        cancelButtonText: 'Annuler',
        confirmButtonText: 'Créer',
        showLoaderOnConfirm: true,
        preConfirm: () => {
          const name = Swal.getPopup().querySelector('#login').value
          const description = Swal.getPopup().querySelector('#password').value
          const image = Swal.getPopup().querySelector('#image').value;
          const category = Swal.getPopup().querySelector('#category').value;
          const prix = Swal.getPopup().querySelector('#prix').value;
          if (!name || !description || !image || !category || !prix) {
            Swal.showValidationMessage(`Entrer un titre et une description et une image et une categorie et un prix`)
          }
          return {image: image, name: name, description: description, category: category, prix : prix}
        }
      }).then((result) => {
        fetch('http://leod1.site:8887/product', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            name: result.value.name,
            description: result.value.description,
            image: result.value.image,
            category: {id: result.value.category},
            prix : result.value.prix,
          })
        }).then(() => {
          Swal.fire({
            title: 'Succès',
            text: 'Le produit a été créer',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500
          }).then(() => {
            this.getProducts();
          })
        })
      })
    },
    getProducts: async function () {
      this.products = [];
      await fetch("http://leod1.site:8887/product", {
        headers: {
          'Content-Type': 'application/json',
          "Access-Control-Allow-Origin": '*',
          "Access-Control-Allow-Methods": 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
          "Access-Control-Allow-Headers": 'Origin, Content-Type, X-Auth-Token, Accept',
          "Access-Control-Max-Age": "1728000",
        },
        method: "GET",
      }).then(res => res.json()).then(data => {
        this.products = [];
        this.products = data;
        console.log(data);
      })
    },
  }
}
</script>