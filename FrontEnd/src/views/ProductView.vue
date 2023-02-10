<template>
  <div><ProductComponent :focus="true" :product="product"></ProductComponent></div>
  <div class="mt-6 text-xl font-bold mb-2">Description : </div>
  <div class="bg-slate-100/25 p-1 rounded-lg shadow">{{ this.product.description }}</div>
</template>

<script>
import ProductComponent from "@/components/ProductComponent.vue";

export default {
  name: "LogView",
  components: {ProductComponent},
  beforeMount() {
    this.getProduct();
  },
  data() {
    return {
      product: Object,
      id: this.$route.params.id,
    }
  },
  methods: {
    getProduct: async function () {
      await fetch("http://leod1.site:8887/product/" + this.id, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }).then((res) => res.json()).then((res) => {
        this.product = res;
      });
    }
  }
}

</script>

<style scoped>

</style>