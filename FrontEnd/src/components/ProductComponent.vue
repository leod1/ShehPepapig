<template>
  <div class="flex relative flex-col rounded-lg shadow p-2">
    <img :src="product.image" :class="this.focus ? 'h-96' : 'h-64'" class="object-cover rounded-lg"/>
   <router-link :to="'/catalogue/' + this.product.id"><div class="flex mt-2 flex-row justify-between font-bold"><div>{{ product.name }}</div><div>{{ product.prix }}€</div></div></router-link>
    <div v-if="!focus" class="absolute right-3 bg-white p-2 rounded-lg top-3">
      <div class="flex gap-3 flex-row">
        <svg @click="editFile(this.product.id)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
             fill="currentColor" class="w-6 cursor-pointer h-6">
          <path
              d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32l8.4-8.4z"/>
          <path
              d="M5.25 5.25a3 3 0 00-3 3v10.5a3 3 0 003 3h10.5a3 3 0 003-3V13.5a.75.75 0 00-1.5 0v5.25a1.5 1.5 0 01-1.5 1.5H5.25a1.5 1.5 0 01-1.5-1.5V8.25a1.5 1.5 0 011.5-1.5h5.25a.75.75 0 000-1.5H5.25z"/>
        </svg>
        <svg @click="deleteFile(this.product.id)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
             fill="currentColor" class="w-6 cursor-pointer  h-6">
          <path fill-rule="evenodd"
                d="M16.5 4.478v.227a48.816 48.816 0 013.878.512.75.75 0 11-.256 1.478l-.209-.035-1.005 13.07a3 3 0 01-2.991 2.77H8.084a3 3 0 01-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 01-.256-1.478A48.567 48.567 0 017.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 013.369 0c1.603.051 2.815 1.387 2.815 2.951zm-6.136-1.452a51.196 51.196 0 013.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 00-6 0v-.113c0-.794.609-1.428 1.364-1.452zm-.355 5.945a.75.75 0 10-1.5.058l.347 9a.75.75 0 101.499-.058l-.346-9zm5.48.058a.75.75 0 10-1.498-.058l-.347 9a.75.75 0 001.5.058l.345-9z"
                clip-rule="evenodd"/>
        </svg>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  name: "ProductComponent",
  props: {
    product: Object,
    focus: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      editing: false,
      displayLog: this.log,
      editedLog: {...this.log},
    }
  },
  methods: {
    deleteFile(id) {
      Swal.fire({
        title: 'Etes vous sûre?',
        text: "Supprimer un jouet est irréversible!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Supprimer'
      }).then((result) => {
        if (result.isConfirmed) {
          fetch(`http://leod1.site:8887/product/${id}`, {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
            }
          }).then(() => {
            this.$parent.getProducts();
            Swal.fire(
                'Supprimé !',
                'Le jouet a été supprimé.',
                'success'
            )
          });
        }
      })
    },
    async editFile(id) {
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
      Swal.fire({
        title: "Modifier le nom de l'image",
        html: `<input value="${ this.product.name }" type="text" id="login" class="swal2-input" style="width: 81%; !important;" placeholder="Titre du produit">
               <input value="${ this.product.description }" type="text" id="password" class="swal2-input" style="width: 81%; !important;" placeholder="Description du produit">
               <input value="${ this.product.image }" type="text" id="image" class="swal2-input" style="width: 81%; !important;" placeholder="Url de l'image du produit">
               <input value="${ this.product.prix }" type="number" id="prix" class="swal2-input" style="width: 81%; !important;" placeholder="Prix du produit">
                <select id="category" class="swal2-input" style="width: 81%; !important;">
                  ${options}
                </select>`,
        showCancelButton: true,
        confirmButtonText: 'Modifier',
        showLoaderOnConfirm: true,
        preConfirm: () => {
          const name = Swal.getPopup().querySelector('#login').value
          const description = Swal.getPopup().querySelector('#password').value
          const image = Swal.getPopup().querySelector('#image').value;
          const category = Swal.getPopup().querySelector('#category').value;
          const prix = Swal.getPopup().querySelector('#prix').value;
          if (!name || !description || !image || !category || !prix) {
            Swal.showValidationMessage(`Entrer un titre et une description et une image et une categorie`)
          }
          return {image: image, name: name, description: description, category: category, prix : prix}
        }
      }).then((result) => {
        console.log(result)
        if (result.isConfirmed) {
          fetch(`http://leod1.site:8887/product/${id}`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              name: result.value.name,
              description: result.value.description,
              image: result.value.image,
              category: {id: result.value.category},
              prix : result.value.prix
            })
          })
              .then(() => {
                this.$parent.getProducts();
                Swal.fire({
                  title: "Nom de l'image modifié",
                  icon: 'success',
                  showConfirmButton: false,
                  timer: 1500
                })
              }).catch(() => {
            Swal.fire({
              title: 'Erreur',
              icon: 'error',
              showConfirmButton: false,
              timer: 1500
            })
          });
        }
      })
    },
  }
}
</script>

<style scoped>

</style>