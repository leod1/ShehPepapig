<template>
  <div class="mx-auto px-6 mt-32 md:px-12 lg:px-6">
    <h1 class="sm:mx-auto text-red-400 font-extrabold text-3xl text-center sm:text-4xl md:text-5xl lg:w-auto lg:text-left xl:text-7x">Erreur 404 <br>Page inconnue</h1>
    <div class="lg:flex flex-row items-center">
      <div class="relative mt-8 md:mt-16 space-y-8 sm:w-10/12 md:w-2/3 lg:ml-0 sm:mx-auto text-center lg:text-left lg:mr-auto lg:w-7/12">
        <div class="grid grid-cols-3 space-x-4 md:space-x-6 md:flex md:justify-center lg:justify-start">
          <button v-if="!isLogged" class="border hover:shadow-md hover:shadow-red-200 transition duration-600 rounded-full px-6 py-4 font-light"><router-link to="/logs">Se connecter</router-link></button>
          <button v-else class="border hover:shadow-md hover:shadow-red-200 transition duration-600 rounded-full px-6 py-4 font-light"><router-link to="/logs">Voir les logs</router-link></button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFoundView",
  data() {
    return {
      isLogged: localStorage.getItem("token") != null,
    };
  },
}
</script>

<style scoped>

</style>