<template>
  <div>test</div>
</template>

<script>
export default {
  name: "LogView",
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
      await fetch("/product/" + this.id, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }).then((res) => res.json()).then((res) => {
        if (res.message === "ok") {
          this.product = res.product;
        } else {
          this.displayError = true;
        }
      });
    }
  }
}

</script>

<style scoped>

</style>